package com.example.courseproject.model;

import com.example.courseproject.entity.ItemCollection;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCollectionListResponse {

    private List<ItemCollection> collections;

}
