package tn.esprit.autoloc.services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.entities.Equipement;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.repositories.EquipementRepository;
import tn.esprit.autoloc.repositories.VehiculeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IVehiculeServiceImp implements IVehiculeService {

    VehiculeRepository vehiculeRepository;
    EquipementRepository equipementRepository;

    @Override
    public Vehicule ajouterVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    /** Scénario 3 (départ) : affecte l'équipement (ex. siège bébé) au véhicule. */
    @Override
    @Transactional
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable : " + idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Équipement introuvable : " + idEquipement));

        if (vehicule.getEquipements() == null) {
            vehicule.setEquipements(new ArrayList<>());
        }
        boolean dejaAffecte = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (!dejaAffecte) {
            vehicule.getEquipements().add(equipement);
        }
        return vehiculeRepository.save(vehicule);
    }

    /** Scénario 3 (retour) : retire l'équipement du véhicule. */
    @Override
    @Transactional
    public Vehicule desaffecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable : " + idVehicule));

        if (vehicule.getEquipements() != null) {
            vehicule.getEquipements().removeIf(e -> e.getIdEquipement().equals(idEquipement));
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule modifierVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> consulterVehicules() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule consulterVehiculeById(long idVehicule) {
        return vehiculeRepository.findById(idVehicule).orElse(null);
    }

    @Override
    public void supprimerVehiculeById(long idVehicule) {
        vehiculeRepository.deleteById(idVehicule);
    }

    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Question 1 : démarre 5 s après le lancement de l'application,
     * puis s'exécute toutes les 60 s (cadence fixe).
     */
    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void actualiserEtatVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("[Actualisation véhicules] Nombre total de véhicules : " + vehicules.size());
        System.out.println("[Actualisation véhicules] Date et heure : " + LocalDateTime.now().format(FORMAT_DATE));
    }

    /**
     * Question 3 : rapport généré chaque lundi à 09h00
     * (cron Spring : seconde minute heure jour-du-mois mois jour-de-la-semaine).
     */
   @Scheduled(cron = "*/10 * * * * *")
    public void genererRapportVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        int total = vehicules.size();
        System.out.println("===== RAPPORT HEBDOMADAIRE DES VÉHICULES =====");
        System.out.println("Nombre total de véhicules : " + total);
        System.out.println("Généré le : " + LocalDateTime.now().format(FORMAT_DATE));
        System.out.println("==============================================");
    }
}