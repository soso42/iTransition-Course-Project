package com.example.courseproject.service;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.entity.User;
import com.example.courseproject.repository.CommentRepository;
import com.example.courseproject.repository.ItemRepository;
import com.example.courseproject.repository.TagRepository;
import com.example.courseproject.repository_elastic.CollectionIndexRepository;
import com.example.courseproject.repository_elastic.CommentIndexRepository;
import com.example.courseproject.repository_elastic.ItemIndexRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IndexServiceTest {

    @Autowired
    private CommentIndexRepository commentIndexRepository;

    @Autowired
    private ItemIndexRepository itemIndexRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private IndexService indexService;

    @Autowired
    private CollectionIndexRepository collectionIndexRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ItemCollectionServiceImpl collectionService;


    @Test
    void findAllCollections() {
//        collectionIndexRepository.findAllByKeyword("guns").forEach(System.out::println);
//        itemRepository.findAllByOrderByIdDesc().forEach(System.out::println);
//        itemRepository.getLatestItems(2L).forEach(System.out::println);
//        tagRepository.findMostPopularTags(2L).forEach(tag -> System.out.println(tag.getTag()));
        collectionService.getLargestCollections(2L).forEach(col -> System.out.println(col.getName()));
    }


    @Transactional
    @Test
    void edititem() {
//        Item item = itemRepository.findById(14L).get();
        Item item = itemIndexRepository.findById(17L).get();

        item.setName("Mein Kampf");

        item.setCustomString1("Soso");
//
//        item.setLikes(null);
//        item.setComments(null);

//        itemRepository.save(item);

        indexService.saveItem(item);
    }


    @Test
    void deleteAll() {
        commentIndexRepository.deleteAll();
        itemIndexRepository.deleteAll();
        collectionIndexRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Comment> comments = commentIndexRepository.findByAllItemsUsingCustomQuery("sun");
        List<Long> ids = comments.stream()
                .map(Comment::getCommentId)
                .map(id -> commentRepository.findById(id).get())
                .map(comment -> comment.getItem().getItemId())
                .collect(Collectors.toList());
        List<Item> res = itemRepository.findAllById(ids);
    }

}
