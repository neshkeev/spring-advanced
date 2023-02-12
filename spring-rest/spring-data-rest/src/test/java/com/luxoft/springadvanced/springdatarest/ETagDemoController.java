package com.luxoft.springadvanced.springdatarest;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springdatarest.model.AppRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ETagDemoController {

    private final AppRepository appRepository;

    public ETagDemoController(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @GetMapping("/myapps")
    public Flux<App> get() {
        return Flux.fromIterable(appRepository.findAll(Pageable.unpaged()));
    }
}
