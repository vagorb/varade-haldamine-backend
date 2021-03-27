package ee.taltech.varadehaldamine.Varadehaldamine.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Classification {
    @Id
    private String subClass;
    private String mainClass;
}
