package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Person;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("person")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping
    public List<Person> getAll() {
        return personService.findAll();
    }
}
