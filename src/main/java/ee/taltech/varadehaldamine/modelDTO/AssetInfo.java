package ee.taltech.varadehaldamine.modelDTO;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssetInfo {

    private String id;
    private String name;
    private Boolean active;
    private Long userId;
    private Long possessorId;
    private Integer lifeMonthsLeft; //
    private Boolean delicateCondition;
    private Boolean checked;
    private Date createdAt;
    private Timestamp modifiedAt;

    // table Worth
    private Double price;
    private Double residualPrice;
    private Date purchaseDate;
    private Boolean isPurchased;

    // table Classification
    // table Kit_relation
    // table Address
    private String buildingAbbreviation;
    private String room;

    // table Description
    private String descriptionText;

    private String subclass;
    private String mainClass;

    private String componentAssetId;
    private String majorAssetId;
    private String kitPartName;

    // table Comment
    private String commentText;

    // Person data
    private String username;

    // Possessor data
    private Integer structuralUnit;
    private Integer subdivision;

    public AssetInfo(String id, String name, Boolean active, Long userId, Long possessorId, java.util.Date date, Boolean delicateCondition, Boolean checked, java.util.Date createdAt, java.util.Date modifiedAt, Double price, Double residualPrice, java.util.Date purchaseDate, String subclass, String mainClass, String majorAssetId, String buildingAbbreviation, String room, String descriptionText, String username, Integer structuralUnit, Integer subdivision) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.userId = userId;
        this.possessorId = possessorId;
        this.lifeMonthsLeft = 0;
        if (date != null) {
            int months = (int) ChronoUnit.MONTHS.between(LocalDate.now(), Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            if (months < 0) {
                months = 0;
            }
            this.lifeMonthsLeft = months;
        }
        this.delicateCondition = delicateCondition;
        this.checked = checked;
        this.createdAt = new Date(createdAt.getTime());
        this.modifiedAt = new Timestamp(modifiedAt.getTime());
        this.price = price;
        this.residualPrice = residualPrice;
        if (purchaseDate != null) {
            this.purchaseDate = new Date(purchaseDate.getTime());
        }
        this.subclass = subclass;
        this.mainClass = mainClass;
        if (majorAssetId != null) {
            this.majorAssetId = majorAssetId;
            if (majorAssetId.equals(id)) {
                kitPartName = "Peavara";
            } else {
                kitPartName = "Komponent";
            }
        }
        this.buildingAbbreviation = buildingAbbreviation;
        this.room = room;
        this.descriptionText = descriptionText;
        this.username = username;
        this.structuralUnit = structuralUnit;
        this.subdivision = subdivision;
    }

}
