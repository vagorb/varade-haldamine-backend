package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, String> {

    Classification findClassificationBySubClass(String subClass);
}
