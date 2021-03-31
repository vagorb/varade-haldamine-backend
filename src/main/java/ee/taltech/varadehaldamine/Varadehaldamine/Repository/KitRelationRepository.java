package ee.taltech.varadehaldamine.Varadehaldamine.Repository;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.KitRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitRelationRepository extends JpaRepository<KitRelation, String> {

    KitRelation findKitRelationByComponentAssetId(String componentAssetId);
}
