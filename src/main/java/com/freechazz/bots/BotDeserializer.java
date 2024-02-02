package com.freechazz.bots;

import com.google.gson.*;

import java.lang.reflect.Type;

public class BotDeserializer implements JsonDeserializer<Bot> {
    @Override
    public Bot deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get("type").getAsString();
        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(jsonObject, clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class not found: " + className, e);
        }
    }
}
