package ee.taltech.varadehaldamine.Varadehaldamine;
import ee.taltech.varadehaldamine.Varadehaldamine.Model.Vara;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.VaradeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component

public class ApplicationInit implements CommandLineRunner {
    @Autowired
    VaradeService service;

    @Override
    public void run(String... args) {
        Vara vara = new Vara("Vasja", "Dima");
        service.save(vara);
    }
}
