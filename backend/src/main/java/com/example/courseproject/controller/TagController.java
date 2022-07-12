package com.example.courseproject.controller;

import com.example.courseproject.model.TagList;
import com.example.courseproject.service.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TagController {

    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping("tags/all")
    public ResponseEntity<?> getAllTags() {
        TagList tagList = tagService.getAllTags();
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @GetMapping("tags/popular/{num}")
    public ResponseEntity<?> getMostPopularTags(@PathVariable Long num) {
        TagList tagList = new TagList();
        tagList.setTags(tagService.getMostPopularTags(num));
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

}
