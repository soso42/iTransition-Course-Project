package com.example.courseproject.service;

import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.entity.User;
import com.example.courseproject.model.CollectionCreateRequest;
import com.example.courseproject.repository.ItemCollectionRepository;
import com.example.courseproject.repository_elastic.CollectionIndexRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCollectionServiceImpl implements ItemCollectionService {

    private final ItemCollectionRepository itemCollectionRepository;
    private final UserServiceImpl userService;
    private final CollectionIndexRepository collectionIndexRepository;

    public ItemCollectionServiceImpl(ItemCollectionRepository itemCollectionRepository,
                                     UserServiceImpl userService,
                                     CollectionIndexRepository collectionIndexRepository) {
        this.itemCollectionRepository = itemCollectionRepository;
        this.userService = userService;
        this.collectionIndexRepository = collectionIndexRepository;
    }

    @Override
    public ItemCollection createNewCollection(CollectionCreateRequest request, MultipartFile file) throws IOException {
        ItemCollection collection = new ItemCollection();
        collection.setName(request.getName());
        collection.setDescription(request.getDescription());
        collection.setTopic(request.getTopic());
        collection.setAuthor(userService.findByUsername(request.getUsername()).get());

        collection.setImage(file.getBytes());

        collection.setCustomString1(request.getCustomString1());
        collection.setCustomString2(request.getCustomString2());
        collection.setCustomString3(request.getCustomString3());

        collection.setCustomInteger1(request.getCustomInteger1());
        collection.setCustomInteger2(request.getCustomInteger2());
        collection.setCustomInteger3(request.getCustomInteger3());

        collection.setCustomBoolean1(request.getCustomBoolean1());
        collection.setCustomBoolean2(request.getCustomBoolean2());
        collection.setCustomBoolean3(request.getCustomBoolean3());

        collection.setCustomMultilineText1(request.getCustomMultilineText1());
        collection.setCustomMultilineText2(request.getCustomMultilineText2());
        collection.setCustomMultilineText3(request.getCustomMultilineText3());

        collection.setCustomDate1(request.getCustomDate1());
        collection.setCustomDate2(request.getCustomDate2());
        collection.setCustomDate3(request.getCustomDate3());

        collection = itemCollectionRepository.save(collection);
        collectionIndexRepository.save(collection);
        return collection;
    }

    @Override
    public ItemCollection updateCollection(ItemCollection request, MultipartFile file) throws IOException {
        ItemCollection dbCollection = itemCollectionRepository.findById(request.getItemCollectionId()).get();
        dbCollection = updateFields(dbCollection, request);
        dbCollection.setImage(file == null ? null : file.getBytes());
        dbCollection = itemCollectionRepository.save(dbCollection);

        ItemCollection indexedCollection = collectionIndexRepository.findById(dbCollection.getItemCollectionId()).get();
        indexedCollection = updateFields(indexedCollection, request);
        collectionIndexRepository.save(indexedCollection);

        return dbCollection;
    }

    private ItemCollection updateFields(ItemCollection current, ItemCollection request) {
        current.setName(request.getName());
        current.setDescription(request.getDescription());
        current.setTopic(request.getTopic());
        current.setCustomString1(request.getCustomString1());
        current.setCustomString2(request.getCustomString2());
        current.setCustomString3(request.getCustomString3());
        current.setCustomInteger1(request.getCustomInteger1());
        current.setCustomInteger2(request.getCustomInteger2());
        current.setCustomInteger3(request.getCustomInteger3());
        current.setCustomBoolean1(request.getCustomBoolean1());
        current.setCustomBoolean2(request.getCustomBoolean2());
        current.setCustomBoolean3(request.getCustomBoolean3());
        current.setCustomMultilineText1(request.getCustomMultilineText1());
        current.setCustomMultilineText2(request.getCustomMultilineText2());
        current.setCustomMultilineText3(request.getCustomMultilineText3());
        current.setCustomDate1(request.getCustomDate1());
        current.setCustomDate2(request.getCustomDate2());
        current.setCustomDate3(request.getCustomDate3());
        return current;
    }

    @Override
    public void removeCollection(Long id) {
        itemCollectionRepository.deleteById(id);
        collectionIndexRepository.deleteById(id);
    }

    @Override
    public List<ItemCollection> getAllCollectionsByUsername(String username) {
        User author = userService.findByUsername(username).get();
        return itemCollectionRepository.findAllByAuthor(author);
    }

    @Override
    public Optional<ItemCollection> getCollectionDetailsByItemId(Long itemId) {
        return itemCollectionRepository.findCollectionByItemId(itemId);
    }

    @Override
    public List<ItemCollection> getLargestCollections(Long num) {
        return itemCollectionRepository.findLargestCollections(num);
    }

}
