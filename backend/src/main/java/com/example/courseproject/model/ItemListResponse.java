package com.example.courseproject.model;

import com.example.courseproject.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemListResponse {

    private List<Item> items;

}
