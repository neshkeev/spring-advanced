package com.luxoft.springadvanced.serde;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ItemSerializer.class)
@JsonDeserialize(using = ItemDeserializer.class)
public record Item(int id, String name, User owner) {
    static Item simple() {
        final var owner = new User(1, "Item owner");
        return new Item(1, "Item name", owner);
    }
}
