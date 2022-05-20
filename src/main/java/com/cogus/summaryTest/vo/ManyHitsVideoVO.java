package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManyHitsVideoVO {
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private String videoId;
    private String title;
    private String thumbnail;
    private String date;
}
