package com.backend.InventoryService.Model.Mapper;

import com.backend.InventoryService.Model.Dto.InventoryResponse;
import com.backend.InventoryService.Model.InventoryItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryResponse inventoryItemToInventoryResponse(InventoryItem inventoryItem);
}
