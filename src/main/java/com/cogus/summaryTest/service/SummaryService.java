package com.cogus.summaryTest.service;

import com.cogus.summaryTest.vo.*;

import java.util.List;

public interface SummaryService {
    public CountVO getCountList(String channelId, String endDate);
    public List<EveryCountVO> getEveryCountList(String channelId, String startDate, String endDate);
    public ManyHitsVideoVO getManyHitsVideoList(String channelId, String startDate, String endDate);
    public MonthResponseVO getMonthResponseCountList(String channelId, String startDate, String endDate);
    public RecommendedVideoVO getRecommendedVideo(String channelId);
    public List<CommentAuthorVO> getAuthor(String channelId, int page);

    public DateVO getDate(String startDate, String endDate);
}
