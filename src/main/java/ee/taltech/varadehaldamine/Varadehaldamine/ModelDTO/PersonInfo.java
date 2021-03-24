package ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class PersonInfo {
    private String azureId;
    private String firstName;
    private String lastName;
}
