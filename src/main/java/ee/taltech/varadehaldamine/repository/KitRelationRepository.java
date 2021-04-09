package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.KitRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitRelationRepository extends JpaRepository<KitRelation, String> {

    KitRelation findKitRelationByComponentAssetId(String componentAssetId);

    @Query("SELECT DISTINCT majorAssetId FROM KitRelation")
    List<String> findAllMajorAssets();
}
