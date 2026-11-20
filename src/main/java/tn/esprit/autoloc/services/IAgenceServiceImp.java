package tn.esprit.autoloc.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.entities.Agence;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.repositories.AgenceRepository;
import tn.esprit.autoloc.repositories.VehiculeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IAgenceServiceImp implements IAgenceService {

    AgenceRepository agenceRepository;
    VehiculeRepository vehiculeRepository;

    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    /**
     * Scénario 2 : crée l'agence, enregistre ses véhicules puis affecte chaque
     * véhicule à l'agence (le côté propriétaire de la relation est Vehicule.agence).
     * Le tout dans une seule transaction : si un véhicule échoue, rien n'est enregistré.
     */
    @Override
    @Transactional
    public Agence ajouterAgenceEtAffecterVehicules(Agence agence) {
        List<Vehicule> vehicules = agence.getVehicules();
        agence.setVehicules(null);                       // l'agence est sauvegardée seule d'abord
        Agence agenceSauvegardee = agenceRepository.save(agence);

        List<Vehicule> vehiculesSauvegardes = new ArrayList<>();
        if (vehicules != null) {
            for (Vehicule vehicule : vehicules) {
                vehicule.setAgence(agenceSauvegardee);   // affectation
                vehiculesSauvegardes.add(vehiculeRepository.save(vehicule));
            }
        }
        agenceSauvegardee.setVehicules(vehiculesSauvegardes);
        return agenceSauvegardee;
    }

    @Override
    public Agence modifierAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public List<Agence> consulterAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence consulterAgenceById(long idAgence) {
        return agenceRepository.findById(idAgence).orElse(null);
    }

    @Override
    public void supprimerAgenceById(long idAgence) {
        agenceRepository.deleteById(idAgence);
    }
}