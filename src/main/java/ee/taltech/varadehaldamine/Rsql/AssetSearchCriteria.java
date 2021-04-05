package ee.taltech.varadehaldamine.Rsql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
public class AssetSearchCriteria {
//    @Id
//    private String id;
//    private String name;
//    private Integer structuralUnit;
//    private Integer subdivision;
//    private String mainClass;
//    private String subclass;
//    private String buildingAbbreviature;
//    private String room;
//    private Integer lifeMonthsLeft;
//    private Boolean active;
    @Id
    private String id;
    private String name;
    private String structuralUnitPlusSubdivision;
    private String mainClassPlusSubclass;
    private String buildingAbbreviationPlusRoom;
    private Integer lifeMonthsLeft;
    private Boolean active;

//    private Timestamp createdAt;
//    private Timestamp modifiedAt;

}
