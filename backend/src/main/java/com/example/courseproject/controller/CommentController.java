package com.example.courseproject.controller;

import com.example.courseproject.entity.Comment;
import com.example.courseproject.model.CommentCreateRequest;
import com.example.courseproject.model.CommentListResponse;
import com.example.courseproject.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentServiceImpl commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("comment/new")
    public ResponseEntity<?> addComment(@RequestBody CommentCreateRequest request) {
        Comment comment = commentService.saveComment(request);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("comment/all/item-id={id}")
    public ResponseEntity<?> getAllCommentsByItemId(@PathVariable Long id) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentService.getAllCommentsByItemId(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
