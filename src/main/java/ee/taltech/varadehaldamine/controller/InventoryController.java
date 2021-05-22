package ee.taltech.varadehaldamine.controller;

import ee.taltech.varadehaldamine.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("inventory")
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

   //@PreAuthorize("hasRole('ROLE_Esimees')")
    @PostMapping
    public ResponseEntity<Object> createInventory() {
        System.out.println("wat");
        if (inventoryService.createInventory() != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    //@PreAuthorize("hasRole('ROLE_Esimees')")
    @PutMapping("/{inventoryId}")
    public ResponseEntity<Object> endInventory(@PathVariable Long inventoryId) {
        if (inventoryService.endInventory(inventoryId) != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
