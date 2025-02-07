package hu.cubix.logistics.dto;

import hu.cubix.logistics.entities.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportPlanDto {

    private Long id;
    private Integer income;
    private List<Section> sections;
}
