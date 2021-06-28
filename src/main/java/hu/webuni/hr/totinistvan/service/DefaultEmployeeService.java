package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService implements EmployeeService {

    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}
