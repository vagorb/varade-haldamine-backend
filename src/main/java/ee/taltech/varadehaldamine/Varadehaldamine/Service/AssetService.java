package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.*;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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
        for (Asset asset: assetRepository.findAll()){
            AssetInfoShort assetInfo = new AssetInfoShort();
            assetInfo.setId(asset.getId());
            assetInfo.setName(asset.getName());
            assetInfo.setActive(asset.getActive());
            Address address = addressRepository.findAddressByAssetId(asset.getId());
            if (address != null){
                if (address.getRoom() != null) {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature() + address.getRoom());
                } else {
                    assetInfo.setBuildingAbbreviationPlusRoom(address.getBuildingAbbreviature());
                }
            }
            assetInfo.setModifiedAt(new Date(asset.getModifiedAt().getTime()));
            Person person = personService.getPersonById(asset.getUserId());
            if (person != null){
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
                System.out.println(classificationRepository.findAll());
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
            System.out.println("New exception occurred: " + e.getMessage());
        }
        return null;
    }

    public AssetInfo getAssetById(String assetId){
        try {
            Asset asset = assetRepository.findAssetById(assetId);
            if (asset != null) {
                AssetInfo assetInfo = new AssetInfo();
                assetInfo.setId(asset.getId());
                assetInfo.setName(asset.getName());

                Person person = personService.getPersonById(asset.getUserId());
                if (person != null) {
                    assetInfo.setUserId(asset.getUserId());
                    assetInfo.setFirstname(person.getFirstname());
                    assetInfo.setLastname(person.getLastname());
                }

                assetInfo.setPossessorId(asset.getPossessorId());
                Possessor possessor = possessorService.getPossesorById(asset.getPossessorId());
                if (possessor == null) {
                    throw new Exception();
                }
                assetInfo.setInstitute(possessor.getInstitute());
                assetInfo.setDivision(possessor.getDivision());
                assetInfo.setSubdivision(possessor.getSubdivision());

                assetInfo.setExpirationDate(asset.getExpirationDate());
                assetInfo.setDelicateCondition(asset.getDelicateCondition());
                assetInfo.setCreatedAt(asset.getCreatedAt());
                assetInfo.setModifiedAt(asset.getModifiedAt());

                Worth worth = worthRepository.findWorthByAssetId(asset.getId());
                if (worth != null) {
                    assetInfo.setPrice(worth.getPrice());
                    assetInfo.setResidualPrice(worth.getResidualPrice());
                    assetInfo.setPurchaseDate(worth.getPurchaseDate());
                }

                Classification classification = classificationRepository
                        .findClassificationBySubClass(asset.getSubClass());
                if (classification == null) {
                    throw new Exception();
                }
                assetInfo.setSubclass(classification.getSubClass());
                assetInfo.setMainClass(classification.getMainClass());

                KitRelation kitRelation = kitRelationRepository.findKitRelationByComponentAssetId(asset.getId());
                if (kitRelation != null) {
                    assetInfo.setComponentAssetId(kitRelation.getComponentAssetId());
                    assetInfo.setMajorAssetId(kitRelation.getMajorAssetId());
                }

                Address address = addressRepository.findAddressByAssetId(asset.getId());
                if (address == null) {
                    throw new Exception();
                }
                assetInfo.setBuildingAbbreviation(address.getBuildingAbbreviature());
                assetInfo.setRoom(address.getRoom());

                Description description = descriptionRepository.findDescriptionByAssetId(asset.getId());
                if (description != null) {
                    assetInfo.setDescriptionText(description.getText());
                }
                return assetInfo;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    private void addAddress(AssetInfo assetInfo) {
        try {
            Address address = new Address(assetInfo.getId(), assetInfo.getBuildingAbbreviation(), assetInfo.getRoom());
            addressRepository.save(address);
        } catch (Exception ignored) {
        }
    }

    private void addKitRelation(AssetInfo assetInfo) {
        try {
            if (!assetInfo.getComponentAssetId().isBlank() && !assetInfo.getMajorAssetId().isBlank()) {
                KitRelation kit = new KitRelation(assetInfo.getComponentAssetId(), assetInfo.getMajorAssetId());
                kitRelationRepository.save(kit);
            }
        } catch (Exception ignored) {
        }
    }

    private void addDescription(AssetInfo assetInfo) {
        try {
            if (!assetInfo.getDescriptionText().isBlank()) {
                Description description = new Description(assetInfo.getId(), assetInfo.getDescriptionText());
                descriptionRepository.save(description);
            }
        } catch (Exception ignored) {
        }
    }

    private void addWorth(AssetInfo assetInfo) {
        try {
            if (assetInfo.getPrice() != null && assetInfo.getResidualPrice() != null) {
                Worth worth = new Worth(assetInfo.getId(), assetInfo.getPrice(),
                        assetInfo.getResidualPrice(), assetInfo.getPurchaseDate());
                worthRepository.save(worth);
            }
        } catch (Exception ignored) {
        }
    }
}
