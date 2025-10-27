package com.example.databasedomo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.databasedomo.model.*;
import com.example.databasedomo.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ POST: Record a sale
    @PostMapping("/add")
    public ResponseEntity<String> recordSale(@RequestBody Sale saleRequest) {
        Long itemId = saleRequest.getItem().getItemId();
        Long userId = saleRequest.getUser().getUserId();

        Optional<Item> itemOpt = itemRepository.findById(itemId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (itemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Invalid Item ID");
        }
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Invalid User ID");
        }

        Item item = itemOpt.get();

        if (item.getStockQuantity() < saleRequest.getQuantity()) {
            return ResponseEntity.badRequest().body("❌ Not enough stock available");
        }

        // Calculate total price
        BigDecimal total = item.getPrice().multiply(BigDecimal.valueOf(saleRequest.getQuantity()));

        // Update stock
        item.setStockQuantity(item.getStockQuantity() - saleRequest.getQuantity());
        itemRepository.save(item);

        // Record sale
        Sale sale = new Sale(item, userOpt.get(), saleRequest.getQuantity(), total, LocalDateTime.now());
        saleRepository.save(sale);

        return ResponseEntity.ok("✅ Sale recorded successfully!");
    }

    // ✅ GET: All sales by a user
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getSalesByUser(@PathVariable Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ User not found");
        }
        List<Sale> sales = saleRepository.findByUser(userOpt.get());
        return ResponseEntity.ok(sales);
    }

    // ✅ GET: All sales for an item
    @GetMapping("/item/{id}")
    public ResponseEntity<?> getSalesByItem(@PathVariable Long id) {
        Optional<Item> itemOpt = itemRepository.findById(id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Item not found");
        }
        List<Sale> sales = saleRepository.findByItem(itemOpt.get());
        return ResponseEntity.ok(sales);
    }
}
