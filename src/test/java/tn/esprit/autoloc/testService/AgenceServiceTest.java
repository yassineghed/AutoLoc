package tn.esprit.autoloc.testService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {

        // 1. Création
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setVille("Monastir");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("73111222");

        Agence saved = agenceService.create(agence);
        Long id = saved.getIdAgence();

        assertNotNull(id);
        System.out.println("Agence créée : " + id);

        // 2. Liste
        assertTrue(
                agenceService.findAll().stream()
                        .anyMatch(a -> a.getIdAgence().equals(id))
        );

        // 3. Recherche
        Agence found = agenceService.findById(id);
        assertEquals("Agence Test", found.getNom());

        // 4. Comptage
        assertTrue(agenceService.count() >= 1);

        // 5. Existence
        assertTrue(agenceService.existsById(id));

        // 6. Suppression
        agenceService.deleteById(id);

        assertFalse(agenceService.existsById(id));
        System.out.println("Test AgenceService réussi !");
    }
}