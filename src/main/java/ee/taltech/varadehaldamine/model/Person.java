package ee.taltech.varadehaldamine.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String azureId;

    public Person(String username, String email, String azureId) {
        this.username = username;
        this.email = email;
        this.azureId = azureId;
    }
}
