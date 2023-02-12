package com.luxoft.springadvanced.springwebclient;

import com.luxoft.springadvanced.springwebclient.feign.SimpleFeignClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableFeignClients
public class FeignClientTest {

    @Test
    public void testGet(@Autowired SimpleFeignClient simpleFeignClient) {
        SimpleFeignClient.Post post = simpleFeignClient.findPost(1);

        assertThat(post, is(notNullValue()));
    }

    @Test
    public void testPost(@Autowired SimpleFeignClient simpleFeignClient) {
        SimpleFeignClient.Post post = simpleFeignClient.addPost(new SimpleFeignClient.Post(null, "title", "body", 1));

        assertThat(post.id(), is(notNullValue()));
    }

}
