package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidAssetException;
import ee.taltech.varadehaldamine.exception.InvalidKitRelationException;
import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.model.KitRelation;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.repository.AssetRepository;
import ee.taltech.varadehaldamine.repository.ClassificationRepository;
import ee.taltech.varadehaldamine.repository.KitRelationRepository;
import ee.taltech.varadehaldamine.repository.PossessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private KitRelationRepository kitRelationRepository;
    @Autowired
    private PossessorRepository possessorRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PossessorService possessorService;

    public List<AssetInfoShort> findAll() {
        return assetRepository.getAll();
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
                Asset asset = new Asset(assetInfo.getId(), assetInfo.getName(), classificationRepository.findClassificationBySubClass(assetInfo.getSubclass()),
                        assetInfo.getPossessorId(), expirationDate,
                        assetInfo.getDelicateCondition(), assetInfo.getChecked(),
                        assetInfo.getPrice(), assetInfo.getResidualPrice(), dbPurchaseDate,
                        assetInfo.getBuildingAbbreviation(), assetInfo.getRoom(),
                        assetInfo.getDescriptionText());
                assetRepository.save(asset);
                addKitRelation(assetInfo);
                return assetRepository.getAssetInfoById(assetInfo.getId());
            }
        } catch (Exception e) {
            throw new InvalidAssetException("Error when adding asset: " + e);
        }
        return null;
    }


    public AssetInfo getAssetById(String assetId) {
        return assetRepository.getAssetInfoById(assetId);
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

    public Page<AssetInfoShort> getAssetsList(int page, int size, AssetInfoShort assetSearchCriteria, String order, String sortBy) {

        String id = "%%";
        String name = "%%";
        Integer division = null;
        String classification = "%%";
        String address = "%%";
        Boolean active = null;

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

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        if (order.equals("ASC")) {
            pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        }

        if (division != null && active != null) {
            return assetRepository.getFilteredAndSortedAssetInfoShortsAll(id, name, classification, address, active, division, pageRequest);
        } else if (division == null && active == null) {
            return assetRepository.getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(id, name, classification, address, pageRequest);
        } else if (division == null) {
            return assetRepository.getFilteredAndSortedAssetInfoShortsNoDivision(id, name, classification, address, active, pageRequest);
        } else {
            return assetRepository.getFilteredAndSortedAssetInfoShortsNoActive(id, name, classification, address, division, pageRequest);
        }
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
                    dbAsset.setSubClass(classificationRepository.findClassificationBySubClass(assetInfo.getSubclass()));
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
                if (room != null && room.length() <= 10) {
                    dbAsset.setRoom(room);
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
                return assetRepository.save(dbAsset);
            }
        } catch (NumberFormatException e) {
            throw new InvalidAssetException("Error when updating asset: " + e);
        }
        return null;
    }


    private boolean checkAssetInfoBeforeAdding(AssetInfo assetInfo){
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
}
