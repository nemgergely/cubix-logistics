package hu.cubix.logistics.service;

import hu.cubix.logistics.configuration.DelayConfigurationProperties;
import hu.cubix.logistics.configuration.DelayConfigurationProperties.*;
import hu.cubix.logistics.dto.MilestoneDelayDto;
import hu.cubix.logistics.entities.Milestone;
import hu.cubix.logistics.entities.Section;
import hu.cubix.logistics.entities.TransportPlan;
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
        TransportPlan transportPlan = transportPlanRepository.findById(id).orElseThrow();
        Section sectionByStartMilestone = sectionRepository.findByStartMilestoneId(milestoneId);
        Section sectionByEndMilestone = sectionRepository.findByEndMilestoneId(milestoneId);

        if (sectionByStartMilestone != null) {
            registerDelayToCurrentSection(sectionByStartMilestone, delay);
        }
        if (sectionByEndMilestone != null) {
            registerDelayToCurrentAndNextSection(sectionByEndMilestone, delay);
        }
        transportPlan.setIncome(calculateNewIncome(transportPlan.getIncome(), delay));
        return transportPlan;
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

        Integer nextOrderIndex = sectionByEndMilestone.getOrderIndex() + 1;
        Section nextSection = sectionRepository.findByOrderIndex(nextOrderIndex);
        if (nextSection != null) {
            Milestone nextStartMilestone = nextSection.getStartMilestone();
            nextStartMilestone.setPlannedTime(nextStartMilestone.getPlannedTime().plusMinutes(delay));
        }
    }

    private int calculateNewIncome(Integer income, Integer delay) {
        return ((100 - getDecreasePercentage(delay)) / 100) * income;
    }

    private int getDecreasePercentage(Integer delay) {
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
