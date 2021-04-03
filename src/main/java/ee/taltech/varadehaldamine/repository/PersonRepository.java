package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Person findPersonById(Long id);
}
