package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Classification addClassification(@RequestBody ClassificationInfo classification){
        return classificationService.addClassification(classification);
    }
}
