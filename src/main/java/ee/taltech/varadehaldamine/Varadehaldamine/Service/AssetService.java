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

    //when adding new asset, the user and comments would not to be put
    public Asset addAsset(AssetInfo assetInfo) {
        try {
            if (assetInfo != null && !assetInfo.getId().isBlank() && !assetInfo.getName().isBlank()
                    && !assetInfo.getSubclass().isBlank() && assetInfo.getPossessor_id() != null
                    && assetInfo.getDelicate_condition() != null && !assetInfo.getBuilding_abbreviature().isBlank()) {
                Optional<Classification> classification = classificationRepository.findById(assetInfo.getSubclass());
                if (classification.isPresent()) {
                    String subclass = classification.get().getSubclass();
                    Asset asset = new Asset(assetInfo.getId(), assetInfo.getName(), subclass,
                            assetInfo.getPossessor_id(), assetInfo.getExpiration_date(),
                            assetInfo.getDelicate_condition());
                    Asset dbAsset = assetRepository.save(asset);
                    addAddress(assetInfo);
                    addKitRelation(assetInfo);
                    addDescription(assetInfo);
                    addWorth(assetInfo);
                    return dbAsset;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private void addAddress(AssetInfo assetInfo) {
        try {
            Address address = new Address(assetInfo.getId(), assetInfo.getBuilding_abbreviature(), assetInfo.getRoom());
            addressRepository.save(address);
        } catch (Exception ignored) {
        }
    }

    private void addKitRelation(AssetInfo assetInfo) {
        try {
            if (!assetInfo.getComponent_asset_id().isBlank() && !assetInfo.getMajor_asset_id().isBlank()) {
                KitRelation kit = new KitRelation(assetInfo.getComponent_asset_id(), assetInfo.getMajor_asset_id());
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
            if (assetInfo.getPrice() != null && assetInfo.getResidual_price() != null) {
                Worth worth = new Worth(assetInfo.getId(), assetInfo.getPrice(),
                        assetInfo.getResidual_price(), assetInfo.getPurchase_date());
                worthRepository.save(worth);
            }
        } catch (Exception ignored) {
        }
    }
}
