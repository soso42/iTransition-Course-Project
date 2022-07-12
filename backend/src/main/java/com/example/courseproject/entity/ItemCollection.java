package com.example.courseproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "item_collection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "collection")
public class ItemCollection {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    @JsonProperty("collectionId")
    private Long ItemCollectionId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "topic")
    private String topic;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @JsonIgnore
    @OneToMany(mappedBy = "itemCollection", cascade = CascadeType.ALL)
    private List<Item> items;


    @Column(name = "custom_string_1")
    private String customString1;

    @Column(name = "custom_string_2")
    private String customString2;

    @Column(name = "custom_string_3")
    private String customString3;


    @Column(name = "custom_integer_1")
    private String customInteger1;

    @Column(name = "custom_integer_2")
    private String customInteger2;

    @Column(name = "custom_integer_3")
    private String customInteger3;


    @Column(name = "custom_boolean_1")
    private String customBoolean1;

    @Column(name = "custom_boolean_2")
    private String customBoolean2;

    @Column(name = "custom_boolean_3")
    private String customBoolean3;


    @Column(name = "custom_multilinetext_1")
    private String customMultilineText1;

    @Column(name = "custom_multilinetext_2")
    private String customMultilineText2;

    @Column(name = "custom_multilinetext_3")
    private String customMultilineText3;


    @Column(name = "custom_date_1")
    private String customDate1;

    @Column(name = "custom_date_2")
    private String customDate2;

    @Column(name = "custom_date_3")
    private String customDate3;

}
