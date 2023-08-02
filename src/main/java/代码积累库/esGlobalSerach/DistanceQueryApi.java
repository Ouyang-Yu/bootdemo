package 代码积累库.esGlobalSerach;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.ParsedGeoDistance;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * distance query 类型的 地理位置查询
 *
 * @author huan.fu 2021/4/22 - 下午4:00
 */
public class DistanceQueryApi {

    @Resource
    RestHighLevelClient client;


    @DisplayName("距离查询")
    @Test
    public void distanceQueryTest() throws IOException {

        // 查询请求
        SearchRequest searchRequest = new SearchRequest("geo_index");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 从索引那个开始返回数据
        searchSourceBuilder.from(0);
        // 查询多少条记录
        searchSourceBuilder.size(10);
        // 查询超时时间
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(20));

        // 构造查询和过滤数据
        searchSourceBuilder.query(
                // 构造布尔查询
                QueryBuilders.boolQuery()
                        // 查询语句
                        .must(QueryBuilders.matchAllQuery())
                        // 过滤语句
                        .filter(
                                // name 是过滤的字段
                                QueryBuilders.geoDistanceQuery("location")
                                        // 在3km之内
                                        .distance("3", DistanceUnit.KILOMETERS)
                                        // 以那个点为中心
                                        .point(31.256224D, 121.462311D)
                                        .geoDistance(GeoDistance.ARC)
                                        // 一个查询的名字，可选
                                        .queryName("optional_name")
                        )
        );
        // 后置过滤
        searchSourceBuilder.postFilter(
                // name 是过滤的字段
                QueryBuilders.geoDistanceQuery("location")
                        .distance("1", DistanceUnit.KILOMETERS)
                        .point(31.256224D, 121.462311D)
        );

        // 排序
        searchSourceBuilder.sort(
                // 不同的类型使用不同的SortBuilder
                new GeoDistanceSortBuilder("location", 31.256224D, 121.462311D)
                        .order(SortOrder.DESC)
                        .unit(DistanceUnit.METERS)
                        .geoDistance(GeoDistance.ARC)
        );

        // 聚合操作
        searchSourceBuilder.aggregation(
                // name 聚合的名字 point 以那个点为中心开始聚合
                AggregationBuilders.geoDistance("distanceAgg", new GeoPoint(31.256224D, 121.462311D))
                        // 字段
                        .field("location")
                        .unit(DistanceUnit.METERS)
                        .distanceType(GeoDistance.ARC)
                        .keyed(true)
                        // 范围
                        .addRange("first", 0.0D, 500D)
                        .addRange("second", 500D, 1000D)
                        .addRange("third", 1000D, Double.NEGATIVE_INFINITY)
        );

        searchRequest.source(searchSourceBuilder);
        System.out.println("查询语句：" + searchSourceBuilder.toString());

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("查询结果：" + searchResponse);

        System.out.println(searchResponse.status().getStatus());
        System.out.println(searchResponse.isTerminatedEarly());
        System.out.println(searchResponse.isTimedOut());

        for (ShardSearchFailure shardFailure : searchResponse.getShardFailures()) {
            System.out.println(shardFailure);
        }

        // 匹配到的结果
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 数据
            System.out.println(hit.getSourceAsMap());
            // 排序距离
            Object[] sortValues = hit.getSortValues();
            System.out.println(Arrays.toString(sortValues));
            System.out.println("=====");
        }

        // 获取聚合的结果
        Aggregations aggregations = searchResponse.getAggregations();
        aggregations.getAsMap().forEach((key, value) -> {
            System.out.println("key:" + key);
            for (Range.Bucket bucket : ((ParsedGeoDistance) value).getBuckets()) {
                System.out.println("from:" + bucket.getFromAsString() + " to:" + bucket.getToAsString() + " value:" + bucket.getDocCount());
            }
            System.out.println("------------");
        });
    }
}
