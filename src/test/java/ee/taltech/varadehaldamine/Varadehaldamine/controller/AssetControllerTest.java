//package ee.taltech.varadehaldamine.Varadehaldamine.controller;
//
//        import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
//        import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
//        import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
//        import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfo;
//        import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
//        import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
//        import ee.taltech.varadehaldamine.Varadehaldamine.common.RestTemplateTests;
//        import org.junit.jupiter.api.Test;
//        import org.springframework.core.ParameterizedTypeReference;
//        import org.springframework.http.HttpEntity;
//        import org.springframework.http.HttpMethod;
//        import org.springframework.http.ResponseEntity;
//
//        import java.sql.Date;
//        import java.time.LocalDate;
//        import java.util.List;
//
//        import static org.junit.jupiter.api.Assertions.assertEquals;
//        import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class AssetControllerTest extends RestTemplateTests {
//
//    private static final ParameterizedTypeReference<List<Asset>> ASSET_LIST =
//            new ParameterizedTypeReference<>() {};
//
//    @Test
//    void one_can_save_one_asset() {
//        savePossessor();
//        saveClassification();
//        Asset asset = saveAsset();
//        assertEquals("LC1121", asset.getId());
//        assertEquals("Hiir", asset.getSubclass());
//    }
//
//    @Test
//    void one_can_query_assets() {
//        savePossessor();
//        saveClassification();
//        saveAsset();
//        ResponseEntity<List<Asset>> exchangeAsset = template()
//                .exchange("/asset", HttpMethod.GET, null, ASSET_LIST);
//        List<Asset> assets = assertOk(exchangeAsset);
//        assertFalse(assets.isEmpty());
//    }
//
//    private Asset saveAsset() {
//        AssetInfo newAsset = new AssetInfo();
//        newAsset.setId("LC1121");
//        newAsset.setName("Hiir Trust");
//        newAsset.setSubclass("Hiir");
//        newAsset.setPossessorId(Long.valueOf("1"));
//        newAsset.setExpirationDate(Date.valueOf(LocalDate.of(2021, 5, 11)));
//        newAsset.setDelicateCondition(Boolean.FALSE);
//        newAsset.setBuildingAbbreviation("UusHoone");
//        newAsset.setRoom("U04-223");
//        newAsset.setComponentAssetId("LC1121");
//        newAsset.setMajorAssetId("LC1121");
//        newAsset.setDescriptionText("Hiir kolme nupuga");
//        newAsset.setPrice(Double.valueOf("19.99"));
//        newAsset.setResidualPrice(Double.valueOf("19.99"));
//        ResponseEntity<Asset> exchangeAsset = template().exchange("/asset", HttpMethod.POST,
//                new HttpEntity<>(newAsset), Asset.class);
//        return assertOk(exchangeAsset);
//    }
//
//    private Possessor savePossessor() {
//        PossessorInfo possessor = new PossessorInfo();
//        possessor.setDivision(1122);
//        ResponseEntity<Possessor> exchangePossessor = template().exchange("/possessor",
//                HttpMethod.POST, new HttpEntity<>(possessor), Possessor.class);
//        return assertOk(exchangePossessor);
//    }
//
//    private Classification saveClassification() {
//        ClassificationInfo newClassification = new ClassificationInfo();
//        newClassification.setMainClass("Arvutikomponent");
//        newClassification.setSubClass("Hiir");
//        ResponseEntity<Classification> exchangeClassification = template()
//                .exchange("/class", HttpMethod.POST,
//                        new HttpEntity<>(newClassification), Classification.class);
//        return assertOk(exchangeClassification);
//    }
//}

