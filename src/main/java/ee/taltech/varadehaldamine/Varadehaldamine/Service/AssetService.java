package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.*;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import ee.taltech.varadehaldamine.Varadehaldamine.Rsql.AssetCriteriaRepository;
//import ee.taltech.varadehaldamine.Varadehaldamine.Rsql.AssetPage;
import ee.taltech.varadehaldamine.Varadehaldamine.Rsql.AssetSearchCriteria;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private CommentRepository commentRepository;
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
            //assetInfo.setActive(asset.getActive());
            Address address = addressRepository.findAddressByAssetId(asset.getId());
            if (address != null) {
                if (address.getRoom() != null) {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature() + address.getRoom());
                } else {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature());
                }
            }
            //assetInfo.setModifiedAt(new Date(asset.getModifiedAt().getTime()));
            Person person = personService.getPersonById(asset.getUserId());
            if (person != null) {
                assetInfo.setPersonName(person.getFirstname() + " " + person.getLastname());
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
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
        }
    }


//    public Page<Asset> getAssetsList(int page, int size, AssetSearchCriteria assetSearchCriteria) {
    public Page<Asset> getAssetsList(int page, int size, AssetSearchCriteria assetSearchCriteria, String order, String sortBy) {
//        AssetSearchCriteria assetSearchCriteria = new AssetSearchCriteria();
//        assetSearchCriteria.setActive(true);
//        assetSearchCriteria.setDelicateCondition(true);
//        assetSearchCriteria.setExpirationDate(Date.valueOf("1988-09-29"));
//        assetSearchCriteria.setId("name");
//        assetSearchCriteria.setName("name");
//        assetSearchCriteria.setPossessorId(5L);
//        assetSearchCriteria.setSubClass("name");
//        assetSearchCriteria.setUserId(5L);
        PageRequest pageReq = PageRequest.of(page, size);
//        PageRequest pageReq
//                = PageRequest.of(page, size);
//        Pageable pageable = PageRequest.of(page, size);
        List<AssetInfoShort> assetInfoList = findAll();
        final int start = (int)pageReq.getOffset();
        final int end = Math.min((start + pageReq.getPageSize()), assetInfoList.size());
//        return new PageImpl<>(assetInfoList.subList(start, end), pageable, assetInfoList.size());
        PageImpl<AssetInfoShort> imp = new PageImpl<>(assetInfoList.subList(start, end), pageReq, assetInfoList.size());
        return assetCriteriaRepository.findAllWithFilters(imp , assetSearchCriteria, order, sortBy);
//        return assetCriteriaRepository.findAllWithFilters(assetPage, assetSearchCriteria);
    }
}
