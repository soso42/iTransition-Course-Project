package com.example.courseproject.controller;

import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.User;
import com.example.courseproject.model.ItemCreateRequest;
import com.example.courseproject.model.ItemListResponse;
import com.example.courseproject.model.ItemUpdateRequest;
import com.example.courseproject.model.LikesResponse;
import com.example.courseproject.service.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemServiceImpl itemService;

    @Autowired
    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/item/new")
    public ResponseEntity<Item> createNewItem(@RequestBody ItemCreateRequest itemCreateRequest) {
        Item item = itemService.createItem(itemCreateRequest);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/item/collection-id={id}")
    public ResponseEntity<?> getAllItemsByCollectionId(@PathVariable Long id) {
        ItemListResponse response = new ItemListResponse();
        response.setItems(itemService.getAllItemsByCollectionId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/item/latest/{num}")
    public ResponseEntity<?> getLatestItems(@PathVariable Long num) {
        ItemListResponse response = new ItemListResponse();
        response.setItems(itemService.getLatestItems(num));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/item/id={id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<Item> optItem = itemService.findById(id);
        if (!optItem.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optItem.get(), HttpStatus.OK);
    }

    @PutMapping("/item/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemUpdateRequest request) {
        Item item = itemService.updateItem(request);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/item/delete/id={id}")
    public ResponseEntity<?> deleteItemById(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/item/addlike/item-id={id}&username={username}")
    public ResponseEntity<?> addLikeToItemById(@PathVariable Long id, @PathVariable String username) {
        itemService.addLikeToItemById(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/item/removelike/item-id={id}&username={username}")
    public ResponseEntity<?> removeLikeToItemById(@PathVariable Long id, @PathVariable String username) {
        itemService.removeLikeToItemById(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/item/likes/item-id={id}")
    public ResponseEntity<?> getLikes(@PathVariable Long id) {
        LikesResponse response = new LikesResponse();
        Set<User> likes = itemService.findById(id).get().getLikes();
        response.setLikes(likes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
