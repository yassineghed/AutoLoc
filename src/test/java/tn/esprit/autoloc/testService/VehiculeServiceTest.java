package tn.esprit.autoloc.testService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehiculeServiceTest {

    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void testVehiculeService() {

        // Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123-TUN-456");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Corolla");
        vehicule.setCategorie(CategorieVehicule.BERLINE);
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setTarifJournalier(
                new BigDecimal("120.00")
        );

        // Enregistrer
        Vehicule saved = vehiculeService.create(vehicule);
        Long id = saved.getIdVehicule();

        assertNotNull(id);

        // Afficher les véhicules
        vehiculeService.findAll().forEach(
                v -> System.out.println(v.getMarque())
        );

        // Rechercher
        Vehicule found = vehiculeService.findById(id);
        assertEquals("Toyota", found.getMarque());

        // Compter
        assertTrue(vehiculeService.count() >= 1);

        // Modifier le tarif
        saved.setTarifJournalier(
                new BigDecimal("150.00")
        );

        Vehicule updated = vehiculeService.update(id, saved);

        assertEquals(
                0,
                new BigDecimal("150.00").compareTo(
                        updated.getTarifJournalier()
                )
        );

        // Supprimer
        vehiculeService.deleteById(id);

        assertFalse(vehiculeService.existsById(id));

        System.out.println("Test VehiculeService réussi !");
    }
}