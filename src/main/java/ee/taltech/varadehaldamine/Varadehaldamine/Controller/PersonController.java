package ee.taltech.varadehaldamine.Varadehaldamine.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("person")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {
}
