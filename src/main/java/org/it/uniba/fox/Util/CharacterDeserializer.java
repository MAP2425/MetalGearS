package org.it.uniba.fox.Util;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.it.uniba.fox.Entity.Item;

import java.lang.reflect.Type;

/**
 * The custom deserializer for the Agent class.
 * It checks if the agent is an Item or a Personage and deserializes it accordingly.
 */
public class CharacterDeserializer implements JsonDeserializer<Item> {
/**
 * Overrides the deserialize method of the JsonDeserializer interface.
 * Deserializes the agent.
 *
 * @param json the json element
 * @param typeOfT the type of the agent
 * @param context the context
 * @return the agent
 */
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();

        if ("Item".equals(type)) {
            return context.deserialize(json, Item.class);
        } else if ("Personage".equals(type)) {
            return context.deserialize(json, Item.class);
        } else {
            throw new JsonParseException("Unknown type: " + type);
        }
    }
}