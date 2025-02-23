package com.giunne.memberservice.domain.inventory.application.interfaces;

import com.giunne.memberservice.domain.inventory.domain.Inventory;

public interface InventoryRepository {
    Inventory insertInventory(Inventory inventory);
}
