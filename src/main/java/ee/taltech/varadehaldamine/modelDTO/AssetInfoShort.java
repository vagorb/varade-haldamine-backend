package ee.taltech.varadehaldamine.modelDTO;

import lombok.*;

import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssetInfoShort {
//    private String id;
//    private String name;
//    private Boolean active;
//
//    // Address data
//    private String buildingAbbreviationPlusRoom;
//
//    // Person data
//    private String personName;
//
//    private Date modifiedAt;

    @Id
    private String id;
    private String name;
    private String structuralUnitPlusSubdivision;
    private String mainClassPlusSubclass;
    private String buildingAbbreviationPlusRoom;
    private Integer lifeMonthsLeft;
    private Boolean active;
}
