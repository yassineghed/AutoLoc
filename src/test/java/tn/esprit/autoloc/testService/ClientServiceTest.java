package tn.esprit.autoloc.testService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    void testClientService() {

        // Créer deux clients
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.test@example.com");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sami");
        c2.setEmail("sami.test@example.com");
        c2.setDateInscription(LocalDate.now());

        // Enregistrer
        Client saved1 = clientService.create(c1);
        Client saved2 = clientService.create(c2);

        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        // Afficher tous les clients
        clientService.findAll().forEach(
                c -> System.out.println(c.getNom())
        );

        // Rechercher un client
        Client found = clientService.findById(
                saved1.getIdClient()
        );

        assertEquals("Ben Ali", found.getNom());

        // Compter les clients
        assertTrue(clientService.count() >= 2);

        // Vérifier l'existence
        assertTrue(
                clientService.existsById(saved1.getIdClient())
        );

        // Supprimer les clients créés
        clientService.deleteById(saved1.getIdClient());
        clientService.deleteById(saved2.getIdClient());

        assertFalse(
                clientService.existsById(saved1.getIdClient())
        );

        System.out.println("Test ClientService réussi !");
    }
}