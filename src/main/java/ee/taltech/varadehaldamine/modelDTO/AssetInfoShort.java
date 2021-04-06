package ee.taltech.varadehaldamine.modelDTO;

import lombok.*;

import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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

    public AssetInfoShort(String id, String name, String structuralUnitPlusSubdivision, String mainClassPlusSubclass, String buildingAbbreviationPlusRoom, Date date, Boolean active) {
        this.id = id;
        this.name = name;
        this.structuralUnitPlusSubdivision = structuralUnitPlusSubdivision;
        this.mainClassPlusSubclass = mainClassPlusSubclass;
        this.buildingAbbreviationPlusRoom = buildingAbbreviationPlusRoom;
        this.lifeMonthsLeft = 0;
        if (date != null){
            int months = (int) ChronoUnit.MONTHS.between(LocalDate.now(), Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            if (months < 0) {
                months = 0;
            }
            this.lifeMonthsLeft = months;
        }
        this.active = active;
    }
}
