package com.xrca.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author xrca
 * @description es客户端配置
 * @date 2020-08-23 12:20
 */
@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.hosts:}")
    private String hosts;

    /**
     * @Author xrca
     * @Description 配置es的客户端
     * @Date 2020-08-23 12:24
     * @return org.elasticsearch.client.RestHighLevelClient
     **/
    @Bean
    public RestHighLevelClient restHighLevelClient() throws Exception {
        if (StringUtils.isEmpty(hosts)) {
            throw new Exception("es hosts not config");
        }

        // 解析配置信息
        String[] hostArr = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArr.length];
        for (int i = 0; i < hostArr.length; i++) {
            String[] hostInfo = hostArr[i].split(":");
            if (hostInfo.length != 2) {
                throw new Exception("es host: " + hostArr[i] + " config error");
            }
            httpHosts[i] = new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]));
        }
        /*
         https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.8/_changing_the_client_8217_s_initialization_code.html
         RestHighLevelClient是线程安全的，官方建议在系统启动或者第一个请求执行时，将其初始化
         The RestHighLevelClient is thread-safe. It is typically instantiated by the application at startup time or when the first request is executed.
         */
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHosts));
        return restHighLevelClient;
    }
}
