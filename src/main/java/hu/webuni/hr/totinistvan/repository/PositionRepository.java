package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
