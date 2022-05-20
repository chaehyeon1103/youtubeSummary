package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecommendedVideoVO {
    private String videoId;
    private String title;
    private String pubDate;
    private String thumbnail;
    private int viewCount;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int responseCount;
}
