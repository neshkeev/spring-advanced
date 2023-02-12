package com.luxoft.springadvanced.serde;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ItemSerializer extends StdSerializer<Item> {

    public ItemSerializer() {
        super(Item.class);
    }

    @Override
    public void serialize(Item value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", value.id());
        if (value.name() != null) jsonGenerator.writeStringField("name", value.name());

        final var owner = value.owner();
        if (owner != null) {
            jsonGenerator.writeNumberField("owner", owner.id());
            if (owner.name() != null) jsonGenerator.writeStringField("ownerName", owner.name());
        }

        jsonGenerator.writeEndObject();
    }
}