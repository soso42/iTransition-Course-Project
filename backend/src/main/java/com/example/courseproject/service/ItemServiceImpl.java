package com.example.courseproject.service;

import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.entity.User;
import com.example.courseproject.model.ItemCreateRequest;
import com.example.courseproject.model.ItemUpdateRequest;
import com.example.courseproject.repository.ItemCollectionRepository;
import com.example.courseproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemCollectionRepository collectionRepository;
    private final TagServiceImpl tagService;
    private final UserServiceImpl userService;
    private final IndexServiceImpl indexingService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemCollectionRepository collectionRepository,
                           TagServiceImpl tagService,
                           UserServiceImpl userService,
                           IndexServiceImpl indexingService) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.indexingService = indexingService;
    }

    @Override
    public Item createItem(ItemCreateRequest request) {
        Item item = new Item();
        User owner = userService.findByUsername(request.getOwner()).get();
        ItemCollection parent = collectionRepository.getById(request.getCollectionId());
        item.setName(request.getName());
        item.setTags(tagService.getListOfTag(request.getTags()));
        item.setItemCollection(parent);
        item.setOwner(owner);

        item.setCustomString1(request.getCustomString1());
        item.setCustomString2(request.getCustomString2());
        item.setCustomString3(request.getCustomString3());
        item.setCustomInteger1(request.getCustomInteger1());
        item.setCustomInteger2(request.getCustomInteger2());
        item.setCustomInteger3(request.getCustomInteger3());
        item.setCustomBoolean1(request.getCustomBoolean1());
        item.setCustomBoolean2(request.getCustomBoolean2());
        item.setCustomBoolean3(request.getCustomBoolean3());
        item.setCustomMultilineText1(request.getCustomMultilineText1());
        item.setCustomMultilineText2(request.getCustomMultilineText2());
        item.setCustomMultilineText3(request.getCustomMultilineText3());
        item.setCustomDate1(request.getCustomDate1());
        item.setCustomDate2(request.getCustomDate2());
        item.setCustomDate3(request.getCustomDate3());

        item = itemRepository.save(item);
        indexingService.saveItem(item);
        return item;
    }

    @Override
    public Item updateItem(ItemUpdateRequest request) {
        Item dbItem = itemRepository.findById(request.getItemId()).get();
        dbItem = updateFields(dbItem, request);
        dbItem = itemRepository.save(dbItem);

        Item indexedItem = indexingService.findItemById(request.getItemId());
        indexedItem = updateFields(indexedItem, request);
        indexingService.saveItem(indexedItem);

        return dbItem;
    }

    private Item updateFields(Item item, ItemUpdateRequest request) {
        item.setName(request.getName());
        item.setTags(tagService.getListOfTag(request.getTags()));
        item.setCustomString1(request.getCustomString1());
        item.setCustomString2(request.getCustomString2());
        item.setCustomString3(request.getCustomString3());
        item.setCustomInteger1(request.getCustomInteger1());
        item.setCustomInteger2(request.getCustomInteger2());
        item.setCustomInteger3(request.getCustomInteger3());
        item.setCustomBoolean1(request.getCustomBoolean1());
        item.setCustomBoolean2(request.getCustomBoolean2());
        item.setCustomBoolean3(request.getCustomBoolean3());
        item.setCustomMultilineText1(request.getCustomMultilineText1());
        item.setCustomMultilineText2(request.getCustomMultilineText2());
        item.setCustomMultilineText3(request.getCustomMultilineText3());
        item.setCustomDate1(request.getCustomDate1());
        item.setCustomDate2(request.getCustomDate2());
        item.setCustomDate3(request.getCustomDate3());
        return item;
    }

    @Override
    public List<Item> getAllItemsByCollectionId(Long id) {
        ItemCollection collection = collectionRepository.getById(id);
        return itemRepository.findAllByItemCollection(collection);
    }

    @Override
    public List<Item> getLatestItems(Long num) {
        return itemRepository.getLatestItems(num).stream()
                .peek(item -> {
                    ItemCollection collection = collectionRepository.findCollectionByItemId(item.getItemId()).get();
                    item.setParentCollection(collection);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteByItemId(id);
        indexingService.deleteItemById(id);
    }

    @Override
    public void addLikeToItemById(Long id, String username) {
        Item item = itemRepository.findById(id).get();
        User user = userService.findByUsername(username).get();
        Set<User> likes = item.getLikes();
        likes.add(user);
        item.setLikes(likes);
        itemRepository.save(item);
    }

    @Override
    public void removeLikeToItemById(Long id, String username) {
        Item item = itemRepository.findById(id).get();
        User user = userService.findByUsername(username).get();
        Set<User> likes = item.getLikes();
        likes.remove(user);
        item.setLikes(likes);
        itemRepository.save(item);
    }

}
