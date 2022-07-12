package com.example.courseproject.service;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.repository_elastic.CollectionIndexRepository;
import com.example.courseproject.repository_elastic.CommentIndexRepository;
import com.example.courseproject.repository_elastic.ItemIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    private final ItemIndexRepository itemIndexRepository;
    private final CommentIndexRepository commentIndexRepository;
    private final CollectionIndexRepository collectionIndexRepository;

    @Autowired
    public IndexServiceImpl(ItemIndexRepository itemIndexRepository,
                            CommentIndexRepository commentIndexRepository,
                            CollectionIndexRepository collectionIndexRepository) {
        this.itemIndexRepository = itemIndexRepository;
        this.commentIndexRepository = commentIndexRepository;
        this.collectionIndexRepository = collectionIndexRepository;
    }

    @Override
    public Item saveItem(Item item) {
        return itemIndexRepository.save(item);
    }

    @Override
    public Item findItemById(Long id) {
        return itemIndexRepository.findById(id).get();
    }

    @Override
    public void deleteItemById(Long id) {
        itemIndexRepository.deleteItemByItemId(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentIndexRepository.save(comment);
    }

    @Override
    public ItemCollection findCollectionById(Long id) {
        return collectionIndexRepository.findById(id).get();
    }

    @Override
    public ItemCollection saveCollection(ItemCollection collection) {
        return collectionIndexRepository.save(collection);
    }

}
