package ee.taltech.varadehaldamine.controller;


import ee.taltech.varadehaldamine.exception.InventoryExcelException;
import ee.taltech.varadehaldamine.filesHandling.ExcelAssetExporter;
import ee.taltech.varadehaldamine.model.Person;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import ee.taltech.varadehaldamine.service.AssetService;
import ee.taltech.varadehaldamine.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequestMapping("asset")
@RestController
public class AssetController {

    @Autowired
    PersonService personService;

    @Autowired
    AssetService assetService;

    /**
     * Method to get username
     *
     * @return name of user
     */
    @GetMapping("/username")
    public String getUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) userObject;
        return defaultOidcUser.getFullName();
    }

    /**
     * This method is used by front-end to understand, which user (his/her role)
     * is connected to show right pages to user
     * <p>
     * Roles: every user(Tavakasutaja).
     *
     * @return role name
     */
    @GetMapping("/accountt")
    public String getAccountt() {
        Collection<? extends GrantedAuthority> list = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (list.toString().contains("Raamatupidaja")) {
            return "Raamatupidaja";
        } else if (list.toString().contains("??ksuseJuht")) {
            return "??ksuseJuht";
        } else if (list.toString().contains("KomisjoniLiige")) {
            return "KomisjoniLiige";
        } else if (list.toString().contains("Tavakasutaja")) {
            return "Tavakasutaja";
        } else {
            return "Unauthorized";
        }
    }

    /**
     * Get user to front.
     *
     * @return user json
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/account")
    public Object getAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Logout function to logout user from Azure
     * <p>
     * Roles: every user(Tavakasutaja).
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }


    /**
     * Method to get assets, also ables to filter and sort assets by different criteria.
     * <p>
     * Roles: every user(Tavakasutaja).
     *
     * @param assetSearchCriteria AssetInfoShort to filter assets by criteria in it
     * @param page                a nr of page to return, default 0
     * @param size                a size of returned page, default 10
     * @param order               descending or ascending order to sort assets by, default ascending
     * @param sortBy              a criteria to sort by, default by asset id
     * @return paged AssetInfoShort to show it in table
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/filtered")
    @ResponseBody
    public ResponseEntity<Page<AssetInfoShort>> getAssets(
            AssetInfoShort assetSearchCriteria,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            // Using default value of 10 instead of a pathVariable
            @RequestParam(required = false, value = "size", defaultValue = "10") int size,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy) {
        Person user = personService.getCurrentUser();
        List<String> authorities = personService.getAuthorities();
        return new ResponseEntity<>(assetService.getAssetsList(page, size, assetSearchCriteria, order, sortBy, authorities), HttpStatus.OK);
    }

    /**
     * Method which returns assets, which are in user use.
     * <p>
     * Roles: every user(Tavakasutaja).
     *
     * @param page a nr of page to return, default 0
     * @param size a size of returned page, default 10
     * @return paged AssetInfoShort to show it in table
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/own")
    public Page<AssetInfoShort> getAssetsUserOwning(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            // Using default value of 10 instead of a pathVariable
            @RequestParam(required = false, value = "size", defaultValue = "10") int size) {
        //register if no such user
        Person user = personService.getCurrentUser();
        return assetService.getAssetsUserOwning(user.getId(), page, size);
    }

    /**
     * Get audit of the asset by id.
     * Every asset has different variants of the states in past, so to see the, we get it by index.
     * <p>
     * Roles: Raamatupidaja and Esimees (division boss).
     *
     * @param assetId asset which audit we need
     * @param index   a nr of the asset audit. 0 index is the newest variant of the asset.
     * @return all information of the asset
     */
//    @PreAuthorize("hasRole('ROLE_Raamatupidaja') || hasRole('ROLE_Esimees')")
    @Transactional
    @GetMapping("/audit")
    public AssetInfo getAuditByIndex(@RequestParam String assetId, @RequestParam Integer index) {
//        //register if no such user
//        personService.getCurrentUser();
        Page<AssetInfo> assets = assetService.getAuditById(assetId);
        return assets.getContent().get(index);
    }


    /**
     * Method to get all audits in paged variant of the asset.
     * <p>
     * Roles: Raamatupidaja and Esimees (division boss).
     *
     * @param id asset which audit we need
     * @return paged all audits as AssetInfo
     */
    //    @PreAuthorize("hasRole('ROLE_Raamatupidaja') || hasRole('ROLE_Esimees')")
    @Transactional
    @GetMapping("/audit/{id}")
    public Page<AssetInfo> getAuditById(@PathVariable String id) {
//        //register if no such user
//        personService.getCurrentUser();
        return assetService.getAuditById(id);
    }

    /**
     * Method to get asset by it's id.
     * <p>
     * Roles: every user(Tavakasutaja).
     * <p>
     * If user has role of raamatupidaja, then is ability to get every asset,
     * otherwise user can get asset, if his/her division is same as assets' division,
     * also if user is as asset user, but divisions are different, user can get asset.
     *
     * @param id asset id
     * @return all information of the asset
     */
    @PreAuthorize("hasRole('ROLE_Tavakasutaja')")
    @GetMapping("/{id}")
    public AssetInfo getAssetById(@PathVariable String id) {
        //register if no such user
        Person person = personService.getCurrentUser();
        List<String> authorities = personService.getAuthorities();
        return assetService.getAssetById(id, authorities, person.getId());
    }

    /**
     * Method to update data of the asset.
     * <p>
     * Roles: only Raamatupidaja.
     * <p>
     *
     * @param assetInfo info of the new data of the fields to change
     * @param id        asset id which to update
     * @return asset
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAsset(@RequestBody AssetInfo assetInfo, @PathVariable String id) {
        if (assetService.update(assetInfo, id) != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Check if user has ability to make inventory on asset.
     *
     * @param id asset id
     * @return status
     */
    @PreAuthorize("hasRole('ROLE_KomisjoniLiige')")
    @PutMapping("/check/{id}")
    public ResponseEntity<Object> checkAsset(@PathVariable String id) {
        List<String> authorities = personService.getAuthorities();
        if (assetService.check(id, authorities) != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Check if user has ability to make inventory on assets.
     *
     * @param assetIds list of asset ids
     * @return status
     */
    @PreAuthorize("hasRole('ROLE_KomisjoniLiige')")
    @PutMapping("/check")
    public ResponseEntity<Object> checkMultiple(@RequestBody List<String> assetIds) {
        List<String> authorities = personService.getAuthorities();
        if (assetService.checkMultiple(assetIds, authorities)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Method to add new asset.
     * <p>
     * Roles: only Raamatupidaja.
     *
     * @param asset new asset
     * @return message to front-end, the asset is added or not
     */
    @PreAuthorize("hasRole('ROLE_Raamatupidaja')")
    @PostMapping
    public ResponseEntity<Object> addAsset(@RequestBody AssetInfo asset) {
        if (assetService.addAsset(asset) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @GetMapping("/exportExcel")
    public void exportAllAssetsExcel(HttpServletResponse response) {
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=assets.xlsx";
        response.setHeader(headerKey, headerValue);

        List<AssetInfo> assets = assetService.getAllInfoAboutAssetASC();

        ExcelAssetExporter excelAssetExporter = new ExcelAssetExporter(assets);
        try {
            excelAssetExporter.export(response);
        } catch (IOException e) {
            System.out.println("error when asset excel generating: " + e);
        }
    }

    /**
     * Method to export excel with all assets
     *
     * @param response HttpServletResponse
     */
    @PreAuthorize("hasRole('ROLE_??ksuseJuht')")
    @Transactional
    @GetMapping("/inventoryExcel")
    public void getInventoryLists(HttpServletResponse response) {
        List<String> authorities = personService.getAuthorities();
        List<AssetInfo> assets = assetService.getLists(authorities);
        if (assets.size() == 0) {
            throw new InventoryExcelException("No assets");
        }
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=assets.xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelAssetExporter excelAssetExporter = new ExcelAssetExporter(assets);
        try {
            excelAssetExporter.export(response);
        } catch (IOException e) {
            System.out.println("error when asset excel generating: " + e);
        }
    }

    /**
     * Get inventory by year
     *
     * @param year     int of year
     * @param response HttpServletResponse
     */
    @PreAuthorize("hasRole('ROLE_??ksuseJuht')")
    @Transactional
    @GetMapping("/inventory/{year}")
    public void getInventoryListsByYear(@PathVariable int year, HttpServletResponse response) {
        List<String> authorities = personService.getAuthorities();
        List<AssetInfo> assets = assetService.getInventoryListsByYear(authorities, year);
        if (assets.size() == 0) {
            throw new InventoryExcelException("No assets");
        }
        ExcelAssetExporter excelAssetExporter = new ExcelAssetExporter(assets);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=assets.xlsx";
        response.setHeader(headerKey, headerValue);


        try {
            excelAssetExporter.export(response);
        } catch (IOException e) {
            System.out.println("error when asset excel generating: " + e);
        }

    }
}
