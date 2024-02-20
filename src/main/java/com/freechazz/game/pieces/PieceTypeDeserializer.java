package com.freechazz.game.pieces;

import com.freechazz.generators.piece.PieceTypeGenerator;
import com.google.gson.*;

import java.lang.reflect.Type;


public class PieceTypeDeserializer implements JsonDeserializer<PieceType> {

    @Override
    public PieceType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject pieceTypeIdJson = jsonObject.getAsJsonObject("pieceTypeId");
        PieceTypeId pieceTypeId = context.deserialize(pieceTypeIdJson, PieceTypeId.class);

        int lvl = pieceTypeId.getLvl();
        long seed = pieceTypeId.getSeed();
        String generatorVersion = pieceTypeId.getGeneratorVersion();

        PieceType pieceType = PieceTypeGenerator.generate(lvl, seed, generatorVersion);
        pieceType.setSymbol(jsonObject.get("symbol").getAsString());
        return pieceType;
    }
}
