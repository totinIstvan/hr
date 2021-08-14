package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.PositionByCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionByCompanyRepository extends JpaRepository<PositionByCompany, Long> {
}
