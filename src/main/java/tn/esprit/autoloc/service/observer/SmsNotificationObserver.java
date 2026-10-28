package tn.esprit.autoloc.service.observer;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;

@Component
public class SmsNotificationObserver implements ReservationObserver {

    @Override
    public void notifier(Reservation reservation) {

        System.out.println(
                "Notification SMS pour la réservation : "
                        + reservation.getIdReservation()
        );
    }
}