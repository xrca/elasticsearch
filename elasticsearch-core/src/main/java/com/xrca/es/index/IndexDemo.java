package com.xrca.es.index;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xrca
 * @description es索引操作示例
 * @date 2020-08-23 12:43
 */
@Component
public class IndexDemo {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @Author xrca
     * @Description 创建索引，通过JsonXContent
     * @Date 2020-08-23 12:45
     * @Param []
     * @return void
     **/
    public void createIndex() throws Exception {
        // 创建settings
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 1) // 设置分片为1
                .put("number_of_replicas", 0); // 设置分片备份0，即不备份

        CreateIndexRequest createIndexRequest = new CreateIndexRequest("movie");
        createIndexRequest.settings(settings);
        // 设置别名
        createIndexRequest.alias(new Alias("film"));

        // 设置索引mappings
        /*
        "mappings": {
            "properties": {
                "name": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "index": true,
                    "store": false
                },
                "author": {
                    "type": "keyword"
                },
                "count": {
                    "type": "long"
                },
                "onSale": {
                    "type": "date",
                    "format": "yyyy-MM-dd HH:mm:ss"
                 },
                "desc": {
                    "type": "text",
                    "analyzer": "ik_max_word"
                }
            }
        }

         */
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        // name
                        .startObject("name")
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                            .field("index", true)
                            .field("store", false)
                        .endObject()
                        // director
                        .startObject("director")
                            .field("type", "keyword")
                        .endObject()
                        // boxOffice
                        .startObject("boxOffice")
                            .field("type", "double")
                        .endObject()
                        // Release
                        .startObject("release")
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd")
                        .endObject()
                        // desc
                        .startObject("desc")
                            .field("type", "text")
                            .field("analyzer", "ik_max_word")
                        .endObject()
                    .endObject()
                .endObject();
        // 添加mappings
        createIndexRequest.mapping(xContentBuilder);
        // 发送请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
    
    /**
     * @Author xrca
     * @Description 通过json格式的string来创建索引
     * @Date 2020-08-23 13:12
     * @Param []
     * @return void
     **/
    public void createIndexByJsonString() throws Exception {
        // 创建settings
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 1) // 设置分片为1
                .put("number_of_replicas", 0); // 设置分片备份0，即不备份

        CreateIndexRequest createIndexRequest = new CreateIndexRequest("music");
        createIndexRequest.settings(settings);
        // 设置别名
        createIndexRequest.alias(new Alias("song"));

        // 设置索引mappings
        /*
            "properties": {
                "name": {
                    "type": "text",
                    "analyzer": "ik_max_word",
                    "index": true,
                    "store": false
                },
                "singer": {
                    "type": "keyword"
                },
                "duration": {
                    "type": "long"
                },
                "release": {
                    "type": "date",
                    "format": "yyyy-MM-dd"
                },
                "desc": {
                    "type": "text",
                    "analyzer": "ik_max_word"
                }
            }
         */
        // 添加mappings
        createIndexRequest.mapping("{\"properties\":{\"name\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"index\":true,\"store\":false},\"singer\":{\"type\":\"keyword\"},\"duration\":{\"type\":\"long\"},\"release\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd\"},\"desc\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"}}}",
                XContentType.JSON);
        // 发送请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }
}
