package com.yatao.eisoo.service;

import com.yatao.eisoo.VO.BoolQueryVO;
import com.yatao.eisoo.VO.PeopleVO;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class PeopleService {

    @Autowired
    private RestHighLevelClient client;

    public String addPeple(PeopleVO vo) {
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("type", vo.getType())
                    .field("name", vo.getName())
                    .field("date", vo.getDate())
                    .endObject();
            IndexRequest request = new IndexRequest("people").source(content);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String findPeopleById(String id) {

        GetRequest request = new GetRequest("people", id);
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            Map<String, Object> resultMap = response.getSource();
            return resultMap.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String update(PeopleVO vo) {
        UpdateRequest request = new UpdateRequest("people", vo.getId());
        XContentBuilder content = null;
        try {
            content = XContentFactory.jsonBuilder().startObject()
                    .field("type", vo.getType())
                    .field("name", vo.getName())
                    .field("date", vo.getDate())
                    .field("id", vo.getId())
                    .endObject();
            request.doc(content);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(String id){
        DeleteRequest request = new DeleteRequest("people").id(id);
        DeleteResponse response = null;
        try {
            response = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }


    public String boolQuery(BoolQueryVO vo){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(vo.getType())){
            boolQuery.must(QueryBuilders.matchQuery("type",vo.getType()));
        }
        if (!StringUtils.isEmpty(vo.getName())){
            boolQuery.must(QueryBuilders.matchQuery("name",vo.getName()));
        }
        if (vo.getLtDate() !=null && vo.getGtDate() !=null){
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("date")
            .from(vo.getGtDate()).to(vo.getLtDate());
            boolQuery.filter(rangeQuery);
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQuery);
        SearchRequest request = new SearchRequest().source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
