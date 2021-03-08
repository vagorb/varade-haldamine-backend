package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Vara;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.VaradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("vara")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VaradeController {

    @Autowired
    private VaradeService varadeService;

    @PostMapping
    public void saveVara(@RequestBody Vara vara) {
        varadeService.save(vara);
    }

    @CrossOrigin(origins="http://localhost:8080")
    @GetMapping("/user")
    public List<Vara> getAll() {
        return varadeService.findAll();
    }

    @CrossOrigin(origins="http://localhost:8080")
    @GetMapping("user2")
    public List<Vara> getall2() {
        return varadeService.findAll();
    }

}
