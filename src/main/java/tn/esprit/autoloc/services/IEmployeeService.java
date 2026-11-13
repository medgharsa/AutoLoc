package tn.esprit.autoloc.services;

import tn.esprit.autoloc.entities.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee ajouterEmployee(Employee employee);
    Employee modifierEmployee(Employee employee);
    List<Employee> consulterEmployees();
    Employee consulterEmployeeById(long idEmployee);
    void supprimerEmployeeById(long idEmployee);
}