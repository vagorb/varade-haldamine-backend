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

    /**
     * Method to get all users.
     *
     * @return all users
     */
    @GetMapping
    public List<Person> getAll() {
        return personService.findAll();
    }

    /**
     * Method to get user by user id.
     *
     * @param id person unique id
     * @return person
     */
    @GetMapping("id")
    public Person getPersonById(@RequestParam Long id) {
        return personService.getPersonById(id);
    }

    /**
     * Method to add new user to system.
     *
     * @param person person to add
     * @return added person
     */
    @PostMapping
    public Person addPerson(@RequestBody PersonInfo person) {
        return personService.addPerson(person);
    }
}
