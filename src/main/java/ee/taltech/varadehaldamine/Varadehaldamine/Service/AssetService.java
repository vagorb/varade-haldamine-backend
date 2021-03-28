package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.*;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PersonRepository personRepository;
    @Autowired
    private PossessorRepository possessorRepository;
    @Autowired
    private WorthRepository worthRepository;


    public List<Asset> findAll() {
        return assetRepository.findAll();
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
