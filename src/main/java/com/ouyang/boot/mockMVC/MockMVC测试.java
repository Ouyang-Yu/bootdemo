package com.ouyang.boot.mockMVC;

import com.ouyang.boot.BootApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

/**
 * @author ouyangyu369@gmail.com
 * @time  2022-07-03  14:26
 */
@SpringBootTest(classes = BootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//随机端口启动,避免多个测试用例同时运行冲突
@AutoConfigureMockMvc
public class MockMVC测试 {
    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("给测试方法起个名字,没有就是原来的名字")
    public void mock1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("XX"))
                .andReturn();
    }

    @Test
    public void mockJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/json")
                        .header("token", "Xxx")//构建请求头
                        .param("id", "abc")//构建请求体
                ).andExpect(MockMvcResultMatchers.jsonPath("token").value("xxx"))
                .andReturn();
    }


}
