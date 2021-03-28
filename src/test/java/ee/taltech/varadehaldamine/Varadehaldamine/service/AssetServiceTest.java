package ee.taltech.varadehaldamine.Varadehaldamine.service;

import ee.taltech.varadehaldamine.Varadehaldamine.Repository.*;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.AssetService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssetServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    AssetRepository assetRepository;

    @Mock
    ClassificationRepository classificationRepository;

    @Mock
    DescriptionRepository descriptionRepository;

    @Mock
    KitRelationRepository kitRelationRepository;

    @Mock
    WorthRepository worthRepository;

    @InjectMocks
    AssetService assetService;

    public void testAddAsset() {

    }
}
