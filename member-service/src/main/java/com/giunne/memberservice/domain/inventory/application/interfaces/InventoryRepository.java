package com.giunne.memberservice.domain.inventory.application.interfaces;

import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.inventory.domain.Inventory;

import java.util.List;

public interface InventoryRepository {
    Inventory insertInventory(Inventory inventory);
    List<Inventory> findInventoryByAvatar(Avatar avatar);
}
