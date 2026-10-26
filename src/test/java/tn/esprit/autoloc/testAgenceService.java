package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setAdresse("Monastir");

        // 2. Enregistrer l'agence
        Agence agenceEnregistree = agenceService.create(agence);
        System.out.println("Agence créée : " + agenceEnregistree);

        Long id = agenceEnregistree.getIdAgence();

        // 3. Afficher toutes les agences
        System.out.println("Liste des agences :");
        agenceService.findAll().forEach(System.out::println);

        // 4. Rechercher l'agence par son identifiant
        Agence agenceTrouvee = agenceService.findById(id);
        System.out.println("Agence trouvée : " + agenceTrouvee);

        // 5. Afficher le nombre d'agences
        System.out.println("Nombre d'agences : " + agenceService.count());

        // 6. Supprimer l'agence
        agenceService.deleteById(id);

        // 7. Vérifier la suppression
        System.out.println(
                "Agence supprimée : " +
                        !agenceService.existsById(id)
        );
    }
}