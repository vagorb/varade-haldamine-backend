package ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class AssetInfo {

    private String id;
    private String name;
    private Boolean active;
    private Long userId;
    private Long possessorId;
    private Date expirationDate;
    private Boolean delicateCondition;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    // table Worth
    private Double price;
    private Double residualPrice;
    private Timestamp purchaseDate;

    // table Classification
    private String subclass;

    // table Kit_relation
    private String componentAssetId;
    private String majorAssetId;

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
    private Integer institute;
    private Integer division;
    private Integer subdivision;

}
