package com.example.courseproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "item")
public class Item {

    @Id
    @org.springframework.data.annotation.Id
    @Field(type = FieldType.Keyword)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Field(type = FieldType.Text, name = "name")
    @Column(name = "name")
    private String name;

    @Field(type = FieldType.Object)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "item_tag_mapping",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @org.springframework.data.annotation.Transient
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_user_likes_mapping",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private ItemCollection itemCollection;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @org.springframework.data.annotation.Transient
    @Field(type = FieldType.Object)
    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments;


    @Column(name = "custom_string_1")
    private String customString1;

    @Column(name = "custom_string_2")
    private String customString2;

    @Column(name = "custom_string_3")
    private String customString3;


    @Column(name = "custom_integer_1")
    private Integer customInteger1;

    @Column(name = "custom_integer_2")
    private Integer customInteger2;

    @Column(name = "custom_integer_3")
    private Integer customInteger3;


    @Column(name = "custom_boolean_1")
    private Boolean customBoolean1;

    @Column(name = "custom_boolean_2")
    private Boolean customBoolean2;

    @Column(name = "custom_boolean_3")
    private Boolean customBoolean3;


    @Column(name = "custom_multilinetext_1", length = 2000)
    private String customMultilineText1;

    @Column(name = "custom_multilinetext_2", length = 2000)
    private String customMultilineText2;

    @Column(name = "custom_multilinetext_3", length = 2000)
    private String customMultilineText3;


    @Field(type=FieldType.Date, format=DateFormat.basic_date)
    @Column(name = "custom_date_1")
    private LocalDate customDate1;

    @Field(type=FieldType.Date, format=DateFormat.basic_date)
    @Column(name = "custom_date_2")
    private LocalDate customDate2;

    @Field(type=FieldType.Date, format=DateFormat.basic_date)
    @Column(name = "custom_date_3")
    private LocalDate customDate3;


    // Transient fields are used for fetching Latest items information

    @Transient
    private ItemCollection parentCollection;

}
