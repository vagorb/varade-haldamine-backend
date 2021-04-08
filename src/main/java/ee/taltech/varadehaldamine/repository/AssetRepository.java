package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

    Asset findAssetById(String assetId);

    String assetInfoCreate = "SELECT new ee.taltech.varadehaldamine.modelDTO.AssetInfoShort(A.id, A.name, " +
            "CONCAT(P.structuralUnit, ' ', P.subdivision), CONCAT(C.mainClass, ' ', C.subClass)," +
            " CONCAT(Ad.buildingAbbreviature, ' ', Ad.room) , A.expirationDate, A.active)";
    String table = " FROM Asset AS A JOIN Address AS Ad ON A.id = Ad.assetId " +
            "JOIN Classification AS C ON A.subClass = C.subClass JOIN Possessor AS P ON A.possessorId = P.id";
    String where = " WHERE";
    String andStr = " AND";
    String checkId = " LOWER(A.id) LIKE ?1";
    String checkName = " LOWER(A.name) LIKE ?2";
    String checkClass = " (LOWER(C.subClass) LIKE ?3 OR LOWER(C.mainClass) LIKE ?3)";
    String checkAddress = " (LOWER(Ad.buildingAbbreviature) LIKE ?4 OR LOWER(Ad.room) LIKE ?4)";
    String checkActive = " A.active = ?5";

    @Query(assetInfoCreate + table)
    List<AssetInfoShort> getAll();

    @Query(assetInfoCreate + table + where + checkId + andStr + checkName + andStr + checkClass + andStr + checkAddress)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(String id, String name, String classification, String address, PageRequest pageRequest);

    @Query(assetInfoCreate + table + where + checkId + andStr + checkName + andStr + checkClass + andStr + checkAddress + andStr + checkActive)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoDivision(String id, String name, String classification, String address, Boolean active, PageRequest pageRequest);

    @Query(assetInfoCreate + table + where + checkId + andStr + checkName + andStr + checkClass + andStr + checkAddress + andStr +" (P.structuralUnit = ?5 OR P.subdivision = ?5)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActive(String id, String name, String classification, String address, Integer division, PageRequest pageRequest);

    @Query(assetInfoCreate + table + where + checkId + andStr + checkName + andStr + checkClass + andStr + checkAddress + andStr + checkActive + andStr + " (P.structuralUnit = ?6 OR P.subdivision = ?6)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsAll(String id, String name, String classification, String address, Boolean active, Integer division, PageRequest pageRequest);

    @Query("SELECT new ee.taltech.varadehaldamine.modelDTO.AssetInfo(A.id, A.name, A.active, A.userId, A.possessorId, " +
            "A.expirationDate, A.delicateCondition, A.checked, A.createdAt, A.modifiedAt, W.price, W.residualPrice, " +
            "W.purchaseDate, C.subClass, C.mainClass, K.majorAssetId, Ad.buildingAbbreviature, Ad.room, D.text," +
            " P.firstname, P.lastname, Po.structuralUnit, Po.subdivision) FROM Asset AS A LEFT JOIN Worth AS W ON A.id = W.assetId LEFT JOIN Classification AS C ON A.subClass = C.subClass LEFT JOIN KitRelation AS K ON A.id = K.componentAssetId LEFT JOIN Address AS Ad ON A.id = Ad.assetId LEFT JOIN Description AS D ON A.id = D.assetId LEFT JOIN Person AS P ON A.userId = P.id LEFT JOIN Possessor AS Po ON A.possessorId = Po.id WHERE A.id = ?1")
    AssetInfo getAssetInfoById(String id);

}
