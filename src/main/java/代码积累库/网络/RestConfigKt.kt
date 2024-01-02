package 代码积累库.网络

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 *
 *  OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 *  2023/6/10 11:32
 */

@Component
@Configuration
open class RestConfigKt {
    @Bean
    open fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        return SimpleClientHttpRequestFactory().apply {
            setConnectTimeout(15000)
            setReadTimeout(5000)
        }

    }

    @Bean
    open fun restTemplate(clientHttpRequestFactory: ClientHttpRequestFactory): RestTemplate {
        return RestTemplate(clientHttpRequestFactory)
            .apply {
                val interceptor = ClientHttpRequestInterceptor { request, body, execution ->
                    execution.execute(
                        request.apply { this.headers.add(HttpHeaders.USER_AGENT, "") },
                        body
                    )
                }
                this.interceptors = listOf(interceptor)
                interceptors= listOf(interceptor)
            }
    }


}