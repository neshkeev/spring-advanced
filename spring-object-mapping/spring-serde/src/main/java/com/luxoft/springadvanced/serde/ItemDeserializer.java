package com.luxoft.springadvanced.serde;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Optional;

@JsonComponent
public class ItemDeserializer extends StdDeserializer<Item> {

    protected ItemDeserializer() {
        super(Item.class);
    }

    @Override
    public Item deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonNode node = jp.getCodec().readTree(jp);

        final var userId = Optional.ofNullable(node.get("owner"))
                .map(JsonNode::numberValue)
                .map(Number::intValue)
                .orElse(null);

        final var userName = Optional.ofNullable(node.get("ownerName"))
                .map(JsonNode::asText)
                .orElse(null);

        final var user = userId != null && userName != null
                ? new User(userId, userName)
                : null;

        final var itemId = node.get("id").numberValue().intValue();
        final var itemName = Optional.ofNullable(node.get("name"))
                .map(JsonNode::asText)
                .orElse(null);

        return new Item(itemId, itemName, user);
    }

}