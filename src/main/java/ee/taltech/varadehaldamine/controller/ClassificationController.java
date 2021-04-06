package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.Classification;
import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("class")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    @GetMapping
    public List<Classification> getAll() {
        return classificationService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> addClassification(@RequestBody ClassificationInfo classification) {
        if (classificationService.addClassification(classification) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
