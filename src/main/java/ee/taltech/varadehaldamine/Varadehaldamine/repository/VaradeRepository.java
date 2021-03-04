package ee.taltech.varadehaldamine.Varadehaldamine.repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Vara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaradeRepository extends JpaRepository<Vara, Long> {
}
