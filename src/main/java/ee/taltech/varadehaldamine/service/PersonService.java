package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidCommentException;
import ee.taltech.varadehaldamine.exception.InvalidPersonException;
import ee.taltech.varadehaldamine.model.Person;
import ee.taltech.varadehaldamine.modelDTO.PersonInfo;
import ee.taltech.varadehaldamine.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
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
            if (personInfo != null && !personInfo.getUsername().isBlank() && !personInfo.getUsername().isBlank()) {
                Person person = new Person(personInfo.getUsername(), personInfo.getEmail());
                return personRepository.save(person);
            } else {
                throw new InvalidPersonException("Error when saving Person");
            }
        } catch (InvalidCommentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Person getCurrentUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof DefaultOidcUser) {
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) userObject;
            Person currentUser = getUserByEmail(defaultOidcUser.getPreferredUsername());
            if (currentUser == null) {
                currentUser = registerNewUser(defaultOidcUser.getName(), defaultOidcUser.getPreferredUsername());
            }
            return currentUser;
        }
        return null;
    }

    private Person getUserByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }

    private Person registerNewUser(String username, String email) {
        return personRepository.save(new Person(username, email));
    }

    public List<String> getAuthorities() {
        Collection<? extends GrantedAuthority> listOfAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> authorities = new LinkedList<>();
        for (GrantedAuthority role: listOfAuthorities){
            authorities.add(role.getAuthority().toString());
        }
        return authorities;
    }

}
