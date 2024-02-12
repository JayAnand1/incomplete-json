package dto;

public class JsonNumber extends JsonValue {
    private final Number value;

    public JsonNumber(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }
}