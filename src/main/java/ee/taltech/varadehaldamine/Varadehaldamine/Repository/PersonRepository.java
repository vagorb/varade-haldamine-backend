package ee.taltech.varadehaldamine.Varadehaldamine.Repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String > {

}
