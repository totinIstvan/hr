package hu.webuni.hr.totinistvan.config;

import hu.webuni.hr.totinistvan.service.EmployeeService;
import hu.webuni.hr.totinistvan.service.DefaultEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class SalaryConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new DefaultEmployeeService();
    }
}
