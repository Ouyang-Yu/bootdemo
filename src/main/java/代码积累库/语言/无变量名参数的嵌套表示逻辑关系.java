package 代码积累库.语言;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-24  16:18
 */
public class 无变量名参数的嵌套表示逻辑关系 {


    public String before() {

        String url = "请求接口地址";
        String filePath = "文件路径地址";
        RestTemplate rest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders() {
            {
                String auth = "用户admin" + ":" + "密码123456";
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
                set("Content-Type", "multipart/form-data");
            }
        };

        FileSystemResource resource = new FileSystemResource(new File(filePath));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        //MultipartFile的名称
        param.add("file", resource);
        param.add("name", "test_template");
        param.add("fileName", "test_template.xlsx");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, httpHeaders);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String result = responseEntity.getBody();
        System.out.println(result);
        return result;
    }

    public String after() {
        String url = "请求接口地址";
        String filePath = "文件路径地址";
        String auth = "用户admin" + ":" + "密码123456";
        return new RestTemplate()
                .exchange(//4 args
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>( //2 args
                                new LinkedMultiValueMap<String,Object>() {{
                                    add("file", new FileSystemResource(new File(filePath)));
                                    add("name", "test_template");
                                    add("fileName", "test_template.xlsx");
                                }},
                                new HttpHeaders() {{
                                    set("Authorization", "Basic " + new String(
                                            Base64.getEncoder().encode(auth.getBytes(Charset.defaultCharset()))));
                                    set("Content-Type", "multipart/form-data");
                                }}
                        ),
                        String.class
                )
                .getBody();
    }


}
