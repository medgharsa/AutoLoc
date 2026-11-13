package tn.esprit.autoloc.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.entities.Employee;
import tn.esprit.autoloc.repositories.EmployeeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IEmployeeServiceImp implements IEmployeeService {

    EmployeeRepository employeeRepository;

    @Override
    public Employee ajouterEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee modifierEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> consulterEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee consulterEmployeeById(long idEmployee) {
        return employeeRepository.findById(idEmployee).orElse(null);
    }

    @Override
    public void supprimerEmployeeById(long idEmployee) {
        employeeRepository.deleteById(idEmployee);
    }
}