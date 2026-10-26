package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // GET /api/vehicules
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    // GET /api/vehicules/{id}
    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    // POST /api/vehicules
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule nouveauVehicule) {
        return vehiculeService.create(nouveauVehicule);
    }

    // PUT /api/vehicules/{id}
    @PutMapping("/{id}")
    public Vehicule update(
            @PathVariable Long id,
            @RequestBody Vehicule vehiculeModifie) {

        return vehiculeService.update(id, vehiculeModifie);
    }

    // DELETE /api/vehicules/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}