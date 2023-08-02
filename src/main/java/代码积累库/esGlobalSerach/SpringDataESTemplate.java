package 代码积累库.esGlobalSerach;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/12 15:31
 */
@RequiredArgsConstructor
public class SpringDataESTemplate {
    // https://juejin.cn/post/6976253744342122504#heading-2

    // # es集群的名字 服务器config文件中设置的名字
    // spring.data.elasticsearch.cluster-name=elasticsearch
    // #es机器的可用节点 多个节点可用英文逗号分隔
    // spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300

    final ElasticsearchRestTemplate elasticsearchRestTemplate;

}
