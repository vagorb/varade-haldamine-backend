package ee.taltech.varadehaldamine.Varadehaldamine.Model;

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String azureId;
    private String firstName;
    private String lastName;

    public Person(String azureId, String firstName, String lastName) {
        this.azureId = azureId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
