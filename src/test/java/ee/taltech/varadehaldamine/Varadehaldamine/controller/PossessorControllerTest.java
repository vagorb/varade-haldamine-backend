//package ee.taltech.varadehaldamine.Varadehaldamine.controller;
//
//import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
//import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
//import ee.taltech.varadehaldamine.Varadehaldamine.common.RestTemplateTests;
//import org.junit.jupiter.api.Test;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//public class PossessorControllerTest extends RestTemplateTests {
//
//    private static final ParameterizedTypeReference<List<Possessor>> POSSESSOR_LIST
//            = new ParameterizedTypeReference<>() {};
//
//    @Test
//    void one_can_save_possessor() {
//        Possessor savedPossessor = savePossessor();
//		assertEquals(1122, savedPossessor.getDivision());
//    }
//
//    @Test
//    void one_can_query_possessor() {
//        savePossessor();
//        ResponseEntity<List<Possessor>> exchangeClassification = template()
//                .exchange("/possessor", HttpMethod.GET, null, POSSESSOR_LIST);
//        List<Possessor> classifications = assertOk(exchangeClassification);
//        assertFalse(classifications.isEmpty());
//    }
//
//    private Possessor savePossessor() {
//        PossessorInfo possessor = new PossessorInfo();
//        possessor.setDivision(1122);
//        ResponseEntity<Possessor> exchangePossessor = template().exchange("/possessor",
//                HttpMethod.POST, new HttpEntity<>(possessor), Possessor.class);
//        return assertOk(exchangePossessor);
//    }
//}
