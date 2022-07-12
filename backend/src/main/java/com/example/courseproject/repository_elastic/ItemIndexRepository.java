package com.example.courseproject.repository_elastic;

import com.example.courseproject.entity.Item;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemIndexRepository extends ElasticsearchRepository<Item, Long> {

    void deleteItemByItemId(Long id);

    @Override
    List<Item> findAllById(Iterable<Long> longs);

    @Query("""
            {
                "multi_match" : {
                  "query":    "?0",
                  "fields": [ "name", "tags.tag", "customString1", "customString2", "customString3", "customMultilineText1", "customMultilineText2", "customMultilineText3" ]
                }
            }
            """)
    List<Item> findAllByKeyword(String keyword);

}
