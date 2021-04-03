package ee.taltech.varadehaldamine.Varadehaldamine.Model;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
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
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
