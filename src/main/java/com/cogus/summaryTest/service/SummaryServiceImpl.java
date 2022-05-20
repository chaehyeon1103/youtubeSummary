package com.cogus.summaryTest.service;

import com.cogus.summaryTest.mapper.SummaryMapper;
import com.cogus.summaryTest.vo.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService{

    @Setter(onMethod_=@Autowired)
    private SummaryMapper mapper;

    @Override
    public CountVO getCountList(String channelId, String endDate) {
        return mapper.getCountList(channelId, endDate);
    }

    @Override
    public List<EveryCountVO> getEveryCountList(String channelId, String startDate, String endDate) {
        return mapper.getEveryCountList(channelId, startDate, endDate);
    }

    @Override
    public ManyHitsVideoVO getManyHitsVideoList(String channelId, String startDate, String endDate) {
        return mapper.getManyHitsVideoList(channelId, startDate, endDate);
    }

    @Override
    public MonthResponseVO getMonthResponseCountList(String channelId, String startDate, String endDate) {
        return mapper.getMonthResponseCountList(channelId, startDate, endDate);
    }

    @Override
    public RecommendedVideoVO getRecommendedVideo(String channelId) {
        return mapper.getRecommendedVideo(channelId);
    }

    @Override
    public List<CommentAuthorVO> getAuthor(String channelId, int page) {
        int startCount = (page - 1) * 10;
        return mapper.getAuthor(channelId, startCount);
    }

    @Override
    public DateVO getDate(String startDate, String endDate) {
        return mapper.getDate(startDate, endDate);
    }
}
