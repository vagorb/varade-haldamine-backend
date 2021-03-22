package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
