package com.freechazz.game.pieces.stats;

import com.freechazz.game.core.Pos;
import com.freechazz.game.pieces.ActionMap;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ActionMapSerializer implements JsonSerializer<ActionMap> {

    @Override
    public JsonElement serialize(ActionMap actionMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        JsonArray actionsArray = new JsonArray();
        for (Pos pos : actionMap.keySet()) {
            JsonObject entryObject = new JsonObject();
            entryObject.add("pos", jsonSerializationContext.serialize(pos));
            entryObject.add("action", jsonSerializationContext.serialize(actionMap.get(pos)));
            actionsArray.add(entryObject);
        }

        jsonObject.add("actions", actionsArray);

        return jsonObject;
    }
}