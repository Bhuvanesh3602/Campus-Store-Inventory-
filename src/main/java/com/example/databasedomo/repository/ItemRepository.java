package com.example.databasedomo.repository;

import com.example.databasedomo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Find items by category
    List<Item> findByCategory(String category);

    // Find items with stock less than a given quantity
    List<Item> findByStockQuantityLessThan(int quantity);
}
