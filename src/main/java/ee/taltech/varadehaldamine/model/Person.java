package ee.taltech.varadehaldamine.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String azureId;
    private String firstname;
    private String lastname;

    public Person(String azureId, String firstName, String lastName) {
        this.azureId = azureId;
        this.firstname = firstName;
        this.lastname = lastName;
    }
}
