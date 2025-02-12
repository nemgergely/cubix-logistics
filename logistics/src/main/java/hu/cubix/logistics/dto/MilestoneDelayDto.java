package hu.cubix.logistics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDelayDto {

    @NotNull(message = "The milestone ID, which you would like to register the delay to, cannot be null")
    private Long id;

    @Positive(message = "The registered delay can only be a positive number")
    private Integer delay;
}
