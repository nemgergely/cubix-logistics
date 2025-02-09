package hu.cubix.logistics.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "section")
@Getter
@Setter
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderIndex;

    @OneToOne(mappedBy = "section", fetch = FetchType.EAGER)
    private Milestone startMilestone;

    @OneToOne(mappedBy = "section", fetch = FetchType.EAGER)
    private Milestone endMilestone;

    @ManyToOne
    @JoinColumn(name = "transport_plan_id")
    private TransportPlan transportPlan;
}
