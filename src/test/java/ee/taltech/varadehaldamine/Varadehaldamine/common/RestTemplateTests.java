//package ee.taltech.varadehaldamine.Varadehaldamine.common;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class RestTemplateTests {
//
//    @Autowired
//    protected TestRestTemplate testRestTemplate;
//
//    public <T> T assertOk(ResponseEntity<T> exchange) {
//        assertNotNull(exchange.getBody());
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
//        return exchange.getBody();
//    }
//
//    public TestRestTemplate template() {
//        return testRestTemplate;
//    }
//
//
//}
