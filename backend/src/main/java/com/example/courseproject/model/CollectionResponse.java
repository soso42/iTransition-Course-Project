package com.example.courseproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionResponse {

    private Long ItemCollectionId;
    private Long authorId;
    private String name;
    private String description;
    private String topic;

    private String customString1;
    private String customString2;
    private String customString3;

    private Integer customInteger1;
    private Integer customInteger2;
    private Integer customInteger3;

    private Boolean customBoolean1;
    private Boolean customBoolean2;
    private Boolean customBoolean3;

    private String customMultilineText1;
    private String customMultilineText2;
    private String customMultilineText3;

    private LocalDate customDate1;
    private LocalDate customDate2;
    private LocalDate customDate3;
}
