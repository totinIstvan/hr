package hu.webuni.hr.totinistvan;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.service.InitDbService;
import hu.webuni.hr.totinistvan.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements  CommandLineRunner {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Employee employee1 = new Employee(1, "John Doe", "CEO", 1_500_000, LocalDateTime.of(1980, 6, 15, 8, 0,0));
        Employee employee2 = new Employee(2, "Jack Doe", "CTO", 1_000_000, LocalDateTime.of(2012, 9, 10, 8, 0,0));
        Employee employee3 = new Employee(3, "Jane Doe", "CMO", 1_000_000, LocalDateTime.of(2017, 12, 3, 8, 0,0));
        Employee employee4 = new Employee(4, "Jason Doe", "CBO", 1_000_000, LocalDateTime.of(2020, 8, 17, 8, 0,0));
        System.out.println(salaryService.payRise(employee1));
        System.out.println(salaryService.payRise(employee2));
        System.out.println(salaryService.payRise(employee3));
        System.out.println(salaryService.payRise(employee4));*/

        initDbService.clearDb();

        initDbService.insertTestData();
    }
}
