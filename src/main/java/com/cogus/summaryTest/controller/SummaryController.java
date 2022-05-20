package com.cogus.summaryTest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.cogus.summaryTest.service.SummaryService;
import com.cogus.summaryTest.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {

    @Setter(onMethod_=@Autowired)
    private SummaryService service;

    //구독자수, 조회수, 평균 조회수, 반응수
    @GetMapping("/getCount")
    public ResponseEntity<String> getCount(String channelId, String endDate) {
        //System.out.println("구독자수, 조회수, 평균 조회수, 반응수 get");
        CountVO count = service.getCountList(channelId, endDate);

        //data값을 넣을 hashMap 생성
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("subscriberCount", count.getSubscriberCount());
        hashMap.put("viewCount", count.getViewCount());
        hashMap.put("avgViewCount", count.getAvgViewCount());
        hashMap.put("responseCount", count.getResponseCount());

        //hashMap을 이용해 json 생성
        JSONObject counts = new JSONObject(hashMap);

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(counts), headers, HttpStatus.OK);
    }

    //누적 구독자 수 추이, 일별 구독자 수 추이, 일별 조회 수, 일별 반응 수
    @GetMapping("/getEveryCount")
    public ResponseEntity<String> getEverySubCnt(String channelId, String startDate, String endDate) {
        //System.out.println("누적 구독자 수 추이, 일별 구독자 수 추이, 일별 조회 수, 일별 반응 수 get");
        List<EveryCountVO> list = service.getEveryCountList(channelId, startDate, endDate);

        JSONObject everyCountList = new JSONObject();

        //누적 구독자 수 추이
        JSONObject subCountList = new JSONObject();

        //일별 구독자 수 추이
        JSONObject everySubCountList = new JSONObject();
        int subGainTotal = 0;
        int subGainLost = 0;

        //일별 조회 수
        JSONObject viewCountList = new JSONObject();
        int viewTotal = 0;
        double viewAvg = 0.00;

        //일별 반응 수
        JSONObject responseCountList = new JSONObject();
        int responseTotal = 0;
        double responseAvg = 0.0;

        //list에서 하나씩 꺼내와 json 저장
        for (EveryCountVO everyCount : list) {

            //누적 구독자 수 추이
            JSONObject subCount = new JSONObject();
            subCount.put("subscriberCount", everyCount.getSubscriberCount());
            subCount.put("subscriberCountDiff", everyCount.getSubscriberCountDiff());

            subCountList.put(everyCount.getDatetime(), subCount);

            //일별 구독자 수 추이
            JSONObject everySubCount = new JSONObject();
            everySubCount.put("subscriberGainDiff", everyCount.getSubscriberGainDiff());
            everySubCount.put("subscriberLostDiff", everyCount.getSubscriberLostDiff());

            everySubCountList.put(everyCount.getDatetime(), everySubCount);

            subGainTotal += everyCount.getSubscriberGainDiff();
            subGainLost -= everyCount.getSubscriberLostDiff();

            //일별 조회 수
            JSONObject viewCount = new JSONObject();
            viewCount.put("viewCountDiff", everyCount.getViewCountDiff());

            viewCountList.put(everyCount.getDatetime(), viewCount);

            viewTotal += everyCount.getViewCountDiff();

            //일별 반응 수
            JSONObject responseCount = new JSONObject();
            responseCount.put("responseCountDiff", everyCount.getResponseCountDiff());

            responseCountList.put(everyCount.getDatetime(), responseCount);

            responseTotal += everyCount.getResponseCountDiff();

        }
        everyCountList.put("totalSubscriberCount", subCountList);

        everySubCountList.put("subscriberGainSum", subGainTotal);
        everySubCountList.put("subscriberLostSum", subGainLost);
        everyCountList.put("everySubscriberCount", everySubCountList);

        viewAvg = (double) viewTotal/list.size();
        viewCountList.put("viewCountSum", viewTotal);
        viewCountList.put("viewCountAvg", Math.round(viewAvg * 100)/100.0);
        everyCountList.put("everyViewCount", viewCountList);

        responseAvg = (double) responseTotal/ list.size();
        responseCountList.put("responseCountSum", responseTotal);
        responseCountList.put("responseCountAvg", Math.round(responseAvg * 10)/10.0);
        everyCountList.put("everyResponseCount", responseCountList);

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(everyCountList), headers, HttpStatus.OK);
    }

    //선택기간 가장 높은 조회수 받은 영상
    @GetMapping("/getManyHitsVideo")
    public ResponseEntity<String> getManyHitsVideo(String channelId, String startDate, String endDate) {
        //System.out.println("선택기간 가장 높은 조회수 받은 영상 get");
        ManyHitsVideoVO video = service.getManyHitsVideoList(channelId, startDate, endDate);

        JSONObject manyHitsVideo = new JSONObject();
        manyHitsVideo.put("viewCount", video.getViewCount());
        manyHitsVideo.put("likeCount", video.getLikeCount());
        manyHitsVideo.put("commentCount", video.getCommentCount());
        manyHitsVideo.put("link", "https://www.youtube.com/watch?v="+video.getVideoId());
        manyHitsVideo.put("title", video.getTitle());
        manyHitsVideo.put("thumbnail", video.getThumbnail());
        manyHitsVideo.put("date", video.getDate());

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(manyHitsVideo), headers, HttpStatus.OK);
    }

    //기간별 주요 지표 비교, 기간별 반응 상세 비교
    @GetMapping("/getMonthResponseCount")
    public ResponseEntity<String> getMonthResponseCount(String channelId, String startDate, String endDate) throws ParseException {
        //직전 동기간 구하기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date startDate2 = formatter.parse(startDate);
        Date endDate2 = formatter.parse(endDate);

        //차이나는 기간 일수 구하기
        long dateDiff = startDate2.getTime() - endDate2.getTime();
        TimeUnit time = TimeUnit.DAYS;
        dateDiff = time.convert(dateDiff, TimeUnit.MILLISECONDS) - 1;

        //beforeStart get
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate2);
        cal.add(Calendar.DATE, (int) dateDiff);
        String beforeStart = formatter.format(cal.getTime());

        //beforeEnd get
        cal.setTime(endDate2);
        cal.add(Calendar.DATE, (int) dateDiff);
        String beforeEnd = formatter.format(cal.getTime());

        List<MonthResponseVO> monthResponseList = new ArrayList<>();
        MonthResponseVO monthResponsee = service.getMonthResponseCountList(channelId, startDate, endDate);
        monthResponsee.setDatetime(startDate.toString()+"-"+endDate.toString());
        monthResponseList.add(monthResponsee);

        monthResponsee = service.getMonthResponseCountList(channelId, beforeStart, beforeEnd);
        monthResponsee.setDatetime(beforeStart.toString()+"-"+beforeEnd.toString());
        monthResponseList.add(monthResponsee);

        JSONObject monthTotal = new JSONObject();
        JSONObject monthResponseCount = new JSONObject();
        JSONObject monthResponseDetailCount = new JSONObject();

        JSONObject monthView = new JSONObject();
        JSONObject monthResponse = new JSONObject();
        JSONObject monthSub = new JSONObject();
        JSONObject monthMedia = new JSONObject();
        JSONObject monthLike = new JSONObject();
        JSONObject monthComment = new JSONObject();
        JSONObject monthResPer = new JSONObject();
        JSONObject monthviewPer = new JSONObject();

        for (MonthResponseVO month : monthResponseList) {
            //기간별 주요 지표 비교
            //조회
            monthView.put(month.getDatetime(), month.getViewCount());

            //반응
            monthResponse.put(month.getDatetime(), month.getResponseCount());

            //구독자
            monthSub.put(month.getDatetime(), month.getSubscriberCount());

            //미디어
            monthMedia.put(month.getDatetime(), month.getVideoCount());

            //기간별 반응 상세 비교
            //좋아요
            monthLike.put(month.getDatetime(), month.getLikeCount());

            //댓글
            monthComment.put(month.getDatetime(), month.getCommentCount());

            //반응율
            monthResPer.put(month.getDatetime(), month.getResponsePercent());

            //구독대비 조회수
            monthviewPer.put(month.getDatetime(), month.getViewPercent());
        }

        //기간별 주요 지표 비교
        monthResponseCount.put("viewCount", monthView);
        monthResponseCount.put("responseCount", monthResponse);
        monthResponseCount.put("subscriberCount", monthSub);
        monthResponseCount.put("mediaCount", monthMedia);

        //기간별 반응 상세 비교
        monthResponseDetailCount.put("viewCount", monthView);
        monthResponseDetailCount.put("likeCount", monthLike);
        monthResponseDetailCount.put("commentCount", monthComment);
        monthResponseDetailCount.put("responsePercent", monthResPer);
        monthResponseDetailCount.put("viewPercent", monthviewPer);

        monthTotal.put("mainCompare", monthResponseCount);
        monthTotal.put("responseDetailCompare", monthResponseDetailCount);

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(monthTotal), headers, HttpStatus.OK);
    }

    //추천 동영상
    @GetMapping("/getRecommendedVideo")
    public ResponseEntity<String> getMonthResponseCount(String channelId) {
        //System.out.println("추천 동영상 get");
        RecommendedVideoVO video = service.getRecommendedVideo(channelId);

        JSONObject recommendedVideo = new JSONObject();
        recommendedVideo.put("title", video.getTitle());
        recommendedVideo.put("pubDate", video.getPubDate());
        recommendedVideo.put("viewCount", video.getViewCount());
        recommendedVideo.put("responseCount", video.getResponseCount());
        recommendedVideo.put("likeCount", video.getLikeCount());
        recommendedVideo.put("dislikeCount", video.getDislikeCount());
        recommendedVideo.put("commentCount", video.getCommentCount());
        recommendedVideo.put("link", "https://www.youtube.com/watch?v="+video.getVideoId());
        recommendedVideo.put("thumbnail", video.getThumbnail());

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(recommendedVideo), headers, HttpStatus.OK);
    }

    //내 채널에 가장 많은 댓글을 단 사람
    @GetMapping("/getAuthor")
    public ResponseEntity<String> getMonthResponseCount(String channelId, int page) {

        JSONArray jsonAuthorList = new JSONArray();

        //작성자가 몇 개의 동영상에 몇 개의 댓글을 작성했는지 가져오기
        List<CommentAuthorVO> authorList = service.getAuthor(channelId, page);

        for (CommentAuthorVO author : authorList) {
            JSONObject jsonAuthor = new JSONObject();
            jsonAuthor.put("commentedVideoCount", author.getVideoCount());
            jsonAuthor.put("commentCount", author.getCommentCount());
            jsonAuthor.put("authorName", author.getAuthorName());
            jsonAuthor.put("authorImgUrl", author.getAuthorImgUrl());

            jsonAuthorList.add(jsonAuthor);
        }

        //출력
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<String>(String.valueOf(jsonAuthorList), headers, HttpStatus.OK);
    }
}
