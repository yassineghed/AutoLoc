package tn.esprit.autoloc.service.observer;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;

@Component
public class EmailNotificationObserver implements ReservationObserver {

    @Override
    public void notifier(Reservation reservation) {

        System.out.println(
                "Notification EMAIL pour la réservation : "
                        + reservation.getIdReservation()
        );
    }
}