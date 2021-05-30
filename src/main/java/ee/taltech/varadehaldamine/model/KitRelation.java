package ee.taltech.varadehaldamine.model;

import lombok.*;

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
