package com.example.lostandfound.api;

import com.example.lostandfound.domain.ItemLost;
import com.example.lostandfound.dto.CreateItemLost;
import com.example.lostandfound.dto.SuccessOrFailure;
import com.example.lostandfound.dto.UpdateLostItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemLostService {
    SuccessOrFailure createItem(CreateItemLost createItemLost);
    SuccessOrFailure deleteItem(Long id);
    SuccessOrFailure updateItem(Long id, UpdateLostItemStatus status);
    Page<ItemLost> listItems(Pageable pageable);
}
