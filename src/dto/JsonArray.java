package dto;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonValue {
    private List<JsonValue> elements = new ArrayList<>();

    public void add(JsonValue value) {
        elements.add(value);
    }

    public JsonValue get(int index) {
        return elements.get(index);
    }

    public List<JsonValue> getElements() {
        return elements;
    }
}