package ee.taltech.varadehaldamine.Varadehaldamine.Controller;


import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.AssetInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Object> addAsset(@RequestBody AssetInfo asset){
        if (assetService.addAsset(asset) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
