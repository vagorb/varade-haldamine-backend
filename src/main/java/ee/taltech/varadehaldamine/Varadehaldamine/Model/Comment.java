package ee.taltech.varadehaldamine.Varadehaldamine.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String assetId;
    private String text;
    private Timestamp createdAt;

    public Comment(String assetId, String text) {
        this.assetId = assetId;
        this.text = text;
    }
}
