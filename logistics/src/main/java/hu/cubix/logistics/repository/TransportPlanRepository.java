package hu.cubix.logistics.repository;

import hu.cubix.logistics.entities.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
