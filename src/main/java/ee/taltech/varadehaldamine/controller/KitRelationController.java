package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.KitRelation;
import ee.taltech.varadehaldamine.modelDTO.CommentInfo;
import ee.taltech.varadehaldamine.service.KitRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("kit")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KitRelationController {

    @Autowired
    KitRelationService kitRelationService;

    @GetMapping("majorAssets")
    public List<String> getAllMajorAssets() {
        return kitRelationService.findAllMajorAssets();
    }


}
