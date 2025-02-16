package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.MilestoneDelayDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import hu.cubix.logistics.entities.Milestone;
import hu.cubix.logistics.entities.TransportPlan;
import hu.cubix.logistics.exception.RecordNotFoundException;
import hu.cubix.logistics.repository.MilestoneRepository;
import hu.cubix.logistics.repository.TransportPlanRepository;
import hu.cubix.logistics.validation.MyErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class TransportPlanControllerIntTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    WebTestClient webTestClient;

    private static final String API_TRANSPORT_PLAN = "/api/transportPlans";
    private static final LocalDateTime TIME_OF_START_MILESTONE = LocalDateTime.of(2025, 5, 5, 8, 0, 0);
    private static final LocalDateTime TIME_OF_END_MILESTONE = LocalDateTime.of(2025, 5, 5, 10, 0, 0);
    private static final LocalDateTime TIME_OF_NEXT_SECTION_START_MILESTONE = LocalDateTime.of(2025, 5, 5, 12, 0, 0);

    @BeforeEach
    void setUp() {
        webTestClient = MockMvcWebTestClient
            .bindToApplicationContext(webApplicationContext)
            .apply(springSecurity())
            .build();
    }

    @AfterEach
    void reset() {
        resetTransportPlan();
    }

    @Test
    @WithUserDetails(value = "addressMgr", userDetailsServiceBeanName = "userDetailsService")
    void testForbiddenAttemptToRegisterDelayToTransportPlan() {
        // ARRANGE
        MilestoneDelayDto milestoneDelayDto = getMilestoneDelayDto(1L, 75);

        // ACT - ASSERT
        post403ForbiddenDelayRegisterRequest(milestoneDelayDto);
    }

    @ParameterizedTest
    @WithUserDetails(value = "transportMgr", userDetailsServiceBeanName = "userDetailsService")
    @CsvSource(value = {
        "Valid request - delay on section's start/end milestone - category #1, 1, 1, 15",
        "Valid request - delay on section's start/end milestone - category #2, 1, 1, 45",
        "Valid request - delay on section's start/end milestone - category #3, 1, 1, 75",
        "Valid request - delay on section's start/end milestone - category #4, 1, 1, 135",
        "Valid request - delay on section's end milestone / next section's start milestone - category #1, 1, 2, 15"
    })
    void testValidAttemptsToRegisterDelayToTransportPlan(
        String caseName, String transportPlanIdString, String milestoneIdString, String delayString
    ) {
        // ARRANGE
        Long transportPlanId = Long.parseLong(transportPlanIdString);
        Long milestoneId = Long.parseLong(milestoneIdString);
        Integer delay = Integer.parseInt(delayString);
        MilestoneDelayDto milestoneDelayDto = getMilestoneDelayDto(milestoneId, delay);

        // ACT
        EntityExchangeResult<TransportPlanDto> response =
            post200ValidDelayRegisterRequest(transportPlanId, milestoneDelayDto);
        TransportPlanDto responseDto = response.getResponseBody();

        // ASSERT
        assertNotNull(responseDto);

        switch (caseName) {
            case "Valid request - delay on section's start/end milestone - category #1":
                assertValidCases(responseDto, 1960000, 15, false);
                break;
            case "Valid request - delay on section's start/end milestone - category #2":
                assertValidCases(responseDto, 1920000, 45, false);
                break;
            case "Valid request - delay on section's start/end milestone - category #3":
                assertValidCases(responseDto, 1840000, 75, false);
                break;
            case "Valid request - delay on section's start/end milestone - category #4":
                assertValidCases(responseDto, 1680000, 135, false);
                break;
            case "Valid request - delay on section's end milestone / next section's start milestone - category #1":
                assertValidCases(responseDto, 1960000, 15, true);
                break;
            default:
                break;
        }
    }

    @ParameterizedTest
    @WithUserDetails(value = "transportMgr", userDetailsServiceBeanName = "userDetailsService")
    @CsvSource(value = {
        "Invalid request - transport plan not found, 3, 1, 15",
        "Invalid request - milestone not found, 1, 13, 15",
        "Invalid request - milestone does not belong to any sections of the transport plan, 1, 4, 15"
    })
    void testInvalidAttemptsToRegisterDelayToTransportPlan(
        String caseName, String transportPlanIdString, String milestoneIdString, String delayString
    ) {
        // ARRANGE
        Long transportPlanId = Long.parseLong(transportPlanIdString);
        Long milestoneId = Long.parseLong(milestoneIdString);
        Integer delay = Integer.parseInt(delayString);

        MilestoneDelayDto milestoneDelayDto = getMilestoneDelayDto(milestoneId, delay);
        EntityExchangeResult<MyErrorResponse> response;
        MyErrorResponse myErrorResponse;

        // ACT - ASSERT
        switch (caseName) {
            case "Invalid request - transport plan not found":
                response = post404NotFoundDelayRegisterRequest(transportPlanId, milestoneDelayDto);
                myErrorResponse = response.getResponseBody();
                assertNotNull(myErrorResponse);
                assertTrue(
                    myErrorResponse.getMessage().contains("The transport plan with the given ID was not found in the database")
                );
                break;
            case "Invalid request - milestone not found":
                response = post404NotFoundDelayRegisterRequest(transportPlanId, milestoneDelayDto);
                myErrorResponse = response.getResponseBody();
                assertNotNull(myErrorResponse);
                assertTrue(
                    myErrorResponse.getMessage().contains("The milestone with the given ID was not found in the database")
                );
                break;
            case "Invalid request - milestone does not belong to any section of the transport plan":
                response = post400BadRequestDelayRegisterRequest(transportPlanId, milestoneDelayDto);
                myErrorResponse = response.getResponseBody();
                assertNotNull(myErrorResponse);
                assertTrue(
                    myErrorResponse.getMessage().contains("This milestone does not belong to any section of this transport plan")
                );
                break;
            default:
                break;
        }
    }

    private void assertValidCases(
        TransportPlanDto responseDto,
        Integer expectedReducedIncome,
        Integer delay,
        boolean delayAffectsNextSection) {

        if (delayAffectsNextSection) {
            assertEquals(
                responseDto.getSections().get(1).getStartMilestone().getPlannedTime(),
                TIME_OF_NEXT_SECTION_START_MILESTONE.plusMinutes((long) delay)
            );
        } else {
            assertEquals(
                responseDto.getSections().get(0).getStartMilestone().getPlannedTime(),
                TIME_OF_START_MILESTONE.plusMinutes((long) delay)
            );
        }
        assertEquals(
            responseDto.getSections().get(0).getEndMilestone().getPlannedTime(),
            TIME_OF_END_MILESTONE.plusMinutes((long) delay)
        );
        assertEquals(expectedReducedIncome, responseDto.getIncome());

    }

    private MilestoneDelayDto getMilestoneDelayDto(Long milestoneId, Integer delay) {
        return new MilestoneDelayDto(milestoneId, delay);
    }

    private void post403ForbiddenDelayRegisterRequest(MilestoneDelayDto milestoneDelayDto) {
        webTestClient
            .post()
            .uri(
                API_TRANSPORT_PLAN
                    .concat("//1//delay")
            )
            .bodyValue(milestoneDelayDto)
            .exchange()
            .expectStatus()
            .isForbidden();
    }

    private EntityExchangeResult<MyErrorResponse> post400BadRequestDelayRegisterRequest(Long transportPlanId, MilestoneDelayDto milestoneDelayDto) {
        return webTestClient
            .post()
            .uri(
                API_TRANSPORT_PLAN
                    .concat("//")
                    .concat(String.valueOf(transportPlanId))
                    .concat("//delay")
            )
            .bodyValue(milestoneDelayDto)
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectBody(MyErrorResponse.class)
            .returnResult();
    }

    private EntityExchangeResult<MyErrorResponse> post404NotFoundDelayRegisterRequest(Long transportPlanId, MilestoneDelayDto milestoneDelayDto) {
        return webTestClient
            .post()
            .uri(
                API_TRANSPORT_PLAN
                    .concat("//")
                    .concat(String.valueOf(transportPlanId))
                    .concat("//delay")
            )
            .bodyValue(milestoneDelayDto)
            .exchange()
            .expectStatus()
            .isNotFound()
            .expectBody(MyErrorResponse.class)
            .returnResult();
    }

    private EntityExchangeResult<TransportPlanDto> post200ValidDelayRegisterRequest(Long transportPlanId, MilestoneDelayDto milestoneDelayDto) {
         return webTestClient
            .post()
            .uri(
                API_TRANSPORT_PLAN
                    .concat("//")
                    .concat(String.valueOf(transportPlanId))
                    .concat("//delay")
            )
            .bodyValue(milestoneDelayDto)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(TransportPlanDto.class)
            .returnResult();
    }

    private void resetTransportPlan() {
        TransportPlan transportPlan = transportPlanRepository.findById(1L)
            .orElseThrow(() -> new RecordNotFoundException("Transport plan not found"));
        Milestone startMilestone = milestoneRepository.findById(1L)
            .orElseThrow(() -> new RecordNotFoundException("Milestone not found"));
        Milestone endMilestone = milestoneRepository.findById(2L)
            .orElseThrow(() -> new RecordNotFoundException("Milestone not found"));
        Milestone nextSectionStartMilestone = milestoneRepository.findById(3L)
            .orElseThrow(() -> new RecordNotFoundException("Milestone not found"));

        startMilestone.setPlannedTime(TIME_OF_START_MILESTONE);
        endMilestone.setPlannedTime(TIME_OF_END_MILESTONE);
        nextSectionStartMilestone.setPlannedTime(TIME_OF_NEXT_SECTION_START_MILESTONE);
        milestoneRepository.saveAll(List.of(startMilestone, endMilestone, nextSectionStartMilestone));

        transportPlan.setIncome(2000000);
        transportPlanRepository.save(transportPlan);
    }
}
