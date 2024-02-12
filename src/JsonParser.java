import dto.*;

public class JsonParser {

    private int index = 0;

    public JsonObject readJson(String json) throws Exception {
        if (json == null) {
            throw new Exception("Invalid Input");
        }
        if (json.length() == 0) {
            throw new Exception("Empty String");
        }
        json = json.trim();
        index = 0;
        if (json.charAt(0) != '{') {
            throw new Exception("JSON truncated from start");
        }
        return (JsonObject) parseElement(json.trim());
    }

    private JsonValue parseObject(String json) throws Exception {
        JsonObject jsonObject = new JsonObject();
        index++;
        while (index < json.length()) {
            skipWhiteSpace(json);
            if (index < json.length() && json.charAt(index) == '}') {
                index++;
                break;
            }
            if (index < json.length() && json.charAt(index) == '"') {
                index++;
                StringBuilder key = new StringBuilder();
                while (index < json.length() && json.charAt(index) != '"') {
                    key.append(json.charAt(index));
                    index++;
                }

                while (index < json.length() && json.charAt(index) != ':') {
                    index++;
                }
                index++;
                if (index < json.length()) {
                    JsonValue value = parseElement(json);
                    jsonObject.add(key.toString(), value);
                } else {
                    jsonObject.add(key.toString(), null);
                }
            } else {
                index++;
            }
        }
        return jsonObject;
    }

    private JsonValue parseArray(String json) throws Exception {
        JsonArray jsonArray = new JsonArray();
        index++;
        boolean exit = false;
        while (index < json.length()) {
            JsonValue value = parseElement(json);
            jsonArray.add(value);
            while (index < json.length() && json.charAt(index) != ',') {
                if (json.charAt(index) == ']') {
                    index++;
                    exit = true;
                    break;
                }
                index++;
            }
            if (exit) {
                break;
            }
            index++;
        }
        return jsonArray;
    }

    private void skipWhiteSpace(String json) {
        while (json.charAt(index) == ' ') {
            index++;
        }
    }

    private JsonValue parseElement(String json) throws Exception {
        skipWhiteSpace(json);
        // If the length pointer is still less than the
        if (index < json.length()) {
            if (json.charAt(index) == '{') {
                return parseObject(json);
            } else if (json.charAt(index) == '[') {
                return parseArray(json);
            } else if (Character.isDigit(json.charAt(index))) {
                return parseNumber(json);
            } else if (json.charAt(index) == '"') {
                return parseString(json);
            } else if (json.charAt(index) == 't' || json.charAt(index) == 'f') {
                return parseBoolean(json);
            } else if (json.charAt(index) == 'n') {
                return null;
            }
        }

        throw new Exception("Invalid Type");
    }

    private JsonValue parseString(String json) {
        StringBuilder sb = new StringBuilder();
        index++;
        while (index < json.length() && json.charAt(index) != '"') {
            sb.append(json.charAt(index));
            index++;
        }
        index++;
        return new JsonString(sb.toString());
    }

    private JsonValue parseNumber(String json) {
        StringBuilder sb = new StringBuilder();
        boolean isFloat = false;
        while (index < json.length() && (Character.isDigit(json.charAt(index)) || json.charAt(index) == '.')) {
            if (json.charAt(index) == '.') {
                isFloat = true;
            }
            sb.append(json.charAt(index));
            index++;
        }
        String number = sb.toString();

        if (isFloat) {
            return new JsonNumber(Float.parseFloat(number));
        } else {
            return new JsonNumber(Integer.parseInt(number));
        }

    }

    private JsonValue parseBoolean(String json) {
        if (json.charAt(index) == 't') {
            index += 4;
            return new JsonBoolean(true);
        } else {
            index += 5;
            return new JsonBoolean(false);
        }
    }

}
