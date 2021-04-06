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
public class Address {
    @Id

    private String assetId;
    private String buildingAbbreviature;
    private String room;
}
