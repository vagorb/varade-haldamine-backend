package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.Possessor;
import ee.taltech.varadehaldamine.modelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.service.PossessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("possessor")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PossessorController {

    @Autowired
    PossessorService possessorService;

    /**
     * Method to get all possessors.
     *
     * Roles: only Raamatupidaja.
     *
     * @return List of possessors
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @GetMapping
    public List<Possessor> getAll() {
        return possessorService.findAll();
    }

    /**
     * Method to add new possessor.
     *
     * Roles: only Raamatupidaja.
     *
     * @param possessor information of the new possessor
     * @return message to front-end, the possessor is added or not
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PostMapping
    public ResponseEntity<Object> addPossessor(@RequestBody PossessorInfo possessor) {
        if (possessorService.addPossessor(possessor) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Method to update data of the possessor.
     *
     * Roles: only Raamatupidaja.
     *
     * @param possessor information about fields to change
     * @param id possessor id to change
     * @return changed possessor
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PutMapping("/{id}")
    public Possessor updatePossessor(@RequestBody Possessor possessor, @PathVariable Long id) {
        return possessorService.update(possessor, id);
    }
}
