package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.Rsql.AssetCriteriaRepository;
import ee.taltech.varadehaldamine.Rsql.AssetSearchCriteria;
import ee.taltech.varadehaldamine.exception.*;
import ee.taltech.varadehaldamine.model.*;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private AssetCriteriaRepository assetCriteriaRepository;

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
        List<AssetInfoShort> assetInfoList = new ArrayList<>();
        System.out.println(assetRepository.findAll());
        for (Asset asset : assetRepository.findAll()) {
            AssetInfoShort assetInfo = new AssetInfoShort();
            assetInfo.setId(asset.getId());
            assetInfo.setName(asset.getName());
            assetInfo.setActive(asset.getActive());
            Address address = addressRepository.findAddressByAssetId(asset.getId());
            System.out.println(address);
            if (address != null) {
                if (address.getRoom() != null) {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature() + address.getRoom());
                } else {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature());
                }
            }
            if (asset.getUserId() != null) {
                Possessor possessor = possessorService.getPossesorById(asset.getPossessorId());
                assetInfo.setStructuralUnitPlusSubdivision(possessor.getStructuralUnit().toString()
                        + possessor.getSubdivision().toString());
            }
            if (asset.getSubClass() != null) {
                Classification classification = classificationRepository.findClassificationBySubClass(asset.getSubClass());
                assetInfo.setMainClassPlusSubclass(classification.getMainClass() + " " + classification.getSubClass());
            }
            if (asset.getExpirationDate() != null) {
                long minutes = (asset.getExpirationDate().getTime() / 60000) - (System.currentTimeMillis() / 60000);
                long month = minutes / 43800;
                if (month >= 0) {
                    assetInfo.setLifeMonthsLeft((int) month);
                } else {
                    assetInfo.setLifeMonthsLeft(0);
                }
            }
            assetInfoList.add(assetInfo);
        }
        return assetInfoList;
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
                    long minutes = (asset.getExpirationDate().getTime() / 60000) - (System.currentTimeMillis() / 60000);
                    long month = minutes / 43800;
                    if (month >= 0) {
                        assetInfo.setLifeMonthsLeft((int) month);
                    } else {
                        assetInfo.setLifeMonthsLeft(0);
                    }
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


    //    public Page<Asset> getAssetsList(int page, int size, AssetSearchCriteria assetSearchCriteria) {
    public Page<AssetInfoShort> getAssetsList(int page, int size, AssetSearchCriteria assetSearchCriteria, String order, String sortBy) {
        List<AssetInfoShort> assetInfoList = findAll();
        if (assetSearchCriteria.getId() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getId() != null)
                    .filter(assetInfoShort -> assetInfoShort.getId().contains(assetSearchCriteria.getId())).collect(Collectors.toList());
        } if (assetSearchCriteria.getName() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getName() != null)
                    .filter(assetInfoShort -> assetInfoShort.getName().contains(assetSearchCriteria.getName())).collect(Collectors.toList());
        } if (assetSearchCriteria.getMainClassPlusSubclass() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getMainClassPlusSubclass() != null)
                    .filter(assetInfoShort -> assetInfoShort.getMainClassPlusSubclass().contains(assetSearchCriteria.getMainClassPlusSubclass())).collect(Collectors.toList());
        } if (assetSearchCriteria.getBuildingAbbreviationPlusRoom() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getBuildingAbbreviationPlusRoom() != null)
                    .filter(assetInfoShort -> assetInfoShort.getBuildingAbbreviationPlusRoom().contains(assetSearchCriteria.getBuildingAbbreviationPlusRoom())).collect(Collectors.toList());
        } if (assetSearchCriteria.getActive() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getActive() != null)
                    .filter(assetInfoShort -> assetInfoShort.getActive().equals(assetSearchCriteria.getActive())).collect(Collectors.toList());
        } if (assetSearchCriteria.getStructuralUnitPlusSubdivision() != null) {
            assetInfoList = assetInfoList.stream().filter(assetInfoShort -> assetInfoShort.getStructuralUnitPlusSubdivision() != null)
                    .filter(assetInfoShort -> assetInfoShort.getStructuralUnitPlusSubdivision().contains(assetSearchCriteria.getStructuralUnitPlusSubdivision())).collect(Collectors.toList());
        }
        if (sortBy.equalsIgnoreCase("Id")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getId));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getId).reversed());
            }
        } else if (sortBy.equalsIgnoreCase("ExpirationDate")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getLifeMonthsLeft));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getLifeMonthsLeft).reversed());
            }
        } else if (sortBy.equalsIgnoreCase("Name")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getName));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getName).reversed());
            }
        } else if (sortBy.equalsIgnoreCase("BuildingAbbreviationPlusRoom")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getBuildingAbbreviationPlusRoom));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getBuildingAbbreviationPlusRoom).reversed());
            }
        }  else if (sortBy.equalsIgnoreCase("MainClassPlusSubclass")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getMainClassPlusSubclass));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getMainClassPlusSubclass).reversed());
            }
        }  else if (sortBy.equalsIgnoreCase("StructuralUnitPlusSubdivision")) {
            if (order.equalsIgnoreCase("ASC")) {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getStructuralUnitPlusSubdivision));
            } else {
                assetInfoList.sort(Comparator.comparing(AssetInfoShort::getStructuralUnitPlusSubdivision).reversed());
            }
        }
        PageRequest pageReq = PageRequest.of(page, size);
        Pageable pageable = PageRequest.of(page, size);
        final int start = (int) pageReq.getOffset();
        final int end = Math.min((start + pageReq.getPageSize()), assetInfoList.size());
        return new PageImpl<>(assetInfoList.subList(start, end), pageable, assetInfoList.size());

    }
}
