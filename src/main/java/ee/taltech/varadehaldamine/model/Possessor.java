package ee.taltech.varadehaldamine.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Audited
@Entity
public class Possessor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer structuralUnit;
    private Integer subdivision;

//    @OneToMany(mappedBy = "possessorId", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Asset> assets;
}
