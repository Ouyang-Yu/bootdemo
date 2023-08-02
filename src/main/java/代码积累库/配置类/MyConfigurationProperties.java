package 代码积累库.配置类;

import com.ouyang.boot.entity.Account;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/4 21:29
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "my")
//@PropertySource(value = "classpath:application-dev.properties") //指定读取哪一个文件
@RefreshScope
public class MyConfigurationProperties {
    String name;
    String sex;
    Account account;
    List<Account> accountList;
}
/**
 * #{
 * #  "properties": [
 * #    {
 * #      "name": "person.pwd",
 * #      "type": "java.lang.String",
 * #      "description": "Description for person.pwd."
 * #    }
 * #  ],
 * #  "hints": {
 * #    "name": "person.name",
 * #    "values": [
 * #      {
 * #        "value": "ouyang",
 * #        "description": "family name."
 * #      },
 * #      {
 * #        "value": "yu",
 * #        "description": " name."
 * #      }
 * #    ]
 * #  }
 * #}
 */
