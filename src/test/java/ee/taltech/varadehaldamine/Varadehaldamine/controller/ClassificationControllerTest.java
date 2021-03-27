package ee.taltech.varadehaldamine.Varadehaldamine.controller;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.common.RestTemplateTests;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClassificationControllerTest extends RestTemplateTests {

    private static final ParameterizedTypeReference<List<Classification>> CLASSIFICATION_LIST
            = new ParameterizedTypeReference<>() {};


    @Test
    void one_can_save_classifications() {
        Classification savedClassification = saveClassification();
        assertEquals("Hiir", savedClassification.getMainClass());
        assertEquals("Arvutikomponent", savedClassification.getSubClass());
    }

    @Test
    void one_can_query_classifications() {
        saveClassification();
        ResponseEntity<List<Classification>> exchangeClassification = template()
                .exchange("/class", HttpMethod.GET, null, CLASSIFICATION_LIST);
        List<Classification> classifications = assertOk(exchangeClassification);
        assertFalse(classifications.isEmpty());
    }

    private Classification saveClassification() {
        ClassificationInfo newClassification = new ClassificationInfo();
        newClassification.setMainClass("Hiir");
        newClassification.setSubClass("Arvutikomponent");
        ResponseEntity<Classification> exchangeClassification = template()
                .exchange("/class", HttpMethod.POST,
                        new HttpEntity<>(newClassification), Classification.class);
        return assertOk(exchangeClassification);
    }
}
