package com.freechazz.bots;

import com.freechazz.game.core.EPlayer;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Random;

public class BotDeserializer implements JsonDeserializer<Bot> {

    @Override
    public Bot deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract class name from "type" field in JSON
        String className = jsonObject.get("type").getAsString();

        try {
            // Load the class
            Class<?> clazz = Class.forName(className);

            // Deserialize the JSON object to the specified class
            Bot bot = (Bot) context.deserialize(jsonObject, clazz);

            // Handle the "player" field
            if (jsonObject.has("player")) {
                EPlayer player = EPlayer.valueOf(jsonObject.get("player").getAsString());
                bot.setPlayer(player);  // Assuming a setter method in your Bot class for the player enum
            }

            // Handle the "isReady" field
            if (jsonObject.has("isReady")) {
                boolean isReady = jsonObject.get("isReady").getAsBoolean();
                bot.setReady(isReady);  // Assuming a setter method in your Bot class for the isReady boolean
            }

            bot.setRand(new Random(312232));

            return bot;
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class not found: " + className, e);
        }
    }
}
