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

    /**
     * Method to get all users from db.
     *
     * @return all persons
     */
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    /**
     * Method to get person by id.
     *
     * @param assetId asset id
     * @return person from db
     */
    public Person getPersonById(Long assetId) {
        return personRepository.findPersonById(assetId);
    }

    /**
     * Method to add new person in db.
     *
     * @param personInfo person information holder
     * @return added person
     */
    public Person addPerson(PersonInfo personInfo) {
        try {
            if (personInfo != null && !personInfo.getUsername().isBlank() && !personInfo.getUsername().isBlank()) {
                Person person = new Person(personInfo.getUsername(), personInfo.getEmail(), "wrong, call Ilja Or Police " + personInfo.getEmail());
                return personRepository.save(person);
            } else {
                throw new InvalidPersonException("Error when saving Person");
            }
        } catch (InvalidCommentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method to get current user information from db.
     *
     * @return current user
     */
    public Person getCurrentUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userObject instanceof DefaultOidcUser) {
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) userObject;
            String azureId = defaultOidcUser.getIdToken().getClaim("oid");
            Person currentUser = getUserByAzureToken(azureId);
            if (currentUser == null) {
                currentUser = registerNewUser(defaultOidcUser.getName(), defaultOidcUser.getPreferredUsername().toLowerCase(), azureId);
            }
            return currentUser;
        }
        return null;
    }

    /**
     * Method to get person by azure token.
     *
     * @param token azure token
     * @return person
     */
    private Person getUserByAzureToken(String token) {
        return personRepository.findPersonByAzureId(token);
    }

    /**
     * Method to add person not by PersonInfo.
     *
     * @param username person username
     * @param email    person email
     * @param azureId  person azure id
     * @return added person
     */
    private Person registerNewUser(String username, String email, String azureId) {
        return personRepository.save(new Person(username, email, azureId));
    }

    /**
     * Method to get all roles of current user.
     *
     * @return list of roles
     */
    public List<String> getAuthorities() {
        Collection<? extends GrantedAuthority> listOfAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> authorities = new LinkedList<>();
        for (GrantedAuthority role : listOfAuthorities) {
            authorities.add(role.getAuthority());
        }
        return authorities;
    }

}
