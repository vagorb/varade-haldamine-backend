package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Worth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorthRepository extends JpaRepository<Worth, String> {

    Worth findWorthByAssetId(String assetId);
}
