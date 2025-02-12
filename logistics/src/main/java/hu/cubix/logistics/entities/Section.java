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

    @Column(name = "order_index")
    private Integer orderIndex;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "start_milestone_id", referencedColumnName = "id")
    private Milestone startMilestone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "end_milestone_id", referencedColumnName = "id")
    private Milestone endMilestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_plan_id")
    private TransportPlan transportPlan;
}
