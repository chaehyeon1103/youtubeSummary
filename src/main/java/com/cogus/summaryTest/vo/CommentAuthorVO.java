package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentAuthorVO {
    private int videoCount;
    private int commentCount;
    private String authorId;
    private String authorName;
    private String authorImgUrl;
}
