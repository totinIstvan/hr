package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties;
import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties.Smart;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SmartEmployeeService implements EmployeeService {

    @Autowired
    EmployeeConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {

        Duration duration = Duration.between(employee.getJoinDate(), LocalDateTime.now());

        double yearsWorked = Math.round((duration.toDays() / 365.0) * 10.0) / 10.0;

        Smart smartConfig = config.getSalary().getSmart();

        TreeMap<Double, Integer> limits = smartConfig.getLimits();

        Map.Entry<Double, Integer> floorEntry = limits.floorEntry(yearsWorked);

        return floorEntry.getValue();

    }
}
