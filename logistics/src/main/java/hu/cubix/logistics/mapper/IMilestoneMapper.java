package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.MilestoneDto;
import hu.cubix.logistics.entities.Milestone;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMilestoneMapper {

    MilestoneDto milestoneToDto(Milestone Milestone);
    List<MilestoneDto> milestonesToDtos(List<Milestone> Milestones);
    Milestone dtoToMilestone(MilestoneDto MilestoneDto);
    List<Milestone> dtosToMilestones(List<MilestoneDto> MilestoneDtos);
}
