package com.example.courseproject.repository_elastic;

import com.example.courseproject.entity.Comment;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentIndexRepository extends ElasticsearchRepository<Comment, Long> {

    @Query("""
            {
                "match": {
                    "text": "?0"
                }
            }
            """)
    List<Comment> findByAllItemsUsingCustomQuery(String term);

    List<Comment> findAll();
}
