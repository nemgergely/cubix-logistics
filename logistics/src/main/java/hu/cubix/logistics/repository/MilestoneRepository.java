package hu.cubix.logistics.repository;

import hu.cubix.logistics.entities.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
