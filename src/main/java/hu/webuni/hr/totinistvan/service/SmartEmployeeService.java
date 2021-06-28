package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties;
import hu.webuni.hr.totinistvan.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SmartEmployeeService implements EmployeeService {

    @Autowired
    EmployeeConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {

        Duration duration = Duration.between(employee.getJoinDate(), LocalDateTime.now());

        double yearsWorked = Math.round((duration.getSeconds() / 30758400.0) * 10.0) / 10.0;

        return yearsWorked >= config.getYearsWorked().getTen() ? config.getPayrisePercent().getSpecial().getTen()
                : yearsWorked >= config.getYearsWorked().getFive() ? config.getPayrisePercent().getSpecial().getFive()
                : yearsWorked >= config.getYearsWorked().getTwoAndHalf() ? config.getPayrisePercent().getSpecial().getTwo()
                : config.getPayrisePercent().getSpecial().getZero();
    }
}
