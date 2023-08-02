package 代码积累库.esGlobalSerach;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

public class ESHighLevelAPI {

    @Resource
    private RestHighLevelClient client;

    @SneakyThrows
    private static void hitSourceToBean(SearchHit hit) {
        Bean bean1 = new ObjectMapper().readValue(hit.getSourceAsString(), Bean.class);
        System.out.println(bean1);
        Bean bean = BeanUtil.mapToBean(hit.getSourceAsMap(), Bean.class, false, CopyOptions.create());
        System.out.println(bean);
    }

    @Test
    public void createIndex() throws IOException {
        client.indices().create(new CreateIndexRequest("books"), RequestOptions.DEFAULT);
        // 通过json定义一类文档的数据结构,即索引
        boolean books = client.indices()
                .exists(new GetIndexRequest("books"), RequestOptions.DEFAULT);

        boolean books1 = client.indices()
                .delete(new DeleteIndexRequest("books"), RequestOptions.DEFAULT)
                .isAcknowledged();
    }

    @Test
    public void addDocumentToIndex() throws IOException {


        IndexRequest request = new IndexRequest("books")
                .id("1")
                .source("json from bean", XContentType.JSON);
        GetRequest getRequest = new GetRequest();
        UpdateRequest updateRequest = new UpdateRequest();
        DeleteRequest deleteRequest = new DeleteRequest();
        client.index(request, RequestOptions.DEFAULT);// 添加文档,即要查询的数据


    }


    @Test
    public void bulk() throws IOException {
        ArrayList<Bean> beans = new ArrayList<>();
        client.bulk(

                new BulkRequest() {{
                    beans.forEach(bean -> {
                        IndexRequest request = new IndexRequest("books").source(JSONUtil.toJsonStr(bean), XContentType.JSON);
                        add(request);
                    });
                }}

                , RequestOptions.DEFAULT
        );// 也可以把多个数据添加到bulk一起提交
    }

    @Test
    public void search() throws Exception {
        SearchRequest request = new SearchRequest("books")
                .source(new SearchSourceBuilder()
                        .query(QueryBuilders.termQuery("name", "java"))
                        .from(0)
                        .size(5)
                );
        client.search(request, RequestOptions.DEFAULT)
                .getHits()
                .forEach(ESHighLevelAPI::hitSourceToBean);// 搜索出的结果转成实体类
    }

    @Configuration
    static class ESConfig {

        @Bean
        public RestHighLevelClient restHighLevelClient() throws IOException {
            HttpHost host = HttpHost.create("http://localhsot:9200");
            try (RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host))) {
                return client;
            }

        }
    }

    @Test
    public void dsf() {


    }
}


