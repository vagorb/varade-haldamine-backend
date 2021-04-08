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

    public Asset(String id, String name, String subclass, Long possessorId, Date expirationDate,
                 Boolean delicateCondition) {
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
    }
}
