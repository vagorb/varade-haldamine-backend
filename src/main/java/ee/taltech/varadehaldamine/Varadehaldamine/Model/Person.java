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
    private String firstname;
    private String lastname;

    public Person(String azureId, String firstname, String lastName) {
        this.azureId = azureId;
        this.firstname = firstname;
        this.lastname = lastName;
    }
}
