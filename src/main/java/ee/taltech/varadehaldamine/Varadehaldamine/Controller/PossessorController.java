package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.PossessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
