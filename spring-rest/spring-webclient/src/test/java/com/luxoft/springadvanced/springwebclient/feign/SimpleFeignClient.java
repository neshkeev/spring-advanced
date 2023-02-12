package com.luxoft.springadvanced.springwebclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "posts", url = "https://jsonplaceholder.typicode.com/posts/")
public interface SimpleFeignClient {
    @GetMapping("/{id}")
    Post findPost(@PathVariable int id);

    @PostMapping("/")
    Post addPost(Post post);

    record Post(Integer id, String title, String body, int userId){}
}
