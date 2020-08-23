package com.xrca.rest;

import com.xrca.es.doc.DocDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xrca
 * @description
 * @date 2020-08-23 17:40
 */
@RestController
@RequestMapping("doc")
public class DocController {
    @Autowired
    private DocDemo docDemo;

    /**
     * @Author xrca
     * @Description 添加文档
     * @Date 2020-08-23 17:41
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("addDoc")
    public String addDoc() {
        try {
            docDemo.addDoc();
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * @Author xrca
     * @Description 更新文档
     * @Date 2020-08-23 17:53
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("updateDoc")
    public String updateDoc() {
        try {
            return docDemo.updateDoc() ? "ok" : "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * @Author xrca
     * @Description 删除文档
     * @Date 2020-08-23 18:00
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("deleteDoc")
    public String deleteDoc() {
        try {
            return docDemo.deleteDoc() ? "ok" : "fail";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
