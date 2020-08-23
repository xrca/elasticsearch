package com.xrca.es.doc;

import com.alibaba.fastjson.JSON;
import com.xrca.entity.Movie;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xrca
 * @description 文档操作Demo
 * @date 2020-08-23 17:19
 */
@Component
public class DocDemo {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @Author xrca
     * @Description 索引文档
     * @Date 2020-08-23 17:20
     * @Param []
     * @return boolean
     **/
    public boolean addDoc() throws Exception {
        Movie movie = Movie.builder()
                .name("无间道")
                .director("刘伟强/麦兆辉")
                .boxOffice(5500.0)
                .release(new SimpleDateFormat("yyyy-MM-dd").parse("2002-12-12"))
                .desc("故事的主角是两名卧底人士，分别为警方和黑社会潜伏对方阵营。当两人的身份一下子将近暴露，他们要尽快找出对方是谁，以解除自身危险。")
                .build();
        String data = JSON.toJSONString(movie);
        IndexRequest indexRequest = new IndexRequest("movie");
        indexRequest.source(data, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getId());
        System.out.println(indexResponse.getResult());
        return true;
    }

    /**
     * @Author xrca
     * @Description 更新文档
     * @Date 2020-08-23 17:21
     * @Param []
     * @return boolean
     **/
    public boolean updateDoc() throws Exception {
        //UpdateRequest ur = new UpdateRequest("movie", "sp2zGnQBjlWZsBHBEKpQ");
        UpdateRequest updateRequest = new UpdateRequest().index("movie");
        Map<String, Object> doc = new HashMap<>();
        doc.put("director", "刘伟强/麦兆辉");

        updateRequest.id("sp2zGnQBjlWZsBHBEKpQ");
        updateRequest.doc(doc);

        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.getVersion());
        return updateResponse.getVersion() > 1;
    }

    /**
     * @Author xrca
     * @Description 删除文档
     * @Date 2020-08-23 17:21
     * @Param []
     * @return boolean
     **/
    public boolean deleteDoc() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest("movie", "sp2zGnQBjlWZsBHBEKpQ");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult().getLowercase());
        return true;
    }
}
