package ee.taltech.varadehaldamine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class KitRelation implements Serializable {


    @Id
    private String componentAssetId;
    private String majorAssetId;
}
