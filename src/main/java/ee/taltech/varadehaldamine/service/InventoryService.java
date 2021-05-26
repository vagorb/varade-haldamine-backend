package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.AssetIsNotChecked;
import ee.taltech.varadehaldamine.exception.OngoingInventoryAlreadyExists;
import ee.taltech.varadehaldamine.exception.OngoingInventoryDoesNotExist;
import ee.taltech.varadehaldamine.exception.WrongCurrentUserRoleException;
import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.model.Inventory;
import ee.taltech.varadehaldamine.repository.AssetRepository;
import ee.taltech.varadehaldamine.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PossessorService possessorService;

    public Inventory createInventory(List<String> roles) {
        Integer userDivision = getDivision(roles);
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(userDivision) && inventory.getEndDate() == null) {
                throw new OngoingInventoryAlreadyExists();
            }
        }
        setAssetsUnchecked(userDivision);
        Inventory newInventory = new Inventory();
        newInventory.setDivision(userDivision);
        newInventory.setStartDate(new Date(System.currentTimeMillis()));
        Set<String> inventoryAssets = getAssetsSetByPossessor(userDivision);
        newInventory.setAssets(inventoryAssets);
        return inventoryRepository.save(newInventory);
    }

    public Inventory endInventory(List<String> roles) {
        Integer userDivision = getDivision(roles);
        Inventory dbInventory = null;
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(userDivision) && inventory.getEndDate() == null) {
                dbInventory = inventory;
                break;
            }
        }
        if (dbInventory == null) {
            throw new OngoingInventoryDoesNotExist();
        }
        if (userDivision.equals(dbInventory.getDivision())) {
            Set<String> allCurrentAssets = getAssetsSetByPossessor(dbInventory.getDivision());
            Set<String> allInventoryAssets = dbInventory.getAssets();
            for (String assetId : allCurrentAssets) {
                allInventoryAssets.add(assetId);
                if (!assetRepository.findAssetById(assetId).getChecked()) {
                    throw new AssetIsNotChecked(assetId);
                }
            }
            dbInventory.setAssets(allInventoryAssets);
            dbInventory.setEndDate(new Date(System.currentTimeMillis()));
            return inventoryRepository.save(dbInventory);
        }
        return null;
    }

    public Set<String> getAssetsSetByPossessor(Integer division) {
        List<Asset> allAssets = assetRepository.findAll();
        Set<String> inventoryAssets = new HashSet<>();
        for (Asset asset : allAssets) {
            if (possessorService.getPossesorById(asset.getPossessorId()).getId()
                    .equals(possessorService.findPossessor(division, null).getId())) {
                inventoryAssets.add(asset.getId());
            }
        }
        return inventoryAssets;
    }

    public void setAssetsUnchecked(Integer division) {
        List<Asset> allAssets = assetRepository.findAll();
        for (Asset asset : allAssets) {
            if (possessorService.getPossesorById(asset.getPossessorId()).getId()
                    .equals(possessorService.findPossessor(division, null).getId())) {
                asset.setChecked(false);
            }
        }
    }

    public Inventory getInventoryByYear(Integer division, int year) {
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(division)
                    && inventory.getEndDate().toLocalDate().getYear() == year) {
                return inventory;
            }
        }
        return null;
    }

    public Inventory getOngoingInventory(Integer division) {
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(division)
                    && inventory.getEndDate().toLocalDate().getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                return inventory;
            }
        }
        return null;
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

    public Integer getDivision(List<String> roles) {
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
        if (division == null) {
            throw new WrongCurrentUserRoleException("Check user roles");
        }
        return division;
    }
}
