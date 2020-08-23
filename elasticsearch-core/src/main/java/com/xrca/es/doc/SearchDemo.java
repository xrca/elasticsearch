package com.xrca.es.doc;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @Description term查询
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
