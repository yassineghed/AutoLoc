package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeService {

    Vehicule create(Vehicule vehicule);

    Vehicule findById(Long id);

    List<Vehicule> findAll();
    
    long count();

    boolean existsById(Long id);

    Vehicule update(Long id, Vehicule vehicule);

    void deleteById(Long id);
}
