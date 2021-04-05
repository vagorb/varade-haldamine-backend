package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
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
public class ClassificationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAddClassification() throws Exception {
        ClassificationInfo newClassification = new ClassificationInfo();

        newClassification.setMainClass("Arvutikomponent");
        newClassification.setSubClass("Hiir");

        mvc.perform(MockMvcRequestBuilders.post("/class").contentType(MediaType.APPLICATION_JSON)
                .content(JsonParser.asJsonString(newClassification)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}
