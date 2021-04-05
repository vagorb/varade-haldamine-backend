package ee.taltech.varadehaldamine.Rsql;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.sql.Date;

@Getter
@Setter
public class AssetSearchCriteria {
    @Id
    private String id;
    private String name;
    private String subClass;
    private Boolean active;
    private Long userId;
    private Long possessorId;
    private Date expirationDate;
    private Boolean delicateCondition;
//    private Timestamp createdAt;
//    private Timestamp modifiedAt;

}
