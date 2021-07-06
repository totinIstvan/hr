package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmartEmployeeService implements EmployeeService {

    @Autowired
    EmployeeConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {

        Duration duration = Duration.between(employee.getJoinDate(), LocalDateTime.now());

        double yearsWorked = Math.round((duration.toDays() / 365.0) * 10.0) / 10.0;

        List<Double> years = config.getYearsWorked().getYears();

        List<Integer> percents = config.getPayRisePercent().getSpecial().getPercents();

        double minOfYearsForPayRise = years.stream()
                .filter(year -> year <= yearsWorked)
                .findFirst()
                .orElse(0.0);

        return percents.stream()
                .filter(percent -> minOfYearsForPayRise >= percent)
                .findFirst()
                .get();
    }
}
