package com.xrca.es.doc;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xrca
 * @description 查询操作
 * @date 2020-08-23 23:17
 */
@Component
public class SearchDemo {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @Author xrca
     * @Description term查询 -> where name like '%keyword%'
     * @Date 2020-08-23 23:17
     * @Param []
     * @return java.lang.String
     **/
    public String termSearch(String keyword) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页
        searchSourceBuilder.from(0).size(10);

        // 添加or查询
        if (!StringUtils.isEmpty(keyword)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("name", keyword));
            boolQueryBuilder.should(QueryBuilders.termQuery("director", keyword));
            boolQueryBuilder.should(QueryBuilders.termQuery("desc", keyword));
            searchSourceBuilder.query(boolQueryBuilder);
        }

        // 在request中添加查询内容
        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description terms查询 -> where name like '%keyword1%' or name like '%keyword%'
     * @Date 2020-08-23 23:40
     * @Param [keywords]
     * @return java.lang.String
     **/
    public String termsSearch(String keywords) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 分页
        searchSourceBuilder.from(0).size(10);

        // 添加or查询
        if (!StringUtils.isEmpty(keywords)) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.termsQuery("desc", keywords.split(",")));
            searchSourceBuilder.query(boolQueryBuilder);
        }

        // 在request中添加查询内容
        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description matchAll查询，查询所有 -> select * from xxx
     * @Date 2020-08-24 20:36
     * @Param []
     * @return java.lang.String
     **/
    public String matchAllSearch() throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询所有
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description match查询
     * @Date 2020-08-24 20:43
     * @Param [keyword]
     * @return java.lang.String
     **/
    public String matchSearch(String keyword) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询desc相关额内容
        searchSourceBuilder.query(QueryBuilders.matchQuery("desc", keyword));

        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description match中的bool查询，分词后需要全部匹配
     * @Date 2020-08-24 20:57
     * @Param [keyword, op]
     * @return java.lang.String
     **/
    public String matchBoolSearch(String keyword, String op) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // match查询，将关键词分词后查询
        // tips：还可以指定分词，指定将keyword分词时，使用那种分词器
        searchSourceBuilder.query(QueryBuilders
                .matchQuery("desc", keyword)
                .operator("AND".equalsIgnoreCase(op) ? Operator.AND : Operator.OR)
                .analyzer("ik_max_word"));

        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description must & should查询
     * @Date 2020-08-24 21:09
     * @Param [keyword]
     * @return java.lang.String
     **/
    public String mustAndShouldSearch(String keyword) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // bool查询，区别于上面的matchBool，进行多字段的bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // must：必须满足query条件 should：query条件可以不满足
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", keyword));
        boolQueryBuilder.must(QueryBuilders.matchQuery("desc", keyword).operator(Operator.OR));

        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);

        searchRequest.source(searchSourceBuilder);

        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description multi_match查询，同一keyword去匹配多个字段，匹配到一个即可
     * @Date 2020-08-24 21:35
     * @Param [keyword]
     * @return java.lang.String
     **/
    public String multiSearch(String keyword) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 指定查询的字段，同时指定分词器
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "director", "desc").analyzer("ik_max_word"));

        searchRequest.source(searchSourceBuilder);
        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }

    /**
     * @Author xrca
     * @Description 根据id查询文档
     * @Date 2020-08-24 21:55
     * @Param [id]
     * @return java.lang.String
     **/
    public String searchById(String id) throws Exception {
        GetRequest getRequest = new GetRequest("movie", id);

        // 查询，并返回文档
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsString();
    }

    /**
     * @Author xrca
     * @Description 根据一组id查询文档
     * @Date 2020-08-24 21:55
     * @Param [id]
     * @return java.lang.String
     **/
    public String searchByIds(String ids) throws Exception {
        SearchRequest searchRequest = new SearchRequest("movie");

        // 查询，并返回文档
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds(ids.split(",")));

        searchRequest.source(searchSourceBuilder);
        // 解析查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits() != null && searchResponse.getHits().getTotalHits().value > 0) {
            List<String> response = new ArrayList<>(searchResponse.getHits().getHits().length);
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                response.add(searchHit.getSourceAsString());
            }
            return response.toString();
        }
        return "";
    }
}
