package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Paiement;

/**
 * Lecture seule : la création, la modification et la suppression d'un paiement
 * passent par le Contrat (cascade).
 */
public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}
