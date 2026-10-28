package tn.esprit.autoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Reservation create(Reservation reservation) {

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "A new reservation must not have an ID"
            );
        }

        verifierReservation(reservation);

        // Find the existing client
        if (reservation.getClient() == null ||
                reservation.getClient().getIdClient() == null) {
            throw new IllegalArgumentException("Client ID is required");
        }

        Long clientId = reservation.getClient().getIdClient();

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client", clientId)
                );

        reservation.setClient(client);

        // Find the existing vehicle
        if (reservation.getVehicule() == null ||
                reservation.getVehicule().getIdVehicule() == null) {
            throw new IllegalArgumentException("Vehicle ID is required");
        }

        Long vehiculeId = reservation.getVehicule().getIdVehicule();

        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", vehiculeId)
                );

        reservation.setVehicule(vehicule);

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id)
                );
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {

        // Vérifier les dates reçues
        verifierReservation(reservation);

        // Rechercher la réservation existante
        Reservation existante = findById(id);

        // Modifier les attributs
        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        // Ne pas modifier les associations ici :
        // client, vehicule et contrat sont gérés séparément

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }
    private void verifierReservation(Reservation reservation) {

        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {

            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }
}
