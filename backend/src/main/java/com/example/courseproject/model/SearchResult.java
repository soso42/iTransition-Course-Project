package com.example.courseproject.model;

import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

    private List<Item> items;
    private List<ItemCollection> collections;

}
