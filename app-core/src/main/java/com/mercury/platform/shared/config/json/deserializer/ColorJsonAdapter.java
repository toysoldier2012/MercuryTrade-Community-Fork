package com.mercury.platform.shared.config.json.deserializer;

import com.google.gson.*;
import com.mercury.platform.shared.config.descriptor.adr.*;

import java.awt.*;
import java.lang.reflect.Type;


public class ColorJsonAdapter implements JsonDeserializer<Color>, JsonSerializer<Color> {

    @Override
    public Color deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        int i = ((JsonObject) jsonElement).get("value").getAsInt();
        double alpha = ((JsonObject) jsonElement).get("falpha").getAsDouble();
        Color color = new Color(i);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha);
    }

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("value", color.getRGB());
        obj.addProperty("falpha", color.getAlpha());
        return obj;
    }
}
