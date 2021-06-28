package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties;
import hu.webuni.hr.totinistvan.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SmartEmployeeService implements EmployeeService {

//    @Value("${employee.yearsworked.ten}")
//    private double yearsWorked1;
//    @Value("${employee.yearsworked.five}")
//    private double yearsWorked2;
//    @Value("${employee.yearsworked.twoandhalf}")
//    private double yearsWorked3;
//    @Value("${employee.payrisepercent.special.ten}")
//    private int payRisePercent1;
//    @Value("${employee.payrisepercent.special.five}")
//    private int payRisePercent2;
//    @Value("${employee.payrisepercent.special.two}")
//    private int payRisePercent3;
//    @Value("${employee.payrisepercent.special.zero}")
//    private int noPayRise;

    @Autowired
    EmployeeConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        Duration duration = Duration.between(employee.getJoinDate(), LocalDateTime.now());
        double yearsWorked = Math.round((duration.getSeconds() / 30758400.0) * 10.0) / 10.0;
//        return yearsWorked >= yearsWorked1 ? payRisePercent1
//                : yearsWorked >= yearsWorked2 ? payRisePercent2
//                : yearsWorked >= yearsWorked3 ? payRisePercent3
//                : noPayRise;
        return yearsWorked >= config.getYearsWorked().getTen() ? config.getPayrisePercent().getSpecial().getTen()
                : yearsWorked >= config.getYearsWorked().getFive() ? config.getPayrisePercent().getSpecial().getFive()
                : yearsWorked >= config.getYearsWorked().getTwoAndHalf() ? config.getPayrisePercent().getSpecial().getTwo()
                : config.getPayrisePercent().getSpecial().getZero();
    }
}
