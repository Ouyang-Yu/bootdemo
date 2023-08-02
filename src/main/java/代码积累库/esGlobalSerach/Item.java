package 代码积累库.esGlobalSerach;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/12 15:33
 */
@Document(indexName = "item", createIndex = false)
public class Item {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")// title使用ik进行分词
    private String title;

    @Field(type = FieldType.Keyword)
    private String brand;// brand 不被分词

    @Field(type = FieldType.Double)
    private Double price;


    @Field(index = false, type = FieldType.Keyword)
    private String images;// brand 不被分词，且不创建索引
}

