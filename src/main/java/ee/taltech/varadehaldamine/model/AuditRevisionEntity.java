package ee.taltech.varadehaldamine.model;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;


import javax.persistence.*;

@Entity
@Table(name = "revision_info")
@RevisionEntity
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp")),
        @AttributeOverride(name = "id", column = @Column(name = "revision_id"))
})

public class AuditRevisionEntity extends DefaultRevisionEntity {

}