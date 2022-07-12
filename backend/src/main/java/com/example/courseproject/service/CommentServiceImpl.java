package com.example.courseproject.service;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.User;
import com.example.courseproject.model.CommentCreateRequest;
import com.example.courseproject.repository.CommentRepository;
import com.example.courseproject.repository.ItemRepository;
import com.example.courseproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final IndexServiceImpl indexService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              ItemRepository itemRepository,
                              IndexServiceImpl indexService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.indexService = indexService;
    }

    @Override
    public Comment saveComment(CommentCreateRequest request) {
        User user = userRepository.findByUsername(request.getAuthorUsername()).get();
        Item item = itemRepository.getById(request.getItemId());
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setDateTime(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setItem(item);
        comment = commentRepository.save(comment);
        indexService.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsByItemId(Long id) {
        Item item = itemRepository.getById(id);
        return commentRepository.findAllByItem(item);
    }

}
