package com.example.courseproject.service;

import com.example.courseproject.entity.Tag;
import com.example.courseproject.model.TagList;
import com.example.courseproject.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagList getAllTags() {
        TagList tagList = new TagList();
        List<String> list = tagRepository.findAll().stream()
                .map(Tag::getTag)
                .collect(Collectors.toList());
        tagList.setTags(list);
        return tagList;
    }

    @Override
    public List<Tag> getListOfTag(List<String> stringTagList) {
        return stringTagList.stream()
                .distinct()
                .map(this::checkAndSaveTagIfNotExists)
                .collect(Collectors.toList());
    }

    private Tag checkAndSaveTagIfNotExists(String str) {
        Optional<Tag> optionalTag = tagRepository.findByTag(str);
        if (!optionalTag.isPresent()) {
            Tag tag = new Tag();
            tag.setTag(str);
            return tagRepository.save(tag);
        }
        return optionalTag.get();
    }

    @Override
    public List<String> getMostPopularTags(Long num) {
        List<Tag> tags = tagRepository.findMostPopularTags(num);
        return tags.stream()
                .map(Tag::getTag)
                .collect(Collectors.toList());
    }

}
