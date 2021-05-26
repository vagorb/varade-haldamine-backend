package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.PossessorNotFoundException;
import ee.taltech.varadehaldamine.exception.WrongCurrentUserRoleException;
import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.model.Inventory;
import ee.taltech.varadehaldamine.repository.AssetRepository;
import ee.taltech.varadehaldamine.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PossessorService possessorService;

    public Inventory createInventory(List<String> roles, Long id) {
        Integer userDivision = getDivision(roles);
        if (userDivision == null) {
            throw new PossessorNotFoundException();
        }
        Inventory newInventory = new Inventory();
        newInventory.setDivision(userDivision);
        newInventory.setStartDate(new Date(System.currentTimeMillis()));
        Set<String> inventoryAssets = getAssetsSetByPossessor(userDivision);
        newInventory.setAssets(inventoryAssets);
        return inventoryRepository.save(newInventory);
    }

    public Inventory endInventory(Long inventoryId) {
        Inventory dbInventory = inventoryRepository.findInventoryById(inventoryId);
        System.out.println("oke");
        if (dbInventory != null) {
            Set<String> allCurrentAssets = getAssetsSetByPossessor(dbInventory.getDivision());
            Set<String> allInventoryAssets = dbInventory.getAssets();
            for (String assetId : allCurrentAssets) {
                allInventoryAssets.add(assetId);
                if (!assetRepository.findAssetById(assetId).getChecked()) {
                    return null;
                }
            }
            System.out.println("yes");
            dbInventory.setAssets(allInventoryAssets);
            dbInventory.setEndDate(new Date(System.currentTimeMillis()));
            return inventoryRepository.save(dbInventory);
        }
        return null;
    }

    public Set<String> getAssetsSetByPossessor(Integer divison) {
        List<Asset> allAssets = assetRepository.findAll();
        Set<String> inventoryAssets = new HashSet<>();
        for (Asset asset : allAssets) {
            if (possessorService.getPossesorById(asset.getPossessorId()).getId()
                    .equals(possessorService.findPossessor(divison, null).getId())) {
                inventoryAssets.add(asset.getId());
            }
        }
        return inventoryAssets;
    }

    public List<Asset> getAssetsInInventory(Long inventoryId) {
        Inventory dbInventory = inventoryRepository.findInventoryById(inventoryId);
        if (dbInventory != null) {
            List<Asset> allCurrentAssets = assetRepository.findAll();
            Set<String> allInventoryAssets = dbInventory.getAssets();
            List<Asset> result = new ArrayList<>();
            for (Asset asset : allCurrentAssets) {
                if (allInventoryAssets.contains(asset.getId())) {
                    result.add(asset);
                }
            }
            return result;
        }
        return null;
    }

    private Integer getDivision(List<String> roles) {
        Integer division = null;
        for (String role: roles) {
            if (role.startsWith("ROLE_D") && division == null) {
                try {
                    division = Integer.valueOf(role.replace("ROLE_D", ""));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Given not integer in division filter field");
                }
            }
        }
        System.out.println(division);
        if (division == null) {
            throw new WrongCurrentUserRoleException("Check user roles");
        }
        return division;
    }
}
