package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

    Asset findAssetById(String assetId);

    String assetInfoShortCreate = "SELECT new ee.taltech.varadehaldamine.modelDTO.AssetInfoShort(A.id, A.name, " +
            "P.structuralUnit, P.subdivision, CONCAT(C.mainClass, ' ', C.subClass)," +
            " A.buildingAbbreviature, A.room, A.expirationDate, A.active)";
    String assetInfoCreate = "SELECT new ee.taltech.varadehaldamine.modelDTO.AssetInfo(A.id, A.name, A.active, A.userId, A.possessorId, " +
            "A.expirationDate, A.delicateCondition, A.checked, A.createdAt, A.modifiedAt, A.price, A.residualPrice, " +
            "A.purchaseDate, C.subClass, C.mainClass, K.majorAssetId, A.buildingAbbreviature, A.room, A.description, " +
            "P.firstname, P.lastname, Po.structuralUnit, Po.subdivision)";

    String tableFromAssetAddressClassPossessor = " FROM Asset AS A JOIN Classification AS C ON A.subClass = C.subClass JOIN Possessor AS P ON A.possessorId = P.id";
    String tableFromAllTables = " FROM Asset AS A LEFT JOIN Classification AS C ON A.subClass = C.subClass " +
            "LEFT JOIN KitRelation AS K ON A.id = K.componentAssetId " +
            "LEFT JOIN Person AS P ON A.userId = P.id LEFT JOIN Possessor AS Po ON A.possessorId = Po.id";

    String checkId = " WHERE LOWER(A.id) LIKE ?1";
    String checkName = " AND LOWER(A.name) LIKE ?2";
    String checkClass = " AND (LOWER(C.subClass) LIKE ?3 OR LOWER(C.mainClass) LIKE ?3)";
    String checkAddress = " AND (LOWER(A.buildingAbbreviature) LIKE ?4 OR LOWER(A.room) LIKE ?4)";
    String checkDate = " AND (A.expirationDate IS NULL OR A.expirationDate BETWEEN ?5 AND ?6)";
    String checkActive = " AND A.active = ?7";

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor)
    List<AssetInfoShort> getAll();

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(String id, String name, String classification, String address, Date start, Date end, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate + checkActive)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoDivision(String id, String name, String classification, String address, Date start, Date end, Boolean active, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate + " AND (P.structuralUnit = ?7 OR P.subdivision = ?7)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActive(String id, String name, String classification, String address, Date start, Date end, Integer division, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId + checkName +
            checkClass + checkAddress + checkDate + checkActive + " AND (P.structuralUnit = ?8 OR P.subdivision = ?8)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsAll(String id, String name, String classification, String address, Date start, Date end, Boolean active, Integer division, PageRequest pageRequest);

    @Query(assetInfoCreate + tableFromAllTables + " WHERE A.id = ?1")
    AssetInfo getAssetInfoById(String id);

}
