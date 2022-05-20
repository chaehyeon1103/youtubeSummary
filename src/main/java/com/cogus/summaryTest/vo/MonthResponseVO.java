package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonthResponseVO {
    private int viewCount;
    private int responseCount;
    private int subscriberCount;
    private int videoCount;
    private int likeCount;
    private int commentCount;
    private int viewPercent;
    private int responsePercent;
    private String datetime;
}
