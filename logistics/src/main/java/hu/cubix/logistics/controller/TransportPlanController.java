package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.MilestoneDelayDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import hu.cubix.logistics.entities.TransportPlan;
import hu.cubix.logistics.mapper.ITransportPlanMapper;
import hu.cubix.logistics.service.TransportPlanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    private final TransportPlanService transportPlanService;
    private final ITransportPlanMapper transportPlanMapper;

    @PostMapping("/{id}/delay")
    public ResponseEntity<TransportPlanDto> registerDelayToTransportPlan(
        @PathVariable Long id,
        @RequestBody @Valid MilestoneDelayDto milestoneDelayDto) {

        TransportPlan transportPlan = transportPlanService.registerDelayToTransportPlan(id, milestoneDelayDto);
        TransportPlanDto transportPlanDto = transportPlanMapper.transportPlanToDto(transportPlan);

        return new ResponseEntity<>(transportPlanDto, HttpStatus.OK);
    }
}
