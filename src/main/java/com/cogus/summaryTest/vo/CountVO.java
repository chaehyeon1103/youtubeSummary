package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountVO {
    private int subscriberCount;
    private int viewCount;
    private float avgViewCount;
    private int responseCount;
}
