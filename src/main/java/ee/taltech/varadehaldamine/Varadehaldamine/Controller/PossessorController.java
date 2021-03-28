package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.PossessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addPerson(@RequestBody PossessorInfo possessor) {
        if (possessorService.addPossessor(possessor) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
