package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidCommentException;
import ee.taltech.varadehaldamine.exception.InvalidPersonException;
import ee.taltech.varadehaldamine.model.Person;
import ee.taltech.varadehaldamine.modelDTO.PersonInfo;
import ee.taltech.varadehaldamine.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long assetId) {
        return personRepository.findPersonById(assetId);
    }

    public Person addPerson(PersonInfo personInfo) {
        try {
            if (personInfo != null && !personInfo.getAzureId().isBlank() && !personInfo.getFirstname().isBlank() && !personInfo.getLastname().isBlank()) {
                Person person = new Person(personInfo.getAzureId(), personInfo.getFirstname(), personInfo.getLastname());
                return personRepository.save(person);
            } else {
                throw new InvalidPersonException("Error when saving Person");
            }
        } catch (InvalidCommentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Person findPersonByFirstLastName (String firstName, String lastName) {
        List<Person> all = findAll();
        for (Person person : all) {
            if (person.getFirstname().equals(firstName) && person.getLastname().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

}
