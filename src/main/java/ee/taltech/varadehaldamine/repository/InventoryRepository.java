package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Inventory findInventoryById(Long inventoryId);
}
