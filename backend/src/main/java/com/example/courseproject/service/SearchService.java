package com.example.courseproject.service;

import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;

import java.util.List;

public interface SearchService {
    List<Item> searchItems(String keyword);
    List<ItemCollection> searchCollections(String keyword);
}
