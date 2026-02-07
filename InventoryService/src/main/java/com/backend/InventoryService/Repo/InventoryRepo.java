package com.backend.InventoryService.Repo;

import com.backend.InventoryService.Model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryItem, UUID> {

}
