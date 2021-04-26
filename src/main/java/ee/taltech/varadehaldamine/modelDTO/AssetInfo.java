package ee.taltech.varadehaldamine.modelDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
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
    private Date modifiedAt;

    // table Worth
    private Double price;
    private Double residualPrice;
    private Date purchaseDate;
    private Boolean isPurchased; //

    // table Classification
    private String subclass;
    private String mainClass;

    // table Kit_relation
    private String componentAssetId;
    private String majorAssetId;
    private String kitPartName; //

    // table Address
    private String buildingAbbreviation;
    private String room;

    // table Description
    private String descriptionText;

    // table Comment
    private String commentText;

    // Person data
    private String firstname;
    private String lastname;

    // Possessor data
    private Integer structuralUnit;
    private Integer subdivision;

}
