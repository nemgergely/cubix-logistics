package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.TransportPlanDto;
import hu.cubix.logistics.entities.TransportPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITransportPlanMapper {

    TransportPlanDto transportPlanToDto(TransportPlan transportPlan);
}
