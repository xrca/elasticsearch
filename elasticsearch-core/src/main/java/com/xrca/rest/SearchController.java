package com.xrca.rest;

import com.xrca.es.doc.SearchDemo;
import org.springframework.beans.factory.annotation.Autowired;
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
}
