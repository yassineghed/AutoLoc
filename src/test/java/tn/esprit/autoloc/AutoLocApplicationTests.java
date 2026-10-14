package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.ModePaiement;
import tn.esprit.autoloc.domain.enums.RoleEmploye;
import tn.esprit.autoloc.domain.enums.StatutVehicule;

import tn.esprit.autoloc.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AutoLocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;


    // ============================================================
    // TEST 1 : AGENCE
    // ============================================================

    @Test
    void testAgenceRepository() {

        System.out.println("\n========== TEST AGENCE ==========");

        long nombreAvant = agenceRepository.count();

        // 1. Création
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setAdresse("Tunis");

        // 2. Enregistrement avec save()
        Agence agenceSaved = agenceRepository.save(agence);

        System.out.println("Agence créée : " + agenceSaved);

        assertNotNull(agenceSaved.getIdAgence());

        // 3. Lecture avec findAll()
        List<Agence> agences = agenceRepository.findAll();

        System.out.println("Liste des agences : " + agences);

        assertTrue(agences.size() >= nombreAvant + 1);

        // 4. Lecture avec findById()
        Long id = agenceSaved.getIdAgence();

        Optional<Agence> resultat =
                agenceRepository.findById(id);

        System.out.println("Agence trouvée : " + resultat);

        assertTrue(resultat.isPresent());

        // 5. Vérification avec existsById()
        boolean existe = agenceRepository.existsById(id);

        System.out.println("Agence existante : " + existe);

        assertTrue(existe);

        // 6. Comptage avec count()
        long nombreApres = agenceRepository.count();

        System.out.println("Nombre d'agences : " + nombreApres);

        assertEquals(nombreAvant + 1, nombreApres);

        // 7. Suppression avec deleteById()
        agenceRepository.deleteById(id);

        // 8. Vérification de la suppression
        boolean existeApresSuppression =
                agenceRepository.existsById(id);

        System.out.println(
                "Agence existe après suppression : "
                        + existeApresSuppression
        );

        assertFalse(existeApresSuppression);

        System.out.println("Test Agence réussi.");
    }


    // ============================================================
    // TEST 2 : CLIENT
    // ============================================================

    @Test
    void testClientRepository() {

        System.out.println("\n========== TEST CLIENT ==========");

        long nombreAvant = clientRepository.count();

        // Identifiant unique pour éviter les doublons entre exécutions
        String suffix = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        // 1. Création du premier client
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed." + suffix + "@example.com");
        client1.setTelephone("22111222");
        client1.setNumPermis("TN1" + suffix);
        client1.setDateInscription(LocalDate.now());

        // 2. Création du deuxième client
        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Yassine");
        client2.setEmail("yassine." + suffix + "@example.com");
        client2.setTelephone("22333444");
        client2.setNumPermis("TN2" + suffix);
        client2.setDateInscription(LocalDate.now());

        // 3. Enregistrement avec save()
        Client clientSaved1 = clientRepository.save(client1);
        Client clientSaved2 = clientRepository.save(client2);

        System.out.println("Client 1 créé : " + clientSaved1);
        System.out.println("Client 2 créé : " + clientSaved2);

        assertNotNull(clientSaved1.getIdClient());
        assertNotNull(clientSaved2.getIdClient());

        // 4. Lecture avec findAll()
        List<Client> clients = clientRepository.findAll();

        System.out.println("Liste des clients : " + clients);

        assertTrue(clients.size() >= nombreAvant + 2);

        // 5. Lecture avec findById()
        Long idClient = clientSaved1.getIdClient();

        Optional<Client> resultat =
                clientRepository.findById(idClient);

        System.out.println("Client trouvé : " + resultat);

        assertTrue(resultat.isPresent());

        // 6. existsById() avec un identifiant existant
        boolean existe = clientRepository.existsById(idClient);

        System.out.println("Client existant : " + existe);

        assertTrue(existe);

        // 7. existsById() avec un identifiant inexistant
        boolean clientInexistant =
                clientRepository.existsById(-1L);

        System.out.println(
                "Client avec ID -1 existe : " + clientInexistant
        );

        assertFalse(clientInexistant);

        // 8. Comptage avec count()
        long nombreApres = clientRepository.count();

        System.out.println("Nombre de clients : " + nombreApres);

        assertEquals(nombreAvant + 2, nombreApres);

        System.out.println("Test Client réussi.");
    }


    // ============================================================
    // TEST 3 : VEHICULE
    // ============================================================

    @Test
    void testVehiculeRepository() {

        System.out.println("\n========== TEST VEHICULE ==========");

        long nombreAvant = vehiculeRepository.count();

        // 1. Création du véhicule
        String suffix = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        Vehicule vehicule = new Vehicule();

        vehicule.setImmatriculation("TUN-" + suffix);
        vehicule.setMarque("Toyota");
        vehicule.setModele("Corolla");
        vehicule.setCategorie(CategorieVehicule.BERLINE);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        // 2. Enregistrement avec save()
        Vehicule vehiculeSaved =
                vehiculeRepository.save(vehicule);

        Long idVehicule = vehiculeSaved.getIdVehicule();

        System.out.println("Véhicule créé : " + vehiculeSaved);

        assertNotNull(idVehicule);

        // 3. Lecture avec findAll()
        List<Vehicule> vehicules = vehiculeRepository.findAll();

        System.out.println("Liste des véhicules : " + vehicules);

        assertTrue(vehicules.size() >= nombreAvant + 1);

        // 4. Comptage avec count()
        long nombreApresCreation = vehiculeRepository.count();

        System.out.println(
                "Nombre de véhicules : " + nombreApresCreation
        );

        assertEquals(nombreAvant + 1, nombreApresCreation);

        // 5. Modification d'une propriété
        vehiculeSaved.setTarifJournalier(
                new BigDecimal("150.00")
        );

        Vehicule vehiculeUpdated =
                vehiculeRepository.save(vehiculeSaved);

        System.out.println("Véhicule modifié : " + vehiculeUpdated);

        // 6. Vérification de la modification en base
        Optional<Vehicule> resultat =
                vehiculeRepository.findById(idVehicule);

        assertTrue(resultat.isPresent());

        assertEquals(
                0,
                new BigDecimal("150.00").compareTo(
                        resultat.get().getTarifJournalier()
                )
        );

        System.out.println(
                "Nouveau tarif journalier : "
                        + resultat.get().getTarifJournalier()
        );

        // 7. Suppression avec deleteById()
        vehiculeRepository.deleteById(idVehicule);

        // 8. Vérification de la suppression
        boolean existeApresSuppression =
                vehiculeRepository.existsById(idVehicule);

        System.out.println(
                "Véhicule existe après suppression : "
                        + existeApresSuppression
        );

        assertFalse(existeApresSuppression);

        System.out.println("Test Vehicule réussi.");
    }
    @Autowired
    private IEmployeRepository employeRepository;

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private IPaiementRepository paiementRepository;

    @Test
    void testEmployeRepository() {

        System.out.println("\n========== TEST EMPLOYE ==========");

        // 1. Création d'un employé
        Employe employe = new Employe();

        employe.setNom("Ben Ali");
        employe.setPrenom("Sami");
        employe.setRole(RoleEmploye.values()[0]);

        // 2. Enregistrement avec save()
        Employe employeSaved = employeRepository.save(employe);

        Long id = employeSaved.getIdEmploye();

        System.out.println("Employé créé : " + employeSaved);

        assertNotNull(id);

        // 3. Lecture avec findAll()
        List<Employe> employes = employeRepository.findAll();

        System.out.println("Liste des employés : " + employes);

        assertTrue(
                employes.stream().anyMatch(e -> e.getIdEmploye().equals(id))
        );

        // 4. Lecture avec findById()
        Optional<Employe> resultat = employeRepository.findById(id);

        System.out.println("Employé trouvé : " + resultat);

        assertTrue(resultat.isPresent());

        // 5. Vérification avec existsById()
        assertTrue(employeRepository.existsById(id));

        System.out.println("Employé existe : true");

        // 6. Suppression avec deleteById()
        employeRepository.deleteById(id);

        // 7. Vérification de la suppression
        assertFalse(employeRepository.existsById(id));

        System.out.println("Employé supprimé avec succès.");
    }


    // ============================================================
    // TEST 2 : MAINTENANCE
    // ============================================================

    @Test
    void testMaintenanceRepository() {

        System.out.println("\n========== TEST MAINTENANCE ==========");

        // 1. Création d'une maintenance
        Maintenance maintenance = new Maintenance();

        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(2));
        maintenance.setDescription("Révision et contrôle du véhicule");

        // 2. Enregistrement avec save()
        Maintenance maintenanceSaved =
                maintenanceRepository.save(maintenance);

        Long id = maintenanceSaved.getIdMaintenance();

        System.out.println("Maintenance créée : " + maintenanceSaved);

        assertNotNull(id);

        // 3. Lecture avec findAll()
        List<Maintenance> maintenances =
                maintenanceRepository.findAll();

        System.out.println("Liste des maintenances : " + maintenances);

        assertTrue(
                maintenances.stream()
                        .anyMatch(m -> m.getIdMaintenance().equals(id))
        );

        // 4. Lecture avec findById()
        Optional<Maintenance> resultat =
                maintenanceRepository.findById(id);

        System.out.println("Maintenance trouvée : " + resultat);

        assertTrue(resultat.isPresent());

        // 5. Vérification avec existsById()
        assertTrue(maintenanceRepository.existsById(id));

        System.out.println("Maintenance existe : true");

        // 6. Suppression avec deleteById()
        maintenanceRepository.deleteById(id);

        // 7. Vérification de la suppression
        assertFalse(maintenanceRepository.existsById(id));

        System.out.println("Maintenance supprimée avec succès.");
    }


    // ============================================================
    // TEST 3 : PAIEMENT
    // ============================================================

    @Test
    void testPaiementRepository() {

        System.out.println("\n========== TEST PAIEMENT ==========");

        // 1. Création d'un paiement
        Paiement paiement = new Paiement();

        paiement.setMontant(new BigDecimal("250.00"));
        paiement.setDatePaiement(LocalDate.now());
        paiement.setModePaiement(ModePaiement.CARTE);

        // 2. Enregistrement avec save()
        Paiement paiementSaved = paiementRepository.save(paiement);

        Long id = paiementSaved.getIdPaiement();

        System.out.println("Paiement créé : " + paiementSaved);

        assertNotNull(id);

        // 3. Lecture avec findAll()
        List<Paiement> paiements = paiementRepository.findAll();

        System.out.println("Liste des paiements : " + paiements);

        assertTrue(
                paiements.stream()
                        .anyMatch(p -> p.getIdPaiement().equals(id))
        );

        // 4. Lecture avec findById()
        Optional<Paiement> resultat = paiementRepository.findById(id);

        System.out.println("Paiement trouvé : " + resultat);

        assertTrue(resultat.isPresent());

        // 5. Vérification avec existsById()
        assertTrue(paiementRepository.existsById(id));

        System.out.println("Paiement existe : true");

        // 6. Suppression avec deleteById()
        paiementRepository.deleteById(id);

        // 7. Vérification de la suppression
        assertFalse(paiementRepository.existsById(id));

        System.out.println("Paiement supprimé avec succès.");
    }


}