package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Possessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PossessorRepository extends JpaRepository<Possessor, Long> {

    Possessor findPossessorById(Long id);
}
