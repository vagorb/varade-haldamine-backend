package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.service.KitRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("kit")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class KitRelationController {

    @Autowired
    KitRelationService kitRelationService;

    /**
     * Method to get all major assets of the system.
     * Used when adding new asset.
     * <p>
     * Roles: Raamatupidaja.
     *
     * @return List of major assets ids as strings
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @GetMapping("majorAssets")
    public List<String> getAllMajorAssets() {
        return kitRelationService.findAllMajorAssets();
    }


}
