package com.freechazz.game.eventManager;

import com.freechazz.game.eventManager.events.JsonSerializeField;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

// EventDeserializer

public class EventDeserializer implements JsonDeserializer<Event> {
    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get("type").getAsString();
        try {
            Class<?> clazz = Class.forName(className);
            Event event = context.deserialize(jsonObject, clazz);

            // Use reflection to set fields
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JsonSerializeField.class) && jsonObject.has(field.getName())) {
                    Object value = context.deserialize(jsonObject.get(field.getName()), field.getType());
                    field.set(event, value);
                }
            }

            return event;
        } catch (ClassNotFoundException | IllegalAccessException e) {
            throw new JsonParseException("Error deserializing event", e);
        }
    }
}
