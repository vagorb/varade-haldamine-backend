package ee.taltech.varadehaldamine.Varadehaldamine.Repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Worth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorthRepository extends JpaRepository<Worth, String> {

    Worth findWorthByAssetId(String assetId);
}
