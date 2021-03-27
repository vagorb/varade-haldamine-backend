package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.PossessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("possessor")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PossessorController {

    @Autowired
    PossessorService possessorService;

    @GetMapping
    public List<Possessor> getAll() {
        return possessorService.findAll();
    }

    @PostMapping
    public Possessor addPerson(@RequestBody PossessorInfo possessor){
        return possessorService.addPossessor(possessor);
    }
}
