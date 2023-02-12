package com.luxoft.springadvanced.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/serde/items")
public class ItemController {

    @GetMapping
    public Mono<Item> getItem() {
        return Mono.just(Item.simple());
    }

    @GetMapping(value = "/text", produces = "application/json")
    public Mono<String> getItemString() throws JsonProcessingException {
        final var item = Item.simple();
        final String serializedItem = new ObjectMapper().writeValueAsString(item);
        return Mono.just(serializedItem);
    }

    @PostMapping
    public Mono<Item> postItem(@RequestBody Item item) {
        return Mono.just(item);
    }
}
