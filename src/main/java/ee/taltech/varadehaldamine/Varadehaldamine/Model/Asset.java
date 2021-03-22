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
    private String subclass;
    private Boolean active;
    private Long user_id;
    private Long possessor_id;
    private Date expiration_date;
    private Boolean delicate_condition;
    private Timestamp created_at;
    private Timestamp modified_at;
}
