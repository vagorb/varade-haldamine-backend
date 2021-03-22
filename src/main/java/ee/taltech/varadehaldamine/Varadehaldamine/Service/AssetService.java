package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
import ee.taltech.varadehaldamine.Varadehaldamine.Model.Person;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AddressRepository addressRepository;
    private AssetRepository assetRepository;
    private ClassificationRepository classificationRepository;
    private CommentRepository commentRepository;
    private DescriptionRepository descriptionRepository;
    private KitRelationRepository kitRelationRepository;
    private PersonRepository personRepository;
    private PossessorRepository possessorRepository;
    private WorthRepository worthRepository;


    public List<Asset> findAll() {
        Person person1 = new Person();
        person1.setId(4L);
        person1.setFirstname("Ilya");
        person1.setLastname("Boy");
        person1.setAzureId("194041IAIB");

        Person person2 = new Person();
        person2.setFirstname("Artur");
        person2.setLastname("Pärn");
        person2.setAzureId("wow");
        return assetRepository.findAll();
    }
}
