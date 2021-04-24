package ee.taltech.varadehaldamine.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Audited
@Entity
public class Classification {
    @Id
    private String subClass;
    private String mainClass;


//    @OneToMany(mappedBy = "subClass", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Asset> assets;
}
