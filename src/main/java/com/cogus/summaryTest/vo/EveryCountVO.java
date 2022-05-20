package com.cogus.summaryTest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EveryCountVO {
    private String datetime;
    private int subscriberCount;
    private int subscriberCountDiff;
    private int subscriberGainDiff;
    private int subscriberLostDiff;
    private int viewCountDiff;
    private int responseCountDiff;
}
