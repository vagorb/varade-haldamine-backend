package ee.taltech.varadehaldamine.model;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Audited
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Asset {

    @Id
    private String id;
    private String name;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "sub_class")
    private String subClass;

    private Boolean active;
    private Long userId;
    private Long possessorId;

    private Date expirationDate;
    private Boolean delicateCondition;
    private Boolean checked;

    @CreatedDate
    private Timestamp createdAt;
    @LastModifiedDate
    private Timestamp modifiedAt;
    private Double price;
    private Double residualPrice;
    private Timestamp purchaseDate;
    private String buildingAbbreviature;
    private String room;
    private String description;

//    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;

    public Asset(String id, String name, String sub_class, Long possessor, Date expirationDate,
                 Boolean delicateCondition, Boolean checked, Double price, Double residualPrice, Timestamp
                         purchaseDate, String buildingAbbreviature, String room, String description) {
        this.id = id;
        this.name = name;
        this.active = true;
        this.checked = false;
        this.subClass = sub_class;
        this.possessorId = possessor;
        this.expirationDate = expirationDate;
        this.delicateCondition = delicateCondition;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.modifiedAt = new Timestamp(System.currentTimeMillis());
        this.checked = checked;
        this.price = price;
        this.residualPrice = residualPrice;
        this.purchaseDate = purchaseDate;
        this.buildingAbbreviature = buildingAbbreviature;
        this.room = room;
        this.description = description;
    }
}
