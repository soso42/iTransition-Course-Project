package com.example.courseproject.service;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.repository.CommentRepository;
import com.example.courseproject.repository.ItemRepository;
import com.example.courseproject.repository_elastic.CollectionIndexRepository;
import com.example.courseproject.repository_elastic.CommentIndexRepository;
import com.example.courseproject.repository_elastic.ItemIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SearchServiceImpl implements SearchService {

    private final ItemIndexRepository itemIndexRepository;
    private final CommentIndexRepository commentIndexRepository;
    private final CollectionIndexRepository collectionIndexRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public SearchServiceImpl(ItemIndexRepository itemIndexRepository,
                             CommentIndexRepository commentIndexRepository,
                             CommentRepository commentRepository,
                             ItemRepository itemRepository,
                             CollectionIndexRepository collectionIndexRepository) {
        this.itemIndexRepository = itemIndexRepository;
        this.commentIndexRepository = commentIndexRepository;
        this.commentRepository = commentRepository;
        this.itemRepository = itemRepository;
        this.collectionIndexRepository = collectionIndexRepository;
    }

    @Override
    public List<Item> searchItems(String keyword) {
        List<Long> resultFromItems = itemIndexRepository.findAllByKeyword(keyword).stream()
                .map(Item::getItemId)
                .collect(Collectors.toList());
        List<Long> resultFromComments = searchInComments(keyword);
        List<Long> listOfIds = Stream.concat(resultFromItems.stream(), resultFromComments.stream())
                .distinct()
                .collect(Collectors.toList());
        return itemIndexRepository.findAllById(listOfIds);
    }

    private List<Long> searchInComments(String keyword) {
        List<Comment> comments = commentIndexRepository.findByAllItemsUsingCustomQuery(keyword);
        return comments.stream()
                .map(Comment::getCommentId)
                .map(id -> commentRepository.findById(id).get())
                .map(comment -> comment.getItem().getItemId())
                .distinct()
                .collect(Collectors.toList());
//        return itemIndexRepository.findAllById(ids);
    }

    @Override
    public List<ItemCollection> searchCollections(String keyword) {
        return collectionIndexRepository.findAllByKeyword(keyword);
    }

}
