package com.example.databasedomo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.databasedomo.model.Item;
import com.example.databasedomo.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // ✅ POST: Add new item
    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        itemRepository.save(item);
        return ResponseEntity.ok("✅ Item added successfully!");
    }

    // ✅ GET: View all items
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ✅ PUT: Update item details by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            item.setName(updatedItem.getName());
            item.setCategory(updatedItem.getCategory());
            item.setPrice(updatedItem.getPrice());
            item.setStockQuantity(updatedItem.getStockQuantity());
            item.setAddedAt(updatedItem.getAddedAt());

            itemRepository.save(item);
            return ResponseEntity.ok("✅ Item updated successfully!");
        } else {
            return ResponseEntity.status(404).body("❌ Item not found with ID: " + id);
        }
    }

    // ✅ DELETE: Delete item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return ResponseEntity.ok("🗑️ Item deleted successfully (ID: " + id + ")");
        } else {
            return ResponseEntity.status(404).body("❌ Item not found with ID: " + id);
        }
    }
}
