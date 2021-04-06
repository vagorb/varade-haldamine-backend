package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.*;
import ee.taltech.varadehaldamine.model.*;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

//    private AssetSearchCriteria assetSearchCriteria;
//    private AssetCriteriaRepository assetCriteriaRepository;

//    private final EmployeeRepository employeeRepository;
//    private final EmployeeCriteriaRepository employeeCriteriaRepository;
//    private AssetRepository
//public EmployeeService(EmployeeRepository employeeRepository,
//                       EmployeeCriteriaRepository employeeCriteriaRepository) {
//    this.employeeRepository = employeeRepository;
//    this.employeeCriteriaRepository = employeeCriteriaRepository;
//}

//    public AssetService(AssetCriteriaRepository assetCriteriaRepository) {
//        this.assetCriteriaRepository = assetCriteriaRepository;
//    }

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
                    Asset asset = new Asset(assetInfo.getId(), assetInfo.getName(), subclass,
                            assetInfo.getPossessorId(), assetInfo.getExpirationDate(),
                            assetInfo.getDelicateCondition());
                    Asset dbAsset = assetRepository.save(asset);
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
        try {
            Asset asset = assetRepository.findAssetById(assetId);
            if (asset != null) {
                System.out.println(asset);
                AssetInfo assetInfo = new AssetInfo();
                assetInfo.setId(asset.getId());
                assetInfo.setName(asset.getName());
                assetInfo.setActive(asset.getActive());
                Person person = personService.getPersonById(asset.getUserId());
                if (person != null) {
                    assetInfo.setUserId(asset.getUserId());
                    assetInfo.setFirstname(person.getFirstname());
                    assetInfo.setLastname(person.getLastname());
                }

                assetInfo.setPossessorId(asset.getPossessorId());
                Possessor possessor = possessorService.getPossesorById(asset.getPossessorId());
                if (possessor == null) {
                    throw new PossessorNotFoundException();
                }
                assetInfo.setStructuralUnit(possessor.getStructuralUnit());
                assetInfo.setSubdivision(possessor.getSubdivision());

                System.out.println(asset.getExpirationDate() + " expiration date");
                if (asset.getExpirationDate() != null) {
                    long monthsBetween = ChronoUnit.MONTHS.between(
                            LocalDate.parse((CharSequence) new java.util.Date()).withDayOfMonth(1),
                            LocalDate.parse((CharSequence) asset.getExpirationDate()).withDayOfMonth(1));
                    System.out.println(monthsBetween); //3
                    assetInfo.setLifeMonthsLeft((int) monthsBetween);
                } else {
                    assetInfo.setLifeMonthsLeft(0);
                }
                assetInfo.setDelicateCondition(asset.getDelicateCondition());
                assetInfo.setCreatedAt(new Date(asset.getCreatedAt().getTime()));
                assetInfo.setModifiedAt(new Date(asset.getModifiedAt().getTime()));

                Worth worth = worthRepository.findWorthByAssetId(asset.getId());
                if (worth != null) {
                    assetInfo.setPrice(worth.getPrice());
                    assetInfo.setResidualPrice(worth.getResidualPrice());
                    if (worth.getPurchaseDate() != null) {
                        assetInfo.setPurchaseDate(new Date(worth.getPurchaseDate().getTime()));
                        assetInfo.setIsPurchased(true);
                    } else {
                        assetInfo.setIsPurchased(false);
                    }
                }

                Classification classification = classificationRepository
                        .findClassificationBySubClass(asset.getSubClass());
                System.out.println("Classification " + classification);
                if (classification == null) {
                    throw new ClassificationNotFoundException();
                }
                assetInfo.setSubclass(classification.getSubClass());
                assetInfo.setMainClass(classification.getMainClass());

                KitRelation kitRelation = kitRelationRepository.findKitRelationByComponentAssetId(asset.getId());
                if (kitRelation != null) {
                    assetInfo.setComponentAssetId(kitRelation.getComponentAssetId());
                    assetInfo.setMajorAssetId(kitRelation.getMajorAssetId());
                    if (kitRelation.getMajorAssetId().equals(asset.getId())) {
                        assetInfo.setKitPartName("Peavara");
                    } else {
                        assetInfo.setKitPartName("Komponent");
                    }
                } else {
                    assetInfo.setKitPartName("");
                }

                Address address = addressRepository.findAddressByAssetId(asset.getId());
                System.out.println("address " + address);
                if (address != null) {
                    assetInfo.setBuildingAbbreviation(address.getBuildingAbbreviature());
                    assetInfo.setRoom(address.getRoom());
                }

                Description description = descriptionRepository.findDescriptionByAssetId(asset.getId());
                if (description != null) {
                    assetInfo.setDescriptionText(description.getText());
                }
                System.out.println(assetInfo);
                return assetInfo;
            } else {
                throw new AssetNotFoundException();
            }

        } catch (ClassificationNotFoundException | AssetNotFoundException | PossessorNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
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
        return null;
//
//        if (division != null && active != null) {
//            return assetRepository.getFilteredAndSortedAssetInfoShortsAll(id, name, classification, address, active, division, pageRequest);
//        } else if (division == null && active == null) {
//            return assetRepository.getFilteredAndSortedAssetInfoShortsNoActiveAndNoDivision(id, name, classification, address, pageRequest);
//        } else if (division == null) {
//            return assetRepository.getFilteredAndSortedAssetInfoShortsNoDivision(id, name, classification, address, active, pageRequest);
//        } else {
//            return assetRepository.getFilteredAndSortedAssetInfoShortsNoActive(id, name, classification, address, division, pageRequest);
//        }
    }
}
