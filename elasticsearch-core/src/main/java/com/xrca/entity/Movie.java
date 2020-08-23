package com.xrca.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author xrca
 * @description 电影
 * @date 2020-08-23 17:27
 */
@Data
@Builder
public class Movie {
    // 影名
    private String name;

    // 导演
    private String director;

    // 票房
    private Double boxOffice;

    // 上映日期
    @JSONField(format = "yyyy-MM-dd")
    private Date release;

    // 简介
    private String desc;
}
