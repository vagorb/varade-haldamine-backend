package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.model.Person;
import ee.taltech.varadehaldamine.modelDTO.PersonInfo;
import ee.taltech.varadehaldamine.service.PersonService;
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
    public Person getPersonById(@RequestParam Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person addPerson(@RequestBody PersonInfo person) {
        return personService.addPerson(person);
    }
}
