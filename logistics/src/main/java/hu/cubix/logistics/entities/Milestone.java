package hu.cubix.logistics.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "milestone")
@Getter
@Setter
@NoArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime plannedTime;

    @OneToOne(mappedBy = "milestone", fetch = FetchType.EAGER)
    private Address address;

    @OneToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
