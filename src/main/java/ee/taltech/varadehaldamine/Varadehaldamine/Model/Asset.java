package ee.taltech.varadehaldamine.Varadehaldamine.Model;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    public Asset(String id, String name, String subclass, Long possessorId, Date expirationDate, Boolean delicateCondition) {
        this.id = id;
        this.name = name;
        this.subClass = subclass;
        this.possessorId = possessorId;
        this.expirationDate = expirationDate;
        this.delicateCondition = delicateCondition;
    }
}
