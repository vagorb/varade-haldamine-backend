package ee.taltech.varadehaldamine.Varadehaldamine.Controller;


import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("asset")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {

    
    @Autowired
    AssetService assetService;

    @GetMapping
    public List<Asset> getAll() {
        return assetService.findAll();
    }
}
