package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {

        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant"
            );
        }

        verifierVehicule(vehicule);

        // Find the existing agency in MySQL
        if (vehicule.getAgence() != null) {

            Long agenceId = vehicule.getAgence().getIdAgence();

            if (agenceId == null) {
                throw new IllegalArgumentException(
                        "L'identifiant de l'agence est obligatoire"
                );
            }

            Agence agence = agenceRepository.findById(agenceId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Agence", agenceId)
                    );

            vehicule.setAgence(agence);
        }

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }
    @Override
    public long count() {
        return vehiculeRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return vehiculeRepository.existsById(id);
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        verifierVehicule(vehicule);
        Vehicule existant = findById(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setCategorie(vehicule.getCategorie());
        existant.setStatut(vehicule.getStatut());
        // agence, reservations, maintenances et equipements ne sont pas recopiés :
        // la gestion des liens est l'objet du Chapitre 9
        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void verifierVehicule(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}
