package tn.esprit.autoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Component
public class TarifStandardStrategy implements ITarificationStrategy {

    @Override
    public BigDecimal calculerTarif(
            Vehicule vehicule,
            Reservation reservation) {

        // Calculer le nombre de jours de location
        long jours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin()
        );

        // Facturer au minimum un jour
        jours = Math.max(jours, 1);

        // Calculer le tarif standard sans réduction
        BigDecimal tarifBrut = vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(jours));

        return tarifBrut;
    }

    @Override
    public String getCode() {
        return "STANDARD";
    }
}