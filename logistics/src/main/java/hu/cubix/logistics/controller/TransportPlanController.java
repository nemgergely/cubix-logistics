package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.MilestoneDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @PostMapping("/{id}/delay")
    public ResponseEntity<TransportPlanDto> registerDelayToTransportPlan(
        @PathVariable Integer id,
        @RequestBody MilestoneDto milestoneDto) {

        return null;
    }
}
