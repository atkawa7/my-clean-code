package com.example.lostandfound.services;

import com.example.lostandfound.api.CurrentUserService;
import com.example.lostandfound.api.ItemLostService;
import com.example.lostandfound.api.RandomNumberClient;
import com.example.lostandfound.domain.ItemLost;
import com.example.lostandfound.domain.ItemLostRepository;
import com.example.lostandfound.dto.CreateItemLost;
import com.example.lostandfound.dto.SuccessOrFailure;
import com.example.lostandfound.dto.UpdateLostItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("ItemLostService")
public class ItemLostServiceImpl implements ItemLostService {
    private final CurrentUserService currentUserService;
    private final ItemLostRepository itemLostRepository;
    private final RandomNumberClient randomNumberClient;


    public ItemLostServiceImpl(CurrentUserService currentUserService, ItemLostRepository itemLostRepository, RandomNumberClient randomNumberClient) {
        this.currentUserService = currentUserService;
        this.itemLostRepository = itemLostRepository;
        this.randomNumberClient = randomNumberClient;
    }

    @Override
    public SuccessOrFailure createItem(CreateItemLost createItemLost) {

        try{
            var item  = new ItemLost();
            item.setCreateAt(LocalDateTime.now());
            item.setDateLost(createItemLost.getDate());
            item.setProvince(createItemLost.getProvince());
            item.setStatus("created");
            item.setRandomInteger(randomNumberClient.getRandomNumber());
            return new SuccessOrFailure().setResult(itemLostRepository.save(item));
        }
        catch (Exception ex){
            return new SuccessOrFailure().setSuccess(false).setMessage("Failed to create item"+ex.getMessage());
        }

    }

    @Override
    public SuccessOrFailure deleteItem(Long id) {
        var current  = currentUserService.current();
        if("admin".equalsIgnoreCase(current.getRole())){
            var result  = itemLostRepository.findById(id);
            if(result.isPresent()){
                itemLostRepository.deleteById(id);
                return new SuccessOrFailure().setResult(result.get());
            }
            return new SuccessOrFailure().setSuccess(false).setMessage("Item is missing");
        }
        return new SuccessOrFailure().setSuccess(false).setMessage("User doesn't have the permissions to delete");
    }

    @Override
    public Page<ItemLost> listItems(Pageable pageable) {
        return itemLostRepository.findAll(pageable);
    }

    @Override
    public SuccessOrFailure updateItem(Long id, UpdateLostItemStatus updateLostItemStatus) {
        if("found".equalsIgnoreCase(updateLostItemStatus.getStatus()) || "collected".equalsIgnoreCase(updateLostItemStatus.getStatus())){
            var current  = currentUserService.current();
            if("admin".equalsIgnoreCase(current.getRole())){
                var result  = itemLostRepository.findById(id);
                if(result.isPresent()){
                    var item  = result.get();
                    item.setStatus(updateLostItemStatus.getStatus());
                    return new SuccessOrFailure().setResult(result.get());
                }
                return new SuccessOrFailure().setSuccess(false).setMessage("Item is missing");
            }
            return new SuccessOrFailure().setSuccess(false).setMessage("User doesn't have the permissions to delete");
        }

        return new SuccessOrFailure().setSuccess(false).setMessage("Must be collected or found");
    }
}
