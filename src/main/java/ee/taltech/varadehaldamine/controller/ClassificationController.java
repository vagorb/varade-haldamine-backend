package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.Classification;
import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("class")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    /**
     * Method to see add classifications.
     * <p>
     * Roles: only Raamatupidaja
     *
     * @return Classification list
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @GetMapping
    public List<Classification> getAll() {
        return classificationService.findAll();
    }

    /**
     * Method to register new classification.
     * <p>
     * Roles: only Raamatupidaja.
     *
     * @param classification information of new classification
     * @return message to front-end, the classification is added or not
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PostMapping
    public ResponseEntity<Object> addClassification(@RequestBody ClassificationInfo classification) {
        System.out.println("dopustim");
        if (classificationService.addClassification(classification) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Method to change data of the classification.
     * <p>
     * Roles: only Raamatupidaja.
     *
     * @param classification information of new data to classification fields
     * @param subClass       subclass to change it
     * @return changed classification
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PutMapping("/{subclass}")
    public Classification updateClassification(@RequestBody Classification classification, @PathVariable String subClass) {
        return classificationService.update(classification, subClass);
    }
}
