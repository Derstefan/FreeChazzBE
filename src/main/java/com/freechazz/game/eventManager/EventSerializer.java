package com.freechazz.game.eventManager;

import com.freechazz.game.eventManager.events.JsonSerializeField;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

// EventSerializer

public class EventSerializer implements JsonSerializer<Event> {
    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", src.getClass().getName());

        // Use reflection to iterate through fields
        for (Field field : src.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonSerializeField.class)) {
                try {
                    jsonObject.add(field.getName(), context.serialize(field.get(src)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }
}
