package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.AssetIsNotChecked;
import ee.taltech.varadehaldamine.exception.InventoryExcelException;
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

    /**
     * Method to start new inventory.
     *
     * @param roles user roles
     * @return started inventory
     */
    public Inventory createInventory(List<String> roles) {
        Integer userDivision = getDivision(roles);
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(userDivision) && inventory.getEndDate() == null) {
                throw new OngoingInventoryAlreadyExists("Could not start inventory because it has already been started.");
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

    /**
     * Method to end existing inventory.
     *
     * @param roles user roles
     * @return ended inventory
     */
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
            throw new OngoingInventoryDoesNotExist("Could not end inventory because there are no inventory in progress.");
        }
        if (userDivision.equals(dbInventory.getDivision())) {
            Set<String> allCurrentAssets = getAssetsSetByPossessor(dbInventory.getDivision());
            Set<String> allInventoryAssets = dbInventory.getAssets();
            for (String assetId : allCurrentAssets) {
                allInventoryAssets.add(assetId);
                if (!assetRepository.findAssetById(assetId).getChecked()) {
                    throw new AssetIsNotChecked("Could not end inventory because not all assets were checked yet.");
                }
            }
            dbInventory.setAssets(allInventoryAssets);
            dbInventory.setEndDate(new Date(System.currentTimeMillis()));
            return inventoryRepository.save(dbInventory);
        }
        return null;
    }

    /**
     * Method to get the last inventory of the division.
     *
     * @param division division nr
     * @return asset id set
     */
    public Set<String> getAssetsSetByPossessor(Integer division) {
        List<Asset> allAssets = assetRepository.findAll();
        Set<String> inventoryAssets = new HashSet<>();
        for (Asset asset : allAssets) {
            if (possessorService.getPossessorById(asset.getPossessorId()).getId()
                    .equals(possessorService.findPossessor(division, null).getId())) {
                inventoryAssets.add(asset.getId());
            }
        }
        return inventoryAssets;
    }

    /**
     * Method to make assets unchecked.
     *
     * @param division division nr
     */
    public void setAssetsUnchecked(Integer division) {
        List<Asset> allAssets = assetRepository.findAll();
        for (Asset asset : allAssets) {
            if (possessorService.getPossessorById(asset.getPossessorId()).getId()
                    .equals(possessorService.findPossessor(division, null).getId())) {
                asset.setChecked(false);
            }
        }
    }

    /**
     * Method to get inventory by year.
     *
     * @param division division nr
     * @param year     inventory year
     * @return inventory of particular year
     */
    public Inventory getInventoryByYear(Integer division, int year) {
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(division) && inventory.getEndDate() == null) {
                throw new InventoryExcelException("Inventory still ongoing");
            }
            if (inventory.getDivision().equals(division)
                    && inventory.getEndDate().toLocalDate().getYear() == year) {
                return inventory;
            }
        }
        return null;
    }

    /**
     * Method to check if there is ongoing inventory.
     *
     * @param division division nr
     * @return inventory is on or not
     */
    public Boolean getInventoryOngoing(Integer division) {
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(division) && inventory.getEndDate() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get ongoing inventory by division.
     *
     * @param division division nr
     * @return ongoing inventory
     */
    public Inventory getOngoingInventory(Integer division) {
        for (Inventory inventory : inventoryRepository.findAll()) {
            if (inventory.getDivision().equals(division) && inventory.getEndDate() == null) {
                throw new InventoryExcelException("Inventory still ongoing");
            }
            if (inventory.getDivision().equals(division)
                    && inventory.getEndDate().toLocalDate().getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                return inventory;
            }
        }
        return null;
    }

    /**
     * Method to get division from given roles.
     *
     * @param roles list of user roles
     * @return division nr
     */
    public Integer getDivision(List<String> roles) {
        Integer division = null;
        for (String role : roles) {
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
