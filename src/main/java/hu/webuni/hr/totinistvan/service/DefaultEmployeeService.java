package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.config.EmployeeConfigProperties;
import hu.webuni.hr.totinistvan.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService implements EmployeeService {

    @Autowired
    EmployeeConfigProperties config;

    @Override
    public int getPayRaisePercent(Employee employee) {
        return config.getPayRisePercent().getDef().getFive();
    }
}
