package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.ClassificationService;
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
