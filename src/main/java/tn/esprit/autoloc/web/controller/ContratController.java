package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // GET /api/contrats
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // GET /api/contrats/{id}
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    // POST /api/contrats
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat nouveauContrat) {
        return contratService.create(nouveauContrat);
    }

    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contratModifie) {

        return contratService.update(id, contratModifie);
    }

    // DELETE /api/contrats/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}