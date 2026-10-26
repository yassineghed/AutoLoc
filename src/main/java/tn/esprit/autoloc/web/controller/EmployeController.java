package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    // GET /api/employes
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    // GET /api/employes/{id}
    @GetMapping("/{id}")
    public Employe findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    // POST /api/employes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe nouvelEmploye) {
        return employeService.create(nouvelEmploye);
    }

    // PUT /api/employes/{id}
    @PutMapping("/{id}")
    public Employe update(
            @PathVariable Long id,
            @RequestBody Employe employeModifie) {

        return employeService.update(id, employeModifie);
    }

    // DELETE /api/employes/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}