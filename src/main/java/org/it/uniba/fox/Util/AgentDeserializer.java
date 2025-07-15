package org.it.uniba.fox.Util;

import com.google.gson.*;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Character;

import java.lang.reflect.Type;

public class AgentDeserializer implements JsonDeserializer<Agent> {

    @Override
    public Agent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Se isTalkable è true => è un Character
        if (jsonObject.has("isTalkable") && jsonObject.get("isTalkable").getAsBoolean()) {
            return context.deserialize(jsonObject, Character.class);
        } else {
            return context.deserialize(jsonObject, Item.class);
        }
    }
}
