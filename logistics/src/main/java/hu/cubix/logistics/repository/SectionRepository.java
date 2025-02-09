package hu.cubix.logistics.repository;

import hu.cubix.logistics.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = "SELECT s FROM Section s WHERE s.startMilestone.id = :milestoneId")
    Section findByStartMilestoneId(long milestoneId);

    @Query(value = "SELECT s FROM Section s WHERE s.endMilestone.id = :milestoneId")
    Section findByEndMilestoneId(long milestoneId);

    Section findByOrderIndex(Integer orderIndex);
}
