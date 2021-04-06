package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.modelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.util.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AssetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAddAsset() throws Exception {
        AssetInfo newAsset = new AssetInfo();
        ClassificationInfo newClassification = new ClassificationInfo();
        PossessorInfo newPossessor = new PossessorInfo();

        newClassification.setMainClass("Arvutikomponent");
        newClassification.setSubClass("Hiir");

        newPossessor.setStructuralUnit(1122);

        newAsset.setId("LC1121");
        newAsset.setName("Hiir Trust");
        newAsset.setSubclass("Hiir");
        newAsset.setPossessorId(Long.valueOf("1"));
        newAsset.setDelicateCondition(Boolean.FALSE);
        newAsset.setBuildingAbbreviation("UusHoone");
        newAsset.setRoom("U04-223");
        newAsset.setComponentAssetId("LC1121");
        newAsset.setMajorAssetId("LC1121");
        newAsset.setDescriptionText("Hiir kolme nupuga");
        newAsset.setPrice(Double.valueOf("19.99"));
        newAsset.setResidualPrice(Double.valueOf("19.99"));

        mvc.perform(MockMvcRequestBuilders.post("/class").contentType(MediaType.APPLICATION_JSON)
                .content(JsonParser.asJsonString(newClassification)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        mvc.perform(MockMvcRequestBuilders.post("/possessor").contentType(MediaType.APPLICATION_JSON)
                .content(JsonParser.asJsonString(newPossessor)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        mvc.perform(MockMvcRequestBuilders.post("/asset").contentType(MediaType.APPLICATION_JSON)
                .content(JsonParser.asJsonString(newAsset)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

    }
}
