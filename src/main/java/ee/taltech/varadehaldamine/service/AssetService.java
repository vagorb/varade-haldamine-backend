package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.*;
import ee.taltech.varadehaldamine.model.*;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private ClassificationRepository classificationRepository;
    @Autowired
    private DescriptionRepository descriptionRepository;
    @Autowired
    private KitRelationRepository kitRelationRepository;
    @Autowired
    private WorthRepository worthRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private PossessorService possessorService;

    public List<AssetInfoShort> findAll() {
        return assetRepository.getAll();
    }

    // when adding new asset, the user and comments would not to be put
    public Asset addAsset(AssetInfo assetInfo) {
        try {
            if (assetInfo != null && !assetInfo.getId().isBlank() && !assetInfo.getName().isBlank()
                    && !assetInfo.getSubclass().isBlank() && assetInfo.getPossessorId() != null
                    && assetInfo.getDelicateCondition() != null && !assetInfo.getBuildingAbbreviation().isBlank()) {
                Optional<Classification> classification = classificationRepository.findById(assetInfo.getSubclass());
                if (classification.isPresent()) {
                    String subclass = classification.get().getSubClass();
                    Date purchaseDate = assetInfo.getPurchaseDate();
                    Date expirationDate = null;
                    if (purchaseDate != null) {
                        expirationDate = Date.valueOf(purchaseDate.toLocalDate()
                                .plusMonths(assetInfo.getLifeMonthsLeft().longValue()));
                    }
                    Asset asset = new Asset(assetInfo.getId(), assetInfo.getActive(), assetInfo.getName(), subclass,
                            assetInfo.getPossessorId(), expirationDate,
                            assetInfo.getDelicateCondition());
                    Asset dbAsset = assetRepository.save(asset);
                    System.out.println(dbAsset);
                    addAddress(assetInfo);
                    addKitRelation(assetInfo);
                    addDescription(assetInfo);
                    addWorth(assetInfo);
                    return dbAsset;
                }
            }
        } catch (Exception e) {
            throw new InvalidAssetException("Error when adding asset");
        }
        return null;
    }

    public AssetInfo getAssetById(String assetId) {
        return assetRepository.getAssetInfoById(assetId);
    }

    private void addAddress(AssetInfo assetInfo) throws Exception {
        try {
            Address address = new Address(assetInfo.getId(), assetInfo.getBuildingAbbreviation(), assetInfo.getRoom());
            addressRepository.save(address);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void addKitRelation(AssetInfo assetInfo) {
        try {
            if (!assetInfo.getComponentAssetId().isBlank() && !assetInfo.getMajorAssetId().isBlank()) {
                KitRelation kit = new KitRelation(assetInfo.getComponentAssetId(), assetInfo.getMajorAssetId());
                kitRelationRepository.save(kit);
            } else {
                throw new InvalidKitRelationException("Error when adding KitRelation");
            }
        } catch (InvalidKitRelationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addDescription(AssetInfo assetInfo) {
        try {
            if (!assetInfo.getDescriptionText().isBlank()) {
                Description description = new Description(assetInfo.getId(), assetInfo.getDescriptionText());
                descriptionRepository.save(description);
            } else {
                throw new InvalidDescriptionException("Error when adding Description");
            }
        } catch (InvalidDescriptionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addWorth(AssetInfo assetInfo) {
        try {
            if (assetInfo.getPrice() != null && assetInfo.getResidualPrice() != null) {
                Worth worth = new Worth(assetInfo.getId(), assetInfo.getPrice(),
                        assetInfo.getResidualPrice(), new Timestamp(assetInfo.getPurchaseDate().getTime()));
                worthRepository.save(worth);
            } else {
                throw new InvalidWorthException("Error when adding Worth");
            }
        } catch (InvalidWorthException e) {
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
}
