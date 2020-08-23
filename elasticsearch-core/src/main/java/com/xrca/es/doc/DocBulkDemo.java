package com.xrca.es.doc;

import com.alibaba.fastjson.JSON;
import com.xrca.entity.Movie;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
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
 * @description 文档批量操作Demo
 * @date 2020-08-23 17:22
 */
@Component
public class DocBulkDemo {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * @Author xrca
     * @Description 批量添加文档
     * @Date 2020-08-23 17:23
     * @Param []
     * @return boolean
     **/
    public boolean bulkAddDoc() throws Exception {
        Movie movie1 = Movie.builder()
                .name("哪吒之魔童降世")
                .director("饺子")
                .release(new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-26"))
                .desc("天地灵气孕育出一颗能量巨大的混元珠，元始天尊将混元珠提炼成灵珠和魔丸，灵珠投胎为人，助周伐纣时可堪大用；而魔丸则会诞出魔王，为祸人间。元始天尊启动了天劫咒语，3年后天雷将会降临，摧毁魔丸。太乙受命将灵珠托生于陈塘关李靖家的儿子哪吒身上。然而阴差阳错，灵珠和魔丸竟然被掉包。本应是灵珠英雄的哪吒却成了混世大魔王。调皮捣蛋顽劣不堪的哪吒却徒有一颗做英雄的心。然而面对众人对魔丸的误解和即将来临的天雷的降临，哪吒是否命中注定会立地成魔？他将何去何从？")
                .build();
        Movie movie2 = Movie.builder()
                .name("流浪地球")
                .director("郭帆")
                .release(new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-05"))
                .desc("近未来，科学家们发现太阳急速衰老膨胀，短时间内包括地球在内的整个太阳系都将被太阳所吞没。为了自救，人类提出一个名为“流浪地球”的大胆计划，即倾全球之力在地球表面建造上万座发动机和转向发动机，推动地球离开太阳系，用2500年的时间奔往另外一个栖息之地。中国航天员刘培强（吴京 饰）在儿子刘启四岁那年前往国际空间站，和国际同侪肩负起领航者的重任。转眼刘启（屈楚萧 饰）长大，他带着妹妹朵朵（赵今麦 饰）偷偷跑到地表，偷开外公韩子昂（吴孟达 饰）的运输车，结果不仅遭到逮捕，还遭遇了全球发动机停摆的事件。为了修好发动机，阻止地球坠入木星，全球开始展开饱和式营救，连刘启他们的车也被强征加入。在与时间赛跑的过程中，无数的人前仆后继，奋不顾身，只为延续百代子孙生存的希望……")
                .build();
        Movie movie3 = Movie.builder()
                .name("我不是药神")
                .director("文牧野")
                .release(new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-05"))
                .desc("普通中年男子程勇（徐峥 饰）经营着一家保健品店，失意又失婚。不速之客吕受益（王传君 饰）的到来，让他开辟了一条去印度买药做“代购”的新事业，虽然困难重重，但他在这条“买药之路”上发现了商机，一发不可收拾地做起了治疗慢粒白血病的印度仿制药独家代理商。赚钱的同时，他也认识了几个病患及家属，为救女儿被迫做舞女的思慧（谭卓 饰）、说一口流利“神父腔”英语的刘牧师（杨新鸣 饰），以及脾气暴烈的“黄毛”（章宇 饰），几个人合伙做起了生意，利润倍增的同时也危机四伏。程勇昔日的小舅子曹警官（周一围 饰）奉命调查仿制药的源头，假药贩子张长林（王砚辉 饰）和瑞士正牌医药代表（李乃文 饰）也对其虎视眈眈，生意逐渐变成了一场关于救赎的拉锯战。")
                .build();

        BulkRequest bulkRequest = new BulkRequest();
        // 添加信息
        bulkRequest.add(new IndexRequest("movie").source(JSON.toJSONString(movie1), XContentType.JSON));
        bulkRequest.add(new IndexRequest("movie").source(JSON.toJSONString(movie2), XContentType.JSON));
        bulkRequest.add(new IndexRequest("movie").source(JSON.toJSONString(movie3), XContentType.JSON));

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.hasFailures();
    }

    /**
     * @Author xrca
     * @Description 批量更新
     * @Date 2020-08-23 22:08
     * @Param []
     * @return boolean
     **/
    public boolean bulkUpdateDoc() throws Exception {
        Map<String, Object> doc1 = new HashMap<>();
        doc1.put("boxOffice", 5028.08);

        Map<String, Object> doc2 = new HashMap<>();
        doc2.put("boxOffice", 4686.38);

        Map<String, Object> doc3 = new HashMap<>();
        doc3.put("boxOffice", 3100.0);

        BulkRequest bulkRequest = new BulkRequest();
        // 更新信息
        bulkRequest.add(new UpdateRequest().index("movie").id("QJ2UG3QBjlWZsBHB3sDI").doc(doc1));
        bulkRequest.add(new UpdateRequest().index("movie").id("QZ2UG3QBjlWZsBHB3sDI").doc(doc2));
        bulkRequest.add(new UpdateRequest().index("movie").id("Qp2UG3QBjlWZsBHB3sDI").doc(doc3));

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.hasFailures();
    }

    /**
     * @Author xrca
     * @Description 批量删除
     * @Date 2020-08-23 22:17
     * @Param []
     * @return boolean
     **/
    public boolean bulkDeleteDoc() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new DeleteRequest("movie", "QJ2UG3QBjlWZsBHB3sDI"));
        bulkRequest.add(new DeleteRequest("movie","QZ2UG3QBjlWZsBHB3sDI"));
        bulkRequest.add(new DeleteRequest("movie","Qp2UG3QBjlWZsBHB3sDI"));

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.hasFailures();
    }
}
