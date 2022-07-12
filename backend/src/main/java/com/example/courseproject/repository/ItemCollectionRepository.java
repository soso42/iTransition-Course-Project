package com.example.courseproject.repository;

import com.example.courseproject.entity.ItemCollection;
import com.example.courseproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCollectionRepository extends JpaRepository<ItemCollection, Long> {
    List<ItemCollection> findAllByAuthor(User author);

    @Query(value = """
                        SELECT c.*,
                               COUNT(i.item_collection_collection_id) AS `value_occurrence`
                        FROM
                            item AS i
                            INNER JOIN item_collection AS c
                            ON i.item_collection_collection_id = c.collection_id
                        GROUP BY
                            item_collection_collection_id
                        ORDER BY `value_occurrence` DESC
                        LIMIT :num ;
                    """, nativeQuery = true)
    List<ItemCollection> findLargestCollections(@Param("num") Long num);


    @Query(value = """
                        SELECT * FROM item_collection
                        WHERE item_collection.collection_id in
                               (
                                   SELECT item_collection_collection_id FROM item
                                   WHERE item_id = :itemId
                               );
                    """, nativeQuery = true)
    Optional<ItemCollection> findCollectionByItemId(@Param("itemId") Long itemId);
}
