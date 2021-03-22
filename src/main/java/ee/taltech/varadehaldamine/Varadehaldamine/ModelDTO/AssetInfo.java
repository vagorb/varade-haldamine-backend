package ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class AssetInfo {

    private String id;
    private String name;
    private Boolean active;
    private Long user_id;
    private Long possessor_id;
    private Date expiration_date;
    private Boolean delicate_condition;
    private Timestamp created_at;
    private Timestamp modified_at;

    // table Worth
    private Double price;
    private Double residual_price;
    private Timestamp purchase_date;

    // table Classification
    private String subclass;
    private String main_class;

    // table Kit_relation
    private String component_asset_id;
    private String major_asset_id;

    // table Address
    private String building_abbreviature;
    private String room;

    // table Description
    private String descriptionText;

    // table Comment
    private String commentText;

}
