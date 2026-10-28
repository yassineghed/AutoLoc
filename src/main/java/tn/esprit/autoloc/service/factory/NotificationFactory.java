package tn.esprit.autoloc.service.factory;

import org.springframework.stereotype.Component;

import tn.esprit.autoloc.service.observer
        .EmailNotificationObserver;
import tn.esprit.autoloc.service.observer.SmsNotificationObserver;
import tn.esprit.autoloc.service.observer.ReservationObserver;

@Component
public class NotificationFactory {

    private final EmailNotificationObserver emailNotificationObserver;
    private final SmsNotificationObserver smsNotificationObserver;

    public NotificationFactory(
            EmailNotificationObserver emailNotificationObserver,
            SmsNotificationObserver smsNotificationObserver) {

        this.emailNotificationObserver = emailNotificationObserver;
        this.smsNotificationObserver = smsNotificationObserver;
    }

    public ReservationObserver creerObservateur(String canal) {

        if (canal == null) {
            throw new IllegalArgumentException(
                    "Le canal de notification ne peut pas être null"
            );
        }

        if (canal.equalsIgnoreCase("EMAIL")) {
            return emailNotificationObserver;
        }

        if (canal.equalsIgnoreCase("SMS")) {
            return smsNotificationObserver;
        }

        throw new IllegalArgumentException(
                "Canal de notification inconnu : " + canal
        );
    }
}