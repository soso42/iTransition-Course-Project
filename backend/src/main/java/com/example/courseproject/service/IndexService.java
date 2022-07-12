package com.example.courseproject.service;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;

public interface IndexService {
    Item saveItem(Item item);
    Item findItemById(Long id);
    void deleteItemById(Long id);
    Comment save(Comment comment);
    ItemCollection findCollectionById(Long id);
    ItemCollection saveCollection(ItemCollection collection);
}
