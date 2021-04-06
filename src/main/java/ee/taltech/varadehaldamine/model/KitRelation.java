package ee.taltech.varadehaldamine.model;

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
public class KitRelation {
    @Id
    private String componentAssetId;
    private String majorAssetId;
}
