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


//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Timestamp getModifiedAt() {
//        return modifiedAt;
//    }
//
//    public void setModifiedAt(Timestamp modifiedAt) {
//        this.modifiedAt = modifiedAt;
//    }
    //    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
}
