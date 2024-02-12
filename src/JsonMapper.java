//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Stack;
//
//public class JsonMapper {
//
//    private int index = 0;
//
//    public Object readJson(String json) throws Exception {
//        if (json == null) {
//            throw new Exception("Invalid Input");
//        }
//        if (json.length() == 0) {
//            throw new Exception("Empty String");
//        }
//        json = json.trim();
//        index = 0;
//        return parseElement(json);
//        // Check if its complete
//        // Start in the middle of a key
//        // Look for the next Key value :
////        Object root;
////        if (json.charAt(0) != '{') {
////            while (index < json.length()) {
////                if (json.charAt(index) == ':' || json.charAt(index) == '{') {
////                    break;
////                }
////                index++;
////            }
////            if (json.charAt(index) == ':') {
////                int parenthesis = 0;
////                while (index > 0) {
////                    if (json.charAt(index) == '"') {
////                        parenthesis++;
////                    }
////                    if (parenthesis == 2) {
////                        json = "{" + json.substring(index);
////                        break;
////                    }
////                    index--;
////                }
////            }
////        }
////        root = parseElement(json);
//
////        while (index < json.length() - 1) {
////            if (json.charAt(index) == '}') {
////                skipWhiteSpace(json);
////                if (index < json.length() && json.charAt(index) == ',') {
////                    skipWhiteSpace(json);
////                    if (index < json.length()) {
////                        if (json.charAt(index) == '{') {
////                            List<Object> array = parseArray(json);
////                            array.add(root);
////                            root = array;
////                        } else {
////                            HashMap<String, Object> obj = parseObject(json);
////                            obj.put("truncated", root);
////                            root = obj;
////                        }
////                        while (index < json.length() && json.charAt(index) != ',') {
////                            // Move to next start
////                            index++;
////                        }
////                    }
////                }
////                HashMap<String, Object> newRoot = parseObject(json);
////                newRoot.put("truncated", root);
////                root = newRoot;
////            }
////            index++;
////        }
////
////        return root;
//    }
//
//    private HashMap<String, Object> parseObject(String json) throws Exception {
//        HashMap<String, Object> jsonObject = new HashMap<>();
//        index++;
//        while (index < json.length()) {
//            skipWhiteSpace(json);
//            if ( index < json.length() &&json.charAt(index) == '}') {
//                index++;
//                break;
//            }
//            if (index < json.length() && json.charAt(index) == '"') {
//                index++;
//                StringBuilder key = new StringBuilder();
//                while (index < json.length() && json.charAt(index) != '"') {
//                    key.append(json.charAt(index));
//                    index++;
//                }
//
//                while (index < json.length() && json.charAt(index) != ':') {
//                    index++;
//                }
//                index++;
//                if (index < json.length()) {
//                    Object value = parseElement(json);
//                    jsonObject.put(key.toString(), value);
//                } else {
//                    jsonObject.put(key.toString(), null);
//                }
//
//            } else {
//                index++;
//            }
//        }
//        return jsonObject;
//    }
//
//    private List<Object> parseArray(String json) throws Exception {
//        List<Object> array = new ArrayList<>();
//        index++;
//        boolean exit = false;
//        while (index < json.length()) {
//            Object value = parseElement(json);
//            array.add(value);
//            while (index < json.length() && json.charAt(index) != ',') {
//                if (json.charAt(index) == ']') {
//                    index++;
//                    exit = true;
//                    break;
//                }
//                index++;
//            }
//            if (exit) {
//                break;
//            }
//            index++;
//        }
//        return array;
//    }
//
//    private void skipWhiteSpace(String json) {
//        while (json.charAt(index) == ' ') {
//            index++;
//        }
//    }
//
//    private Object parseElement(String json) throws Exception {
//        skipWhiteSpace(json);
//        if (index < json.length()) {
//            if (json.charAt(index) == '{') {
//                return parseObject(json);
//            } else if (json.charAt(index) == '[') {
//                return parseArray(json);
//            } else if (Character.isDigit(json.charAt(index))) {
//                return parseNumber(json);
//            } else if (json.charAt(index) == '"') {
//                return parseString(json);
//            } else if (json.charAt(index) == 't' || json.charAt(index) == 'f') {
//                return parseBoolean(json);
//            } else if (json.charAt(index) == 'n') {
//                return null;
//            }
//            throw new Exception("Invalid Type");
//        }
//
//        return null;
//    }
//
//    private String parseString(String json) {
//        StringBuilder sb = new StringBuilder();
//        index++;
//        while (index < json.length() && json.charAt(index) != '"') {
//            sb.append(json.charAt(index));
//            index++;
//        }
//        index++;
//        return sb.toString();
//    }
//
//    private Number parseNumber(String json) {
//        StringBuilder sb = new StringBuilder();
//        boolean isFloat = false;
//        while (index < json.length() && (Character.isDigit(json.charAt(index)) || json.charAt(index) == '.')) {
//            if (json.charAt(index) == '.') {
//                isFloat = true;
//            }
//            sb.append(json.charAt(index));
//            index++;
//        }
//        String number = sb.toString();
//        if (isFloat) {
//            return Float.parseFloat(number);
//        } else {
//            return Integer.parseInt(number);
//        }
//    }
//
//    private Boolean parseBoolean(String json) {
//        if (json.charAt(index) == 't') {
//            index += 4;
//            return true;
//        } else {
//            index += 5;
//            return false;
//        }
//    }
//
//}
