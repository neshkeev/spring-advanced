package com.luxoft.springadvanced.springhazelcastcaching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{isbn}")
    public Mono<String> getBookNameByIsbn(@PathVariable("isbn") String isbn) {
        return Mono.just(bookService.getBookNameByIsbn(isbn));
    }
}
