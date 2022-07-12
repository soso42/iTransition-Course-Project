package com.example.courseproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateRequest {

    private Long collectionId;
    private String owner;
    private String name;
    private List<String> tags;

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
