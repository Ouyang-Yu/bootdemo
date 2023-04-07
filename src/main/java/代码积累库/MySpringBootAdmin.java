package 代码积累库;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Configuration
public class MySpringBootAdmin {
    @Bean
    public InfoContributor infoContributor() {
        return new InfoContributor() {
            @Override
            public void contribute(Info.Builder builder) {
                builder.withDetails(new HashMap<>() {{
                    put("runTime", LocalDateTime.now());
                }});
            }
        };
    }
    @Bean

    public HealthIndicator healthIndicator() {
        return   new AbstractHealthIndicator() {
            @Override
            protected void doHealthCheck(Health.Builder builder) throws Exception {
                builder.up();
                builder.withDetails(new HashMap<>() {{
                    put("startTime", LocalDateTime.now());
                }});
            }
        };
    }

    @Component
    @Endpoint(id = "pay")
    class PayEndPoint {
        @ReadOperation
        public Object getPay() {
            return new HashMap<String, String>();
        }
    }

}
