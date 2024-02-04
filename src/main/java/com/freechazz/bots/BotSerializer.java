package com.freechazz.bots;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BotSerializer implements JsonSerializer<Bot> {
    @Override
    public JsonElement serialize(Bot src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getName());
        // Add other fields if needed
        return jsonObject;
    }
}
