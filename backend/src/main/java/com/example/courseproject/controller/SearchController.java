package com.example.courseproject.controller;

import com.example.courseproject.model.SearchResult;
import com.example.courseproject.service.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchServiceImpl searchService;

    @Autowired
    public SearchController(SearchServiceImpl searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/term={term}")
    public ResponseEntity<?> search(@PathVariable String term) {
        SearchResult result = new SearchResult();
        result.setItems(searchService.searchItems(term));
        result.setCollections(searchService.searchCollections(term));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
