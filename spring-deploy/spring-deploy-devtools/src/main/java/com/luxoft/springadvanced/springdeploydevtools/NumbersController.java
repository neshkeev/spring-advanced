package com.luxoft.springadvanced.springdeploydevtools;

import com.luxoft.springadvanced.springdeploydevtools.aop.SimpleComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class NumbersController {
    @Autowired
    SimpleComponent component;

    @GetMapping("/nums")
    public Flux<Integer> getNumbers() {
        component.f();
        return Flux.range(0, 5);
    }
}
