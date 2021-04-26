package ee.taltech.varadehaldamine.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Worth {
    @Id
    private String assetId;
    private Double price;
    private Double residualPrice;
    private Timestamp purchaseDate;

    public Worth(String assetId, Double price, Double residualPrice) {
        this.assetId = assetId;
        this.price = price;
        this.residualPrice = residualPrice;
    }
}
