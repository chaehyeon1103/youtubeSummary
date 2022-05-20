package com.cogus.summaryTest.test;

import com.cogus.summaryTest.mapper.SummaryMapper;
import com.cogus.summaryTest.vo.CommentAuthorVO;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTests {
    @Setter(onMethod_=@Autowired)
    private SummaryMapper mapper;

    @Test
    public void test1() {
//        CommentAuthorVO author = mapper.getAuthor("UCojm0qrKPoxt6lc6J4iDCrQ", "UCcyjJXbvQlopXFWLX-xjCag");
//        System.out.println(author);
    }
}
