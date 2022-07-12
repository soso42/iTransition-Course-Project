package com.example.courseproject.service;

import com.example.courseproject.entity.Item;
import com.example.courseproject.model.ItemCreateRequest;
import com.example.courseproject.model.ItemUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item createItem(ItemCreateRequest itemCreateRequest);
    Item updateItem(ItemUpdateRequest request);
    List<Item> getAllItemsByCollectionId(Long id);
    List<Item> getLatestItems(Long num);
    Optional<Item> findById(Long id);
    void deleteItem(Long id);
    void addLikeToItemById(Long id, String username);
    void removeLikeToItemById(Long id, String username);
}
