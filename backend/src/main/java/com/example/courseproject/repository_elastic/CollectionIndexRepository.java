package com.example.courseproject.repository_elastic;

import com.example.courseproject.entity.ItemCollection;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionIndexRepository extends ElasticsearchRepository<ItemCollection, Long> {

    @Query("""
            {
                "multi_match" : {
                  "query":    "?0",
                  "fields": [ "name", "description", "topic", "customString1", "customString2", "customString3",
                  "customInteger1", "customInteger2", "customInteger3", "customBoolean1", "customBoolean2", "customBoolean3",
                  "customDate1", "customDate2", "customDate3",
                  "customMultilineText1", "customMultilineText2", "customMultilineText3" ]
                }
            }
            """)
    List<ItemCollection> findAllByKeyword(String keyword);
}
