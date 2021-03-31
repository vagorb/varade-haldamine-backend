package ee.taltech.varadehaldamine.Varadehaldamine.Repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, String> {

    Classification findClassificationBySubClass(String subClass);
}
