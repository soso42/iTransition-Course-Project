package com.example.courseproject.model;

import com.example.courseproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {

    private List<Comment> comments;

}
