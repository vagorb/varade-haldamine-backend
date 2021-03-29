package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Person;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PersonInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person getPersonById(Long assetId){
        return personRepository.findPersonById(assetId);
    }

    public Person addPerson(PersonInfo personInfo){
        try {
            if (personInfo != null && !personInfo.getAzureId().isBlank() && !personInfo.getFirstname().isBlank() && !personInfo.getLastname().isBlank()){
                Person person = new Person(personInfo.getAzureId(), personInfo.getFirstname(), personInfo.getLastname());
                return personRepository.save(person);
            }
        } catch (Exception ignored) {}
        return null;
    }

}
