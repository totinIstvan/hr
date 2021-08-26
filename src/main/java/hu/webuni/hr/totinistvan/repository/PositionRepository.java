package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
}
