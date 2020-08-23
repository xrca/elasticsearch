package com.xrca.rest;

import com.xrca.es.doc.DocBulkDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xrca
 * @description 文档批量操作
 * @date 2020-08-23 18:08
 */
@RequestMapping("bulk")
@RestController
public class DocBulkController {
    @Autowired
    private DocBulkDemo docBulkDemo;

    @GetMapping("addDoc")
    public String bulkAddDoc() {
        try {
            return docBulkDemo.bulkAddDoc() ? "fail" : "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("updateDoc")
    public String bulkUpdateDoc() {
        try {
            return docBulkDemo.bulkUpdateDoc() ? "fail" : "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("deleteDoc")
    public String bulkDeleteDoc() {
        try {
            return docBulkDemo.bulkDeleteDoc() ? "fail" : "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
