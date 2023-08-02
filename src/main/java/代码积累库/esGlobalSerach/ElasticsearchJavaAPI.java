package 代码积累库.esGlobalSerach;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/12 15:08
 */
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchJavaAPI {
    // https://juejin.cn/post/7046759829255225351
    final ElasticsearchClient elasticsearchClient;

    @Test
    public void query() throws IOException {
        SearchResponse<AhaIndex> search = elasticsearchClient.search(s -> s.index("aha-batch")
                        .query(q ->
                                q.term(t -> t.field("name").value(v -> v.stringValue("1aha")))
                        ),
                AhaIndex.class);

        for (Hit<AhaIndex> hit : search.hits().hits()) {
            log.info("== hit: {}", hit.source());
        }

    }

    @Test
    public void multiConditionQuery() throws IOException {
        SearchRequest request = SearchRequest.of(searchRequest ->
                searchRequest.index("INDEX_NAME")
                        .from(0)
                        .size(20)
                        .sort(builder -> builder.field(f -> f.field("age").order(SortOrder.Desc)))
                        // 如果有多个 .query 后面的 query 会覆盖前面的 query
                        .query(query ->
                                query.bool(boolQuery ->
                                        boolQuery
                                                // 在同一个 boolQuery 中 must 会将 should 覆盖
                                                .must(must -> must.range(
                                                        range -> range.field("age")
                                                                .gte(JsonData.of("21"))
                                                                .lte(JsonData.of("25"))
                                                ))
                                                .mustNot(mustNot -> mustNot.term(
                                                        e -> e.field("name").value(value -> value.stringValue("lisi1"))
                                                ))
                                                .should(must -> must.term(
                                                        e -> e.field("name").value(value -> value.stringValue("lisi2"))
                                                ))
                                )
                        )

        );

        SearchResponse<AhaIndex> searchResponse = elasticsearchClient.search(request, AhaIndex.class);


        log.info("返回的总条数有：{}", searchResponse.hits().total().value());
        searchResponse.hits()
                .hits()
                .forEach(hit -> log.info("== hit: {}, id: {}", hit.source(), hit.id()));


    }

    private class AhaIndex {}
}
