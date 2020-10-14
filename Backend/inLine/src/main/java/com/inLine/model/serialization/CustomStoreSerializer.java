package com.inLine.model.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.inLine.model.Store;

import java.io.IOException;

public class CustomStoreSerializer extends JsonSerializer<Store> {
    @Override
    public void serialize(Store store, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", store.getId());
        jsonGenerator.writeEndObject();
    }
}
