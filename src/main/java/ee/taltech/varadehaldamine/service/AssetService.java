package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidAssetException;
import ee.taltech.varadehaldamine.exception.InvalidKitRelationException;
import ee.taltech.varadehaldamine.exception.InventoryExcelException;
import ee.taltech.varadehaldamine.exception.WrongCurrentUserRoleException;
import ee.taltech.varadehaldamine.model.*;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.repository.AssetRepository;
import ee.taltech.varadehaldamine.repository.ClassificationRepository;
import ee.taltech.varadehaldamine.repository.KitRelationRepository;
import ee.taltech.varadehaldamine.repository.PossessorRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Service
public class AssetService {

    @PersistenceUnit
    EntityManagerFactory emf;

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private KitRelationRepository kitRelationRepository;
    @Autowired
    private PossessorRepository possessorRepository;
    @Autowired
    private ClassificationService classificationService;
    @Autowired
    private PossessorService possessorService;
    @Autowired
    private PersonService personService;
    @Autowired
    private InventoryService inventoryService;

    public List<AssetInfoShort> findAll() {
        return assetRepository.getAll();
    }

    public List<AssetInfo> getAllInfoAboutAssetASC(){

        return assetRepository.getAllInfoAboutAsset();
    }

    // when adding new asset, the user and comments would not to be put
    public AssetInfo addAsset(AssetInfo assetInfo) {
        try {
            if (checkAssetInfoBeforeAdding(assetInfo)) {
                Date purchaseDate = assetInfo.getPurchaseDate();
                Date expirationDate = null;
                if (purchaseDate != null && assetInfo.getLifeMonthsLeft() != null && assetInfo.getLifeMonthsLeft() >= 0) {
                    expirationDate = Date.valueOf(purchaseDate.toLocalDate()
                            .plusMonths(assetInfo.getLifeMonthsLeft().longValue()));
                }
                Timestamp dbPurchaseDate = null;
                if (assetInfo.getPurchaseDate() != null) {
                    dbPurchaseDate = new Timestamp(assetInfo.getPurchaseDate().getTime());
                }
                Asset asset = new Asset(assetInfo.getId(), assetInfo.getName(), assetInfo.getSubclass(),
                        assetInfo.getPossessorId(), expirationDate,
                        assetInfo.getDelicateCondition(), assetInfo.getChecked(),
                        assetInfo.getPrice(), assetInfo.getResidualPrice(), dbPurchaseDate,
                        assetInfo.getBuildingAbbreviation(), assetInfo.getRoom(),
                        assetInfo.getDescriptionText());
                assetRepository.save(asset);
                addKitRelation(assetInfo);

                // in getAssetInfoByIdAndDivisionOrUserId put 0 as userId, as userDivision -1 makes able to return every asset
                return assetRepository.getAssetInfoByIdAndDivisionOrUserId(assetInfo.getId(), -1, 0L);
            }
        } catch (Exception e) {
            throw new InvalidAssetException("Error when adding asset: " + e);
        }
        return null;
    }


    public AssetInfo getAssetById(String assetId, List<String> roles, Long id) {
        Integer userDivision = getDivision(roles);
        System.out.println(userDivision);
        return assetRepository.getAssetInfoByIdAndDivisionOrUserId(assetId.toLowerCase(), userDivision, id);
    }

    private void addKitRelation(AssetInfo assetInfo) {
        try {
            if (assetInfo.getMajorAssetId() != null) {
                KitRelation kit = new KitRelation(assetInfo.getId(), assetInfo.getMajorAssetId());
                kitRelationRepository.save(kit);
            }
        } catch (InvalidKitRelationException e) {
            System.out.println(e.getMessage());
        }
    }

    public Page<AssetInfoShort> getAssetsList(int page, int size, AssetInfoShort assetSearchCriteria, String order, String sortBy, List<String> roles) {

        String id = "%%";
        String name = "%%";
        Integer division = null;
        String classification = "%%";
        String address = "%%";
        Boolean active = null;
        java.util.Date start = new java.util.Date(100);
        java.util.Date end = null;
        Integer userDivision = getDivision(roles);
        if (assetSearchCriteria.getId() != null) {
            id = "%" + assetSearchCriteria.getId().toLowerCase() + "%";
        }
        if (assetSearchCriteria.getName() != null) {
            name = "%" + assetSearchCriteria.getName().toLowerCase() + "%";
        }
        if (assetSearchCriteria.getStructuralUnitPlusSubdivision() != null) {
            try {
                division = Integer.parseInt(assetSearchCriteria.getStructuralUnitPlusSubdivision());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Given not integer in division filter field");
            }
        }
        if (assetSearchCriteria.getMainClassPlusSubclass() != null) {
            classification = "%" + assetSearchCriteria.getMainClassPlusSubclass().toLowerCase() + "%";
        }
        if (assetSearchCriteria.getBuildingAbbreviationPlusRoom() != null) {
            address = "%" + assetSearchCriteria.getBuildingAbbreviationPlusRoom().toLowerCase() + "%";
        }
        if (assetSearchCriteria.getActive() != null) {
            active = assetSearchCriteria.getActive();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());
        if (assetSearchCriteria.getLifeMonthsLeft() != null && assetSearchCriteria.getLifeMonthsLeft() == 0) {
            c.add(Calendar.MONTH, 1);
        } else if (assetSearchCriteria.getLifeMonthsLeft() != null && assetSearchCriteria.getLifeMonthsLeft() > 0) {
            Calendar cStart = Calendar.getInstance();
            cStart.setTime(new java.util.Date());
            cStart.add(Calendar.MONTH, assetSearchCriteria.getLifeMonthsLeft());
            start = cStart.getTime();
            c.add(Calendar.MONTH, assetSearchCriteria.getLifeMonthsLeft() + 1);
        } else {
            c.add(Calendar.YEAR, 100);
        }
        end = c.getTime();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        if (order.equals("ASC")) {
            pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        }
        if (assetSearchCriteria.getLifeMonthsLeft() != null && assetSearchCriteria.getLifeMonthsLeft() > 0) {
            if (division != null && active != null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsAll(id, name, classification, address, start, end, userDivision, active, division, pageRequest);
            } else if (division == null && active == null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(id, name, classification, address, start, end, userDivision, pageRequest);
            } else if (division == null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoDivision(id, name, classification, address, start, end, userDivision, active, pageRequest);
            } else {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoActive(id, name, classification, address, start, end, userDivision, division, pageRequest);
            }
        } else {
            if (division != null && active != null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsAllDateWithNull(id, name, classification, address, start, end, userDivision, active, division, pageRequest);
            } else if (division == null && active == null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivisionDateWithNull(id, name, classification, address, start, end, userDivision, pageRequest);
            } else if (division == null) {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoDivisionDateWithNull(id, name, classification, address, start, end, userDivision, active, pageRequest);
            } else {
                return assetRepository.getFilteredAndSortedAssetInfoShortsNoActiveDateWithNull(id, name, classification, address, start, end, userDivision, division, pageRequest);
            }
        }
    }

    public Page<AssetInfoShort> getAssetsUserOwning(Long id, int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return assetRepository.getAssetInfoShortByUserId(id, pageRequest);
    }

    public Asset update(AssetInfo assetInfo, String id) {
        Asset dbAsset = assetRepository.findAssetById(id);
        try {
            if (assetInfo != null && dbAsset != null) {
                String newName = assetInfo.getName();
                if (newName != null && !newName.isBlank() && newName.length() <= 100) {
                    dbAsset.setName(newName);
                }
                if (assetInfo.getActive() != null) {
                    dbAsset.setActive(assetInfo.getActive());
                }
                if (assetInfo.getSubclass() != null && assetInfo.getSubclass().length() <= 30
                        && classificationRepository.findClassificationBySubClass(assetInfo.getSubclass()) != null) {
                    dbAsset.setSubClass(assetInfo.getSubclass());
                }
                // IMPORTANT, if we need USER THEN ADD IT ALSO HERE
                if (assetInfo.getPossessorId() != null
                        && possessorRepository.findPossessorById(assetInfo.getPossessorId()) != null) {
                    dbAsset.setPossessorId(assetInfo.getPossessorId());
                }
                if (assetInfo.getLifeMonthsLeft() != null && assetInfo.getLifeMonthsLeft() >= 0) {
                    LocalDate currentTime = LocalDate.now();
                    dbAsset.setExpirationDate(Date.valueOf(currentTime
                            .plusMonths(assetInfo.getLifeMonthsLeft().longValue())));
                }
                if (assetInfo.getDelicateCondition() != null) {
                    dbAsset.setDelicateCondition(assetInfo.getDelicateCondition());
                }
                if (assetInfo.getChecked() != null) {
                    dbAsset.setChecked(assetInfo.getChecked());
                }
                if (assetInfo.getPrice() != null) {
                    dbAsset.setPrice(assetInfo.getPrice());
                }
                if (assetInfo.getResidualPrice() != null) {
                    dbAsset.setResidualPrice(assetInfo.getResidualPrice());
                }
                if (assetInfo.getPurchaseDate() != null) {
                    dbAsset.setPurchaseDate(new Timestamp(assetInfo.getPurchaseDate().getTime()));
                }
                String buildingAbbreviation = assetInfo.getBuildingAbbreviation();
                if (buildingAbbreviation != null && !buildingAbbreviation.isBlank()
                        && buildingAbbreviation.length() <= 10) {
                    dbAsset.setBuildingAbbreviature(buildingAbbreviation);
                }
                String room = assetInfo.getRoom();
                if (room != null && room.length() > 0 && room.length() <= 10) {
                    if (room.equalsIgnoreCase("-")) {
                        dbAsset.setRoom(null);
                    } else {
                        dbAsset.setRoom(room);
                    }
                }
                String descritpion = assetInfo.getDescriptionText();
                if (descritpion != null && descritpion.length() <= 255) {
                    dbAsset.setDescription(descritpion);
                }
                if (assetInfo.getMajorAssetId() != null) {
                    KitRelation existingKit = kitRelationRepository
                            .findKitRelationByComponentAssetId(assetInfo.getComponentAssetId());
                    if (existingKit != null) {
                        existingKit.setMajorAssetId(assetInfo.getMajorAssetId());
                        kitRelationRepository.save(existingKit);
                    }
                }
                if (assetInfo.getStructuralUnit() != null) {
                    Possessor newPossessor = possessorService
                            .findPossessor(assetInfo.getStructuralUnit(), assetInfo.getSubdivision());
                    if (newPossessor != null) {
                        dbAsset.setPossessorId(newPossessor.getId());
                    }
                }
                String newSubClass = assetInfo.getSubclass();
                String newMainCLass = assetInfo.getMainClass();
                if (newSubClass != null && newMainCLass != null
                        && classificationService.doesClassificationExist(newMainCLass, newSubClass)) {
                    dbAsset.setSubClass(newSubClass);
                }
                return assetRepository.save(dbAsset);
            }
        } catch (NumberFormatException e) {
            throw new InvalidAssetException("Error when updating asset: " + e);
        }
        return null;
    }

    public Asset check(String id, List<String> authorities) {
        Asset dbAsset = assetRepository.findAssetById(id);
        Integer division = inventoryService.getDivision(authorities);
        try {
            if (id != null && dbAsset != null && inventoryService.getInventoryOngoing(division)) {

                dbAsset.setChecked(!dbAsset.getChecked());
                return assetRepository.save(dbAsset);
            }
        } catch (Exception e) {
            throw new InvalidAssetException("Error when adding asset: " + e);
        }
        return null;
    }

    public boolean checkMultiple(List<String> assetIds, List<String> authorities) {
        if (assetIds != null && assetIds.size() > 0) {
            for (String id : assetIds) {
                Asset dbAsset = assetRepository.findAssetById(id);
                Integer division = inventoryService.getDivision(authorities);
                if (dbAsset != null  && inventoryService.getInventoryOngoing(division)) {

                    dbAsset.setChecked(!dbAsset.getChecked());
                    assetRepository.save(dbAsset);
                }
            }
            return true;
        }
        return false;
    }


    public List<AssetInfo> getInventoryListsByYear (List < String > roles,int year) {
        Integer division = inventoryService.getDivision(roles);
        Inventory inventory = inventoryService.getInventoryByYear(division, year);
        if (inventory == null) {
            throw new InventoryExcelException("Inventory not found");
        } else {
            return getAssetListByDate(inventory.getStartDate(), inventory.getEndDate(),
                    inventory.getAssets());
        }
    }


    public List<AssetInfo> getLists (List < String > roles) {
        Integer division = inventoryService.getDivision(roles);
        Inventory inventory = inventoryService.getOngoingInventory(division);

        if (inventory == null) {
            throw new InventoryExcelException("inventory not found");
        } else {
            return getAssetListByDate(inventory.getStartDate(), inventory.getEndDate(),
                    inventory.getAssets());
        }
    }



    public List<AssetInfo> getAssetListByDate(Date firstDate, Date lastDate, Set<String> inventoryAssets) {
        if (firstDate == null || lastDate == null|| inventoryAssets.size() == 0) {
            throw new InventoryExcelException();
        }
        List<AssetInfo> resultLastDate = new ArrayList<>();
        List<AssetInfo> resultFirstDate = new ArrayList<>();
        for (String assetId : inventoryAssets) {
            List<Asset> audit = getAuditList(assetId);
            boolean isLastAdded = false;
            for (Asset auditAsset : audit) {
                if (isLastAdded && (auditAsset.getModifiedAt().toLocalDateTime().toLocalDate().equals(firstDate.toLocalDate())
                        || auditAsset.getModifiedAt().toLocalDateTime().toLocalDate().isBefore(firstDate.toLocalDate()))) {
                    resultFirstDate.add(constructAssetInfo(auditAsset));
                    break;
                }
                if (!isLastAdded && (auditAsset.getModifiedAt().toLocalDateTime().toLocalDate().equals(lastDate.toLocalDate())
                        || auditAsset.getModifiedAt().toLocalDateTime().toLocalDate().isBefore(lastDate.toLocalDate()))) {
                    resultLastDate.add(constructAssetInfo(auditAsset));
                    isLastAdded = true;
                }
            }
        }
        List<AssetInfo> result = new ArrayList<>();
        result.addAll(resultFirstDate);
        result.addAll(resultLastDate);
        return result;
    }

    private AssetInfo constructAssetInfo(Asset asset) {
        Possessor possessor = possessorRepository.findPossessorById(asset.getPossessorId());
        Classification classification = classificationRepository.findClassificationBySubClass(asset.getSubClass());
        KitRelation kitRelation = kitRelationRepository.findKitRelationByComponentAssetId(asset.getId());
        String majorAssetId = null;
        if (kitRelation != null) {
            majorAssetId = kitRelation.getMajorAssetId();
        }
        return new AssetInfo(asset.getId(), asset.getName(), asset.getActive(), asset.getUserId(),
                asset.getPossessorId(), asset.getExpirationDate(), asset.getDelicateCondition(), asset.getChecked(),
                asset.getCreatedAt(), asset.getModifiedAt(), asset.getPrice(), asset.getResidualPrice(), asset.getPurchaseDate(),
                asset.getSubClass(), classification.getMainClass(), majorAssetId,
                asset.getBuildingAbbreviature(), asset.getRoom(), asset.getDescription(), "Kasutaja usename",
                possessor.getStructuralUnit(), possessor.getSubdivision());
    }

    private List<Asset> getAuditList(String id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        AuditReader auditReader = AuditReaderFactory.get(em);

        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Asset.class, true, true);
        q.add(AuditEntity.id().eq(id));
        List<Asset> audit = q.getResultList();
        Collections.reverse(audit);
        em.getTransaction().commit();
        em.close();
        return audit;
    }

    public Page<AssetInfo> getAuditById(String id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        AuditReader auditReader = AuditReaderFactory.get(em);

        List<Asset> audit = getAuditList(id);
        List<AssetInfo> assetInfos = new ArrayList<>();
        Collections.reverse(audit);
        System.out.println(audit);
        for (Asset a : audit) {
            assetInfos.add(constructAssetInfo(a));
        }
        Collections.reverse(assetInfos);
        em.getTransaction().commit();
        em.close();
        Pageable pageable = PageRequest.of(0, 10);
        return new PageImpl<>(assetInfos, pageable, assetInfos.size());
    }


    private boolean checkAssetInfoBeforeAdding(AssetInfo assetInfo) {
        return assetInfo != null && assetInfo.getId() != null && !assetInfo.getId().isBlank()
                && assetRepository.findById(assetInfo.getId()).isEmpty() && assetInfo.getId().length() <= 20
                && assetInfo.getName() != null && !assetInfo.getName().isBlank()
                && assetInfo.getName().length() <= 100
                && assetInfo.getSubclass() != null
                && assetInfo.getSubclass().length() <= 30 && assetInfo.getPossessorId() != null
                && (assetInfo.getDescriptionText() == null
                || assetInfo.getDescriptionText().length() <= 255) && assetInfo.getDelicateCondition() != null
                && assetInfo.getBuildingAbbreviation() != null && !assetInfo.getBuildingAbbreviation().isBlank()
                && assetInfo.getBuildingAbbreviation().length() <= 10 && (assetInfo.getRoom() == null
                || assetInfo.getRoom().length() <= 10)
                && classificationRepository.findClassificationBySubClass(assetInfo.getSubclass()) != null
                && (assetInfo.getMajorAssetId() == null
                || kitRelationRepository.findKitRelationByComponentAssetId(assetInfo.getMajorAssetId()) != null
                || assetInfo.getMajorAssetId().equals(assetInfo.getId())
                && assetInfo.getPrice() != null && assetInfo.getResidualPrice() != null);
    }

    private Integer getDivision(List<String> roles){
        Integer division = null;
        for (String role: roles){
            if (role.equals("ROLE_Raamatupidaja")){
                return -1;
            } else if (role.startsWith("ROLE_D") && division == null){
                try {
                    division = Integer.valueOf(role.replace("ROLE_D", ""));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Given not integer in division filter field");
                }
            }
        }
        System.out.println(division);
        if (division == null){
            throw new WrongCurrentUserRoleException("Check user have right roles");
        }
        return division;
    }
}
