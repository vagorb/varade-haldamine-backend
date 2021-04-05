package ee.taltech.varadehaldamine.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentInfo {
    private String assetId;
    private String text;
    private Long createdAt;
}
