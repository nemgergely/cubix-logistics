package hu.cubix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDto {

    private Long id;
    private LocalDateTime plannedTime;
    private AddressDto addressDto;
}
