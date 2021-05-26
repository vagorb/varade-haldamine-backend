package ee.taltech.varadehaldamine.service;

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

    public Inventory createInventory() {
        System.out.println("not oke");
        Inventory newInventory = new Inventory();
        newInventory.setStartDate(new Date(System.currentTimeMillis()));
        List<Asset> allCurrentAssets = assetRepository.findAll();
        Set<String> inventoryAssets = new HashSet<>();
        for (Asset asset : allCurrentAssets) {
            inventoryAssets.add(asset.getId());
        }
        newInventory.setAssets(inventoryAssets);
        System.out.println("oke");
        return inventoryRepository.save(newInventory);
    }

    public Inventory endInventory(Long inventoryId) {
        Inventory dbInventory = inventoryRepository.findInventoryById(inventoryId);
        if (dbInventory != null) {
            List<Asset> allCurrentAssets = assetRepository.findAll();
            Set<String> allInventoryAssets = dbInventory.getAssets();
            for (Asset asset : allCurrentAssets) {
                allInventoryAssets.add(asset.getId());
                if (!asset.getChecked()) {
                    System.out.println("nou");
                    return null;
                }
            }
            dbInventory.setAssets(allInventoryAssets);
            dbInventory.setEndDate(new Date(System.currentTimeMillis()));
            return inventoryRepository.save(dbInventory);
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
}
