package com.xrca.rest;

import com.xrca.es.doc.SearchDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xrca
 * @description
 * @date 2020-08-23 23:16
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchDemo searchDemo;

    @GetMapping("termSearch")
    public String termSearch(String keyword) {
        try {
            return searchDemo.termSearch(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("termsSearch")
    public String termsSearch(String keywords) {
        try {
            return searchDemo.termsSearch(keywords);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("matchAllSearch")
    public String matchAllSearch() {
        try {
            return searchDemo.matchAllSearch();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("matchSearch")
    public String matchSearch(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return "请输入关键词";
        }
        try {
            return searchDemo.matchSearch(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @GetMapping("matchBoolSearch")
    public String matchBoolSearch(String keyword, String op) {
        if (StringUtils.isEmpty(keyword) || StringUtils.isEmpty(op)) {
            return "请将参数输入完整";
        }
        try {
            return searchDemo.matchBoolSearch(keyword, op);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}