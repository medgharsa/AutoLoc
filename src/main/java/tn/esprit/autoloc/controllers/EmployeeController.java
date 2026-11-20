package tn.esprit.autoloc.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.entities.Employee;
import tn.esprit.autoloc.services.IEmployeeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("employee")
public class EmployeeController {
    private final IEmployeeService employeeService;

    @PostMapping
    public Employee ajouterEmployee(@RequestBody Employee employee){
        return employeeService.ajouterEmployee(employee);
    }

    @PutMapping
    public Employee modifierEmployee(@RequestBody Employee employee){
        return employeeService.modifierEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAll(){
        return employeeService.consulterEmployees();
    }

    @GetMapping("/getById/{idEmployee}")
    public Employee getEmployeeById(@PathVariable Long idEmployee){
        return employeeService.consulterEmployeeById(idEmployee);
    }

    @DeleteMapping("/delete/{idEmployee}")
    public void deleteById(@PathVariable Long idEmployee){
        employeeService.supprimerEmployeeById(idEmployee);
    }

    @PutMapping("/desaffecter/{idEmploye}")
    public Employee desaffecterEmploye(@PathVariable Long idEmploye){
        return employeeService.desaffecterEmploye(idEmploye);
    }

    @PutMapping("/affecter/{idEmploye}/{idAgence}")
    public Employee affecterEmploye(@PathVariable Long idEmploye, @PathVariable Long idAgence){
        return employeeService.affecterEmploye(idEmploye, idAgence);
    }
}