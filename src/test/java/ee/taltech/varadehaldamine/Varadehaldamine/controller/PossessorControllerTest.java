package ee.taltech.varadehaldamine.Varadehaldamine.controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PossessorControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void one_can_save_possessor() {
        PossessorInfo possessor = new PossessorInfo();
        possessor.setDivision(11122);
        ResponseEntity<Possessor> exchangePossessor = testRestTemplate.exchange("/possessor",
                HttpMethod.POST, new HttpEntity<>(possessor), Possessor.class);
        System.out.println(exchangePossessor.getBody());
		/*AssetInfo assetInfo = new AssetInfo();
		assetInfo.setId("1132");
		assetInfo.setName("Laualamp");
		assetInfo.setSubclass("Mööbel");*/
    }
}
