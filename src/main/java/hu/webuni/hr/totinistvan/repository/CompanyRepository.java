package hu.webuni.hr.totinistvan.repository;

import hu.webuni.hr.totinistvan.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
