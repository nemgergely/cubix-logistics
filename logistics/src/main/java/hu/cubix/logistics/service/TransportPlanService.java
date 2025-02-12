package hu.cubix.logistics.service;

import hu.cubix.logistics.configuration.DelayConfigurationProperties;
import hu.cubix.logistics.configuration.DelayConfigurationProperties.Limit;
import hu.cubix.logistics.configuration.DelayConfigurationProperties.Percentage;
import hu.cubix.logistics.dto.MilestoneDelayDto;
import hu.cubix.logistics.entities.Milestone;
import hu.cubix.logistics.entities.Section;
import hu.cubix.logistics.entities.TransportPlan;
import hu.cubix.logistics.exception.RecordNotFoundException;
import hu.cubix.logistics.exception.MilestoneWithoutSectionException;
import hu.cubix.logistics.repository.MilestoneRepository;
import hu.cubix.logistics.repository.SectionRepository;
import hu.cubix.logistics.repository.TransportPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransportPlanService {

    private final DelayConfigurationProperties delayConfigurationProperties;
    private final TransportPlanRepository transportPlanRepository;
    private final SectionRepository sectionRepository;
    private final MilestoneRepository milestoneRepository;

    @Transactional
    public TransportPlan registerDelayToTransportPlan(Long id, MilestoneDelayDto milestoneDelayDto) {
        Long milestoneId = milestoneDelayDto.getId();
        Integer delay = milestoneDelayDto.getDelay();

        TransportPlan transportPlan = transportPlanRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("transport plan")
        );

        registerDelayToSections(milestoneId, delay);

        transportPlan.setIncome(calculateNewIncome(transportPlan.getIncome(), delay));
        return transportPlan;
    }

    private void registerDelayToSections(Long milestoneId, Integer delay) {
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new RecordNotFoundException("milestone");
        }

        Section sectionByStartMilestone = sectionRepository.findByStartMilestoneId(milestoneId);
        Section sectionByEndMilestone = sectionRepository.findByEndMilestoneId(milestoneId);

        if (sectionByStartMilestone == null && sectionByEndMilestone == null) {
            throw new MilestoneWithoutSectionException();
        }
        if (sectionByStartMilestone != null) {
            registerDelayToCurrentSection(sectionByStartMilestone, delay);
        }
        if (sectionByEndMilestone != null) {
            registerDelayToCurrentAndNextSection(sectionByEndMilestone, delay);
        }
    }

    private void registerDelayToCurrentSection(Section sectionByStartMilestone, Integer delay) {
        Milestone startMilestone = sectionByStartMilestone.getStartMilestone();
        Milestone endMilestone = sectionByStartMilestone.getEndMilestone();
        startMilestone.setPlannedTime(startMilestone.getPlannedTime().plusMinutes(delay));
        endMilestone.setPlannedTime(endMilestone.getPlannedTime().plusMinutes(delay));
    }

    private void registerDelayToCurrentAndNextSection(Section sectionByEndMilestone, Integer delay) {
        Milestone endMilestone = sectionByEndMilestone.getEndMilestone();
        endMilestone.setPlannedTime(endMilestone.getPlannedTime().plusMinutes(delay));

        Long transportPlanId = sectionByEndMilestone.getTransportPlan().getId();
        Integer nextOrderIndex = sectionByEndMilestone.getOrderIndex() + 1;
        Section nextSection = sectionRepository.findByTransportPlanIdAndOrderIndex(transportPlanId, nextOrderIndex);
        if (nextSection != null) {
            Milestone nextStartMilestone = nextSection.getStartMilestone();
            nextStartMilestone.setPlannedTime(nextStartMilestone.getPlannedTime().plusMinutes(delay));
        }
    }

    private int calculateNewIncome(Integer income, Integer delay) {
        return (int)(income / 100.0 * (100 - getPercentageDecrease(delay)));
    }

    private int getPercentageDecrease(Integer delay) {
        Limit limit = delayConfigurationProperties.getLimit();
        Percentage percentage = delayConfigurationProperties.getPercentage();

        if (delay > limit.getFirstCategory() && delay < limit.getSecondCategory()) {
            return percentage.getFirstCategory();
        } else if (delay < limit.getThirdCategory()) {
            return percentage.getThirdCategory();
        } else if (delay < limit.getFourthCategory()) {
            return percentage.getThirdCategory();
        } else {
            return percentage.getFourthCategory();
        }
    }
}
