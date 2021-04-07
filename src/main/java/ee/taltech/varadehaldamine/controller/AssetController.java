package ee.taltech.varadehaldamine.controller;


import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("asset")
@RestController
public class AssetController {

    @Autowired
    AssetService assetService;

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping("/admin")
    public String groupOne() {
        return "Hello Group 1 Users!";
    }

    @GetMapping("/filtered")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<Page<AssetInfoShort>> getAssets(
            AssetInfoShort assetSearchCriteria,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            // Using default value of 10 instead of a pathVariable
            @RequestParam(required = false, value = "size", defaultValue = "10") int size,

            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(assetService.getAssetsList(page, size, assetSearchCriteria, order, sortBy), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_admin')")
    public List<AssetInfoShort> getAll() {
        return assetService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public AssetInfo getAssetById(@PathVariable String id) {
        return assetService.getAssetById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<Object> addAsset(@RequestBody AssetInfo asset) {
        if (assetService.addAsset(asset) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
