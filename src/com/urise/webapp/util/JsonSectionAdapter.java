package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    private static final String INSTANCE = "INSTANCE";
    private static final String CLASSNAME = "CLASSNAME";
    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonPrimitive prim = obj.getAsJsonPrimitive(CLASSNAME);
        String className = prim.getAsString();

        try {
            Class clazz = Class.forName(className);
            return jsonDeserializationContext.deserialize(obj.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement jsonElement = jsonSerializationContext.serialize(section);
        obj.add(INSTANCE, jsonElement);
        return jsonElement;
    }
}
