package com.example.courseproject.controller;

import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.model.ItemCollectionListResponse;
import com.example.courseproject.model.CollectionCreateRequest;
import com.example.courseproject.service.ItemCollectionServiceImpl;
import com.example.courseproject.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ItemCollectionController {

    private final ItemCollectionServiceImpl collectionService;
    private final UserServiceImpl userService;

    public ItemCollectionController(ItemCollectionServiceImpl collectionService, UserServiceImpl userService) {
        this.collectionService = collectionService;
        this.userService = userService;
    }

    @RequestMapping(value = "/collection/new", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<?> createNewCollection(
                        @RequestPart("properties") CollectionCreateRequest collectionCreateRequest,
                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ItemCollection itemCollection = collectionService.createNewCollection(collectionCreateRequest, file);
        return new ResponseEntity<>(itemCollection, HttpStatus.OK);
    }

    @RequestMapping(value = "/collection/update", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<?> updateCollection(
                        @RequestPart("properties") ItemCollection collection,
                        @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        ItemCollection updatedCollection = collectionService.updateCollection(collection, file);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("/collection/remove/{id}")
    public ResponseEntity<?> removeCollection(@PathVariable Long id) {
        collectionService.removeCollection(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/collection/user={username}")
    public ResponseEntity<?> getAllByUsername(@PathVariable String username) {
        if (!userService.findByUsername(username).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ItemCollectionListResponse collections = new ItemCollectionListResponse();
        collections.setCollections(collectionService.getAllCollectionsByUsername(username));
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

    @GetMapping("/collection/byItemId={itemId}")
    public ResponseEntity<?> getCollectionDetailsByItemId(@PathVariable Long itemId) {
        Optional<ItemCollection> collection = collectionService.getCollectionDetailsByItemId(itemId);
        if (collection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(collection.get(), HttpStatus.OK);
    }

    @GetMapping("/collection/top/{num}")
    public ResponseEntity<?> getLargestCollections(@PathVariable Long num) {
        ItemCollectionListResponse response = new ItemCollectionListResponse();
        response.setCollections(collectionService.getLargestCollections(num));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
