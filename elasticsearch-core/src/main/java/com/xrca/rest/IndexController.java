package com.xrca.rest;

import com.xrca.es.index.IndexDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xrca
 * @description 测试类
 * @date 2020-08-23 12:30
 */
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private IndexDemo indexDemo;

    @GetMapping("index")
    public String index() {
        try {
            indexDemo.createIndex();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @GetMapping("indexByJson")
    public String indexByJson() {
        try {
            indexDemo.createIndexByJsonString();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "ok";
    }

    @GetMapping("indexExist")
    public String existIndex() {
        try {
            return indexDemo.indexExist() ? "ok" : "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("deleteIndex")
    public String deleteIndex() {
        try {
            return indexDemo.deleteIndex() ? "ok" : "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("getMapping")
    public String getMapping() {
        try {
            return indexDemo.getMappings();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
