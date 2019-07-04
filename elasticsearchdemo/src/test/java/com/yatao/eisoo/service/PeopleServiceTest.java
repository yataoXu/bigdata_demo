package com.yatao.eisoo.service;

import com.yatao.eisoo.VO.BoolQueryVO;
import com.yatao.eisoo.VO.PeopleVO;
import com.yatao.eisoo.config.EsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({EsConfig.class})
public class PeopleServiceTest {

    @Autowired
    private PeopleService peopleService;

    @Test
    public void addPeple() {
        PeopleVO people = new PeopleVO();
        people.setType("man");
        people.setName("wangwang");
        people.setDate("2000-01-01");
        String s = peopleService.addPeple(people);
        System.out.println(s);
    }

    @Test
    public void findPeople(){
        String peopleById = peopleService.findPeopleById("1");
        System.out.println(peopleById);
    }

    @Test
    public void updatePeople(){
        PeopleVO people = new PeopleVO();
        people.setId("1");
        people.setType("man");
        people.setName("milk");
        people.setDate("2000-01-01");
        String update = peopleService.update(people);
    }

    @Test
    public void queryPeople(){
        BoolQueryVO vo = new BoolQueryVO();
        vo.setLtDate("1997-01-01");
        vo.setLtDate("2010-01-01");
        String s = peopleService.boolQuery(vo);
        System.out.println(s);
    }

}