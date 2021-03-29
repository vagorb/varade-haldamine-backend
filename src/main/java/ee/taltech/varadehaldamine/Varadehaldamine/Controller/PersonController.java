package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Person;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PersonInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("id")
    public Person getPersonById(@RequestParam Long id){
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person addPerson(@RequestBody PersonInfo person){
        return personService.addPerson(person);
    }
}
