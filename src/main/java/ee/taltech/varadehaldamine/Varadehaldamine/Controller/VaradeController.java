package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Vara;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.VaradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("Vara")
@RestController
public class VaradeController {

    @Autowired
    private VaradeService varadeService;

    @PostMapping
    public void saveVara(@RequestBody Vara vara) {
        varadeService.save(vara);
    }

}
