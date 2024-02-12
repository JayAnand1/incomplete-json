package dto;

import java.util.HashMap;


public class JsonObject extends JsonValue {

    private final HashMap<String, JsonValue> properties = new HashMap<>();

    public void add(String key, JsonValue value) {
        properties.put(key, value);
    }

    public JsonValue get(String key) {
        return properties.get(key);
    }

    public HashMap<String, JsonValue> getProperties() {
        return properties;
    }

}