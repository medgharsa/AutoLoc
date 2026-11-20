package tn.esprit.autoloc.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.entities.Agence;
import tn.esprit.autoloc.entities.Employee;
import tn.esprit.autoloc.repositories.AgenceRepository;
import tn.esprit.autoloc.repositories.EmployeeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IEmployeeServiceImp implements IEmployeeService {

    EmployeeRepository employeeRepository;
    AgenceRepository agenceRepository;

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

    @Override
    @Transactional
    public Employee desaffecterEmploye(Long idEmploye) {
        Employee employe = employeeRepository.findById(idEmploye)
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable : " + idEmploye));

        employe.setAgence(null);
        return employeeRepository.save(employe);
    }

    @Override
    @Transactional
    public Employee affecterEmploye(Long idEmploye, Long idAgence) {
        Employee employe = employeeRepository.findById(idEmploye)
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable : " + idEmploye));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : " + idAgence));

        employe.setAgence(agence);
        return employeeRepository.save(employe);
    }
}