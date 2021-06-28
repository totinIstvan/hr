package hu.webuni.hr.totinistvan.config;

import hu.webuni.hr.totinistvan.service.EmployeeService;
import hu.webuni.hr.totinistvan.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class SmartSalaryConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new SmartEmployeeService();
    }

}
