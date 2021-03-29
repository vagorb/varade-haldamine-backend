package ee.taltech.varadehaldamine.Varadehaldamine.Repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossessorRepository extends JpaRepository<Possessor, Long> {

    Possessor findPossessorById(Long id);
}
