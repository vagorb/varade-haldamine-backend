package ee.taltech.varadehaldamine.Varadehaldamine.controller;

import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.util.JsonParser;
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
public class PossessorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAddPossessor() throws Exception {
        PossessorInfo newPossessor = new PossessorInfo();

        newPossessor.setStructuralUnit(1122);

        mvc.perform(MockMvcRequestBuilders.post("/possessor").contentType(MediaType.APPLICATION_JSON)
        .content(JsonParser.asJsonString(newPossessor)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
}
