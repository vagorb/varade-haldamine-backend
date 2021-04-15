package ee.taltech.varadehaldamine.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Asset {

    @Id
    private String id;
    private String name;
    private String subClass;
    private Boolean active;
    private Long userId;
    private Long possessorId;
    private Date expirationDate;
    private Boolean delicateCondition;
    private Boolean checked;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Double price;
    private Double residualPrice;
    private Timestamp purchaseDate;
    private String buildingAbbreviature;
    private String room;
    private String description;

    public Asset(String id, String name, String subclass, Long possessorId, Date expirationDate,
                 Boolean delicateCondition, Boolean checked, Double price, Double residualPrice, Timestamp
                         purchaseDate, String buildingAbbreviature, String room, String description) {
        this.id = id;
        this.name = name;
        this.active = true;
        this.checked = false;
        this.subClass = subclass;
        this.possessorId = possessorId;
        this.expirationDate = expirationDate;
        this.delicateCondition = delicateCondition;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        this.checked = checked;
        this.price = price;
        this.residualPrice = residualPrice;
        this.purchaseDate = purchaseDate;
        this.buildingAbbreviature = buildingAbbreviature;
        this.room = room;
        this.description = description;
    }
}
