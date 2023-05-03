package 代码积累库.配置类;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(new UrlBasedCorsConfigurationSource() {{
            registerCorsConfiguration(
                    "/**",
                    new CorsConfiguration() {{
                        addAllowedOrigin("http://localhost");
                        addAllowedHeader("Content-Type");
                        setAllowCredentials(true);
                        addAllowedMethod(HttpMethod.GET);
                        addAllowedMethod("*");
                    }}
          //  new CorsConfiguration().applyPermitDefaultValues();
            );
        }});
    }
}
