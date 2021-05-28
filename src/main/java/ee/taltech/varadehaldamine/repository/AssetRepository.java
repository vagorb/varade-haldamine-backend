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
            " A.buildingAbbreviature, A.room, A.expirationDate, A.active, A.checked)";
    String assetInfoCreate = "SELECT new ee.taltech.varadehaldamine.modelDTO.AssetInfo(A.id, A.name, A.active, A.userId, A.possessorId, " +
            "A.expirationDate, A.delicateCondition, A.checked, A.createdAt, A.modifiedAt, A.price, A.residualPrice, " +
            "A.purchaseDate, C.subClass, C.mainClass, K.majorAssetId, A.buildingAbbreviature, A.room, A.description, " +
            "P.username, Po.structuralUnit, Po.subdivision)";

    String tableFromAssetAddressClassPossessor = " FROM Asset AS A JOIN Classification AS C ON A.subClass = C.subClass JOIN Possessor AS P ON A.possessorId = P.id";
    String tableFromAllTables = " FROM Asset AS A LEFT JOIN Classification AS C ON A.subClass = C.subClass " +
            "LEFT JOIN KitRelation AS K ON A.id = K.componentAssetId " +
            "LEFT JOIN Person AS P ON A.userId = P.id LEFT JOIN Possessor AS Po ON A.possessorId = Po.id";

    String checkId = " WHERE LOWER(A.id) LIKE ?1";
    String checkName = " AND LOWER(A.name) LIKE ?2";
    String checkClass = " AND (LOWER(C.subClass) LIKE ?3 OR LOWER(C.mainClass) LIKE ?3)";
    String checkAddress = " AND (LOWER(A.buildingAbbreviature) LIKE ?4 OR LOWER(A.room) LIKE ?4)";
    String checkDate = " AND A.expirationDate BETWEEN ?5 AND ?6";
    String checkDateWithNull = " AND (A.expirationDate IS NULL OR A.expirationDate BETWEEN ?5 AND ?6)";
    String checkUserDivision = " AND (P.structuralUnit = ?7 OR ?7 = -1)";
    String checkActive = " AND A.active = ?8";

    String sortByIdAsc = " ORDER BY A.id ASC";

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor)
    List<AssetInfoShort> getAll();

    @Query(assetInfoCreate + tableFromAllTables + sortByIdAsc)
    List<AssetInfo> getAllInfoAboutAsset();

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate + checkUserDivision)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate + checkUserDivision + checkActive)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoDivision(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Boolean active, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDate + checkUserDivision + " AND (P.structuralUnit = ?8 OR P.subdivision = ?8)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActive(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Integer division, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId + checkName +
            checkClass + checkAddress + checkDate + checkUserDivision + checkActive + " AND (P.structuralUnit = ?9 OR P.subdivision = ?9)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsAll(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Boolean active, Integer division, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDateWithNull + checkUserDivision)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivisionDateWithNull(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDateWithNull + checkUserDivision + checkActive)
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoDivisionDateWithNull(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Boolean active, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId +
            checkName + checkClass + checkAddress + checkDateWithNull + checkUserDivision + " AND (P.structuralUnit = ?8 OR P.subdivision = ?8)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsNoActiveDateWithNull(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Integer division, PageRequest pageRequest);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + checkId + checkName +
            checkClass + checkAddress + checkDateWithNull + checkUserDivision + checkActive + " AND (P.structuralUnit = ?9 OR P.subdivision = ?9)")
    Page<AssetInfoShort> getFilteredAndSortedAssetInfoShortsAllDateWithNull(String id, String name, String classification, String address, Date start, Date end, Integer userDivision, Boolean active, Integer division, PageRequest pageRequest);

    @Query(assetInfoCreate + tableFromAllTables + checkId + " AND (Po.structuralUnit = ?2 OR ?2 = -1 OR A.userId = ?3)")
    AssetInfo getAssetInfoByIdAndDivisionOrUserId(String id, Integer userDivision, Long userId);

    @Query(assetInfoShortCreate + tableFromAssetAddressClassPossessor + " WHERE A.userId = ?1")
    Page<AssetInfoShort> getAssetInfoShortByUserId(Long id, PageRequest pageRequest);
}
