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

    @Id
    private String id;
    private String name;
    private String structuralUnitPlusSubdivision;
    private String mainClassPlusSubclass;
    private String buildingAbbreviationPlusRoom;
    private Integer lifeMonthsLeft;
    private Boolean active;
    private Boolean checked;

    public AssetInfoShort(String id, String name, Integer structuralUnit, Integer subdivision, String mainClassPlusSubclass, String buildingAbbreviature, String room, Date date, Boolean active, Boolean checked) {
        this.id = id;
        this.name = name;
        if (subdivision != null) {
            this.structuralUnitPlusSubdivision = structuralUnit + " " + subdivision;
        } else {
            this.structuralUnitPlusSubdivision = structuralUnit.toString();
        }
        this.mainClassPlusSubclass = mainClassPlusSubclass;
        if (room != null) {
            this.buildingAbbreviationPlusRoom = buildingAbbreviature + " " + room;
        } else {
            this.buildingAbbreviationPlusRoom = buildingAbbreviature;
        }
        this.lifeMonthsLeft = 0;
        if (date != null) {
            int months = (int) ChronoUnit.MONTHS.between(LocalDate.now(), Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            if (months < 0) {
                months = 0;
            }
            this.lifeMonthsLeft = months;
        }
        this.active = active;
        this.checked = checked;
    }
}
