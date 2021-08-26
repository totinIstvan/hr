package hu.webuni.hr.totinistvan.service;

import hu.webuni.hr.totinistvan.model.entity.Employee;
import hu.webuni.hr.totinistvan.model.entity.Position;
import hu.webuni.hr.totinistvan.repository.EmployeeRepository;
import hu.webuni.hr.totinistvan.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public abstract class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public abstract int getPayRaisePercent(Employee employee);

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }

    @Transactional
    public Employee save(Employee employee) {
        setPositionForEmployee(employee, positionRepository);
        return employeeRepository.save(employee);
    }

    protected static void setPositionForEmployee(Employee employee, PositionRepository positionRepository) {
        Position position = employee.getPosition();
        if (position != null && position.getName() != null) {
            Optional<Position> positionByName = positionRepository.findByName(position.getName());
            if (positionByName.isEmpty()) {
                position = positionRepository.save(new Position(position.getName()));
            } else {
                position = positionByName.get();
            }
        }
        employee.setPosition(position);
    }

    @Transactional
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return save(employee);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> getWithHigherSalaryThanLimit(int limit, Pageable pageable) {
        return employeeRepository.getAllBySalaryAfter(limit, pageable);
    }

    public List<Employee> getEmployeesByPositionName(String position) {
        return employeeRepository.getEmployeesByPositionName(position);
    }

    public List<Employee> getEmployeesByNameStartingWith(String s) {
        return employeeRepository.getEmployeesByNameStartingWithIgnoreCase(s);
    }

    public List<Employee> getEmployeesByJoinDateBetween(LocalDateTime start, LocalDateTime end) {
        return employeeRepository.getEmployeesByJoinDateBetween(start, end);
    }
}
