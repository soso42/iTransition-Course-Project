package com.example.courseproject.repository;

import com.example.courseproject.entity.Item;
import com.example.courseproject.entity.ItemCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByItemCollection(ItemCollection collection);
    void deleteByItemId(Long id);

    @Override
    List<Item> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM item ORDER BY item_id DESC LIMIT :num ;", nativeQuery = true)
    List<Item> getLatestItems(@Param("num") Long num);

}
