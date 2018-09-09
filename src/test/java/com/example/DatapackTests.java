package com.example;

import com.example.dto.PageQueryParamDTO;
import com.example.dto.PageResultDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DatapackTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {
        String baseUrl = "http://localhost:" + port;

        // 测试查询分页
        PageQueryParamDTO param = new PageQueryParamDTO(1, 20, null);
        PageResultDTO pageResult = this.restTemplate.postForObject(baseUrl + "/datapack/loadPage", param,
                PageResultDTO.class);
        assertThat(pageResult).isNotNull();
        assertThat(pageResult.getList()).isNotEmpty();
        //assertThat(pageResult.getTotal()).isGreaterThan(0);
    }

}
