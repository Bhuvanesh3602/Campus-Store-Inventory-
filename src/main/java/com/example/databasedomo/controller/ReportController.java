package com.example.databasedomo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.databasedomo.model.Item;
import com.example.databasedomo.repository.ItemRepository;
import com.example.databasedomo.repository.SaleRepository;
import java.util.List;
// import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SaleRepository saleRepository;

    // ✅ GET: Low-stock report (stock < 10)
    // @GetMapping("/low-stock")
    // public List<Item> getLowStockItems() {
    // List<Item> items = itemRepository.findAll();
    // return items.stream()
    // .filter(i -> i.getStockQuantity() < 10)
    // .collect(Collectors.toList());
    // }
    @GetMapping("/low-stock")
    public List<Item> getLowStockItems(@RequestParam(required = false, defaultValue = "10") int minQuantity) {
        // Fetch items with stock less than minQuantity
        return itemRepository.findByStockQuantityLessThan(minQuantity);
    }

    // ✅ GET: Total sales report
    @GetMapping("/totalsales")
    public ResponseEntity<String> getTotalSalesReport() {
        Double totalSales = saleRepository.findTotalSalesAmount();
        return ResponseEntity.ok("💰 Total Sales Amount: " + (totalSales != null ? totalSales : 0.0));
    }
}
