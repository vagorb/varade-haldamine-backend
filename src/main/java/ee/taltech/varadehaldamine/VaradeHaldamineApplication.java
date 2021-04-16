package ee.taltech.varadehaldamine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VaradeHaldamineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaradeHaldamineApplication.class, args);
    }
}
