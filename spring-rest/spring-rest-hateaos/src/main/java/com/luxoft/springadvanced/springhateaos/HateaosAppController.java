package com.luxoft.springadvanced.springhateaos;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springdatarest.model.AppRepository;
import com.luxoft.springadvanced.springhateaos.assembler.AppAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/hateaos/apps")
public class HateaosAppController {

    private final AppRepository appRepository;
    private final AppAssembler assembler;

    public HateaosAppController(AppRepository appRepository, AppAssembler assembler) {
        this.appRepository = appRepository;
        this.assembler = assembler;
    }

    @GetMapping("/{appName}")
    public Mono<EntityModel<App>> get(@PathVariable String appName) {
        if (appName.equalsIgnoreCase("FB") || appName.equalsIgnoreCase("FACEBOOK")) {
            throw new FacebookException();
        }

        return appRepository.findById(UUID.nameUUIDFromBytes(appName.getBytes()))
                .map(assembler::toModel)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    @PostMapping
    public Mono<EntityModel<App>> post(App app) {
        if (app.getName().equalsIgnoreCase("FB") || app.getName().equalsIgnoreCase("FACEBOOK")) {
            throw new FacebookException();
        }

        return Mono.just(assembler.toModel(appRepository.save(app)));
    }

    @DeleteMapping("/{appName}")
    public Mono<Void> delete(@PathVariable String appName) {
        appRepository.deleteAllByName(appName);
        return Mono.empty();
    }

    @GetMapping
    public Mono<CollectionModel<EntityModel<App>>> findAll() {
        final var all = appRepository.findAll();
        final var data = assembler.toCollectionModel(all);
        return Mono.just(data);
    }


    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(FacebookException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    private static final class FacebookException extends IllegalArgumentException {
        public FacebookException() {
            super("Facebook is forbidden from conducting its business on Russia's territory");
        }
    }
}