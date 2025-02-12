package hu.cubix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {

    private Long id;
    private Integer orderIndex;
    private MilestoneDto startMilestone;
    private MilestoneDto endMilestone;
}
