package ee.taltech.varadehaldamine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Audited
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    private String text;
    private Timestamp createdAt;

    public Comment(Asset asset, String text, Timestamp createdAt) {
        this.asset = asset;
        this.text = text;
        this.createdAt = createdAt;
    }
}
