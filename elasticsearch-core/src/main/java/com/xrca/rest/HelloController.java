package com.xrca.rest;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xrca
 * @description 测试类
 * @date 2020-08-23 12:30
 */
@RestController
public class HelloController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("hello")
    public String hello() {
        return restHighLevelClient.toString();
    }
}
