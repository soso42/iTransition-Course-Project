package com.example.courseproject.repository;

import com.example.courseproject.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTag(String stringTag);

    @Query(value = """
                      SELECT t.tag_id, t.tag,
                             COUNT(t.tag_id) AS `value_occurrence`
                      FROM
                          item_tag_mapping AS itm
                          INNER JOIN tag AS t
                          ON itm.tag_id = t.tag_id
                      GROUP BY
                          tag_id
                      ORDER BY `value_occurrence` DESC
                      LIMIT :num ;""", nativeQuery = true)
    List<Tag> findMostPopularTags(@Param("num") Long num);
}
