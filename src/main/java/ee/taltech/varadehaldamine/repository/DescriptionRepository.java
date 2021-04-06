package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, String> {

    Description findDescriptionByAssetId(String assetId);
}
