package com.example.databasedomo.repository;

import com.example.databasedomo.model.Sale;
import com.example.databasedomo.model.User;
import com.example.databasedomo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUser(User user);

    List<Sale> findByItem(Item item);

    @Query("SELECT SUM(s.totalPrice) FROM Sale s")
    Double findTotalSalesAmount();
}
