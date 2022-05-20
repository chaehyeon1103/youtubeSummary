package com.cogus.summaryTest.mapper;

import com.cogus.summaryTest.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SummaryMapper {
    public CountVO getCountList(String channelId, String endDate);
    public List<EveryCountVO> getEveryCountList(String channelId, String startDate, String endDate);
    public ManyHitsVideoVO getManyHitsVideoList(String channelId, String startDate, String endDate);
    public MonthResponseVO getMonthResponseCountList(String channelId, String startDate, String endDate);
    public RecommendedVideoVO getRecommendedVideo(String channelId);
    public List<CommentAuthorVO> getAuthor(String channelId, int startCount);

    public DateVO getDate(String startDate, String endDate);
}
