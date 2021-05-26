package ee.taltech.varadehaldamine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private Integer division;

    @ElementCollection
    @CollectionTable(name = "Inventory_assets", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "assets")
    private Set<String> assets = new HashSet<>();
}
