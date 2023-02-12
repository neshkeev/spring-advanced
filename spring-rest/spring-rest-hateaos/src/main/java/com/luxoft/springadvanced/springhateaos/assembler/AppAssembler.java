package com.luxoft.springadvanced.springhateaos.assembler;

import com.luxoft.springadvanced.springdatarest.model.App;
import com.luxoft.springadvanced.springhateaos.HateaosAppController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.hateoas.server.core.LinkBuilderSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class AppAssembler implements SimpleRepresentationModelAssembler<App> {

    @Override
    public void addLinks(EntityModel<App> resource) {
        resource.add(linkTo(methodOn(HateaosAppController.class).findAll()).withRel("apps"));

        Optional.ofNullable(resource.getContent())
                .map(App::getName)
                .map(methodOn(HateaosAppController.class)::get)
                .map(WebMvcLinkBuilder::linkTo)
                .map(LinkBuilderSupport::withSelfRel)
                .ifPresent(resource::add);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<App>> resources) {
        resources.add(linkTo(methodOn(HateaosAppController.class).findAll()).withSelfRel());
    }
}
