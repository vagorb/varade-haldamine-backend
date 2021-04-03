package ee.taltech.varadehaldamine.Varadehaldamine.Rsql;

import javax.persistence.Id;
import java.sql.Date;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPossessorId() {
        return possessorId;
    }

    public void setPossessorId(Long possessorId) {
        this.possessorId = possessorId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getDelicateCondition() {
        return delicateCondition;
    }

    public void setDelicateCondition(Boolean delicateCondition) {
        this.delicateCondition = delicateCondition;
    }

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
