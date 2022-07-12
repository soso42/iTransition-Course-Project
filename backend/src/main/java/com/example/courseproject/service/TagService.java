package com.example.courseproject.service;

import com.example.courseproject.entity.Tag;
import com.example.courseproject.model.TagList;

import java.util.List;

public interface TagService {
    TagList getAllTags();
    List<Tag> getListOfTag(List<String> stringTagList);
    List<String> getMostPopularTags(Long num);
}
