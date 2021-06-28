package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SmartEmployeeService implements EmployeeService {

    @Value("${years.worked.ten}")
    private double yearsWorked1;
    @Value("${years.worked.five}")
    private double yearsWorked2;
    @Value("${years.worked.two.and.half}")
    private double yearsWorked3;
    @Value("${pay.rise.percent.ten}")
    private int payRisePercent1;
    @Value("${pay.rise.percent.five}")
    private int payRisePercent2;
    @Value("${pay.rise.percent.two}")
    private int payRisePercent3;
    @Value("${pay.rise.percent.zero}")
    private int noPayRise;


    @Override
    public int getPayRaisePercent(Employee employee) {
        Duration duration = Duration.between(employee.getJoinDate(), LocalDateTime.now());
        double yearsWorked = Math.round((duration.getSeconds() / 30758400.0) * 10.0) / 10.0;
        return yearsWorked >= yearsWorked1 ? payRisePercent1 : yearsWorked >= yearsWorked2 ? payRisePercent2 : yearsWorked >= yearsWorked3 ? payRisePercent3 : noPayRise;
    }
}
