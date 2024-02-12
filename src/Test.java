import dto.*;

import java.util.HashMap;
import java.util.Objects;

public class Test {

    public static void main(String[] args) throws Exception {
        JsonParser parser = new JsonParser();
        String example1 = "{"
                + " \"className\": \"year 1\","
                + " \"description\": \"class for year 1\","
                + " \"numberOfStudents\": 5,"
                + " \"groups\": ["
                + "   {"
                + "     \"group\": 1,"
                + "     \"students\": ["
                + "       {"
                + "         \"name\": \"student A\","
                + "         \"needSupport\": false"
                + "       },"
                + "       {"
                + "         \"name\": \"student B\","
                + "         \"needSupport\": true"
                + "       ";
        JsonObject incompleteObject = parser.readJson(example1);
        assert Objects.equals(((JsonString) incompleteObject.get("className")).getValue(), "year 1");
        assert Objects.equals(((JsonString) incompleteObject.get("description")).getValue(), "class for year 1");
        assert Objects.equals(((JsonNumber) incompleteObject.get("numberOfStudents")).getValue(), 5);
        JsonArray array = (JsonArray) incompleteObject.get("groups");
        assert array.getElements().size() == 1;
        JsonObject firstGroup = (JsonObject) array.get(0);
        assert ((JsonNumber) firstGroup.get("group")).getValue().intValue() == 1;
        JsonArray students = ((JsonArray) firstGroup.get("students"));
        assert students.getElements().size() == 2;
        JsonObject s1 = (JsonObject) students.get(0);
        assert Objects.equals(((JsonString) s1.get("name")).getValue(), "student A");
        assert Objects.equals(((JsonBoolean) s1.get("needSupport")).getValue(), false);
        JsonObject s2 = (JsonObject) students.get(1);
        assert Objects.equals(((JsonString) s2.get("name")).getValue(), "student B");
        assert Objects.equals(((JsonBoolean) s2.get("needSupport")).getValue(), true);

        String example2 = "{"
                + " \"numberOfStudents\" : 10,"
                + " \"group";
        incompleteObject = parser.readJson(example2);
        assert Objects.equals(((JsonNumber) incompleteObject.get("numberOfStudents")).getValue(), 10);
        assert Objects.equals(incompleteObject.getProperties().size(), 2);
        assert Objects.equals(incompleteObject.getProperties().containsKey("group"), true);

        String completeJson = "{"
                + " \"className\": \"year 1\","
                + " \"description\": \"class for year 1\","
                + " \"numberOfStudents\": 5,"
                + " \"groups\": ["
                + "   {"
                + "     \"group\": 1,"
                + "     \"students\": ["
                + "       {"
                + "         \"name\": \"student A\","
                + "         \"needSupport\": false"
                + "       },"
                + "       {"
                + "         \"name\": \"student B\","
                + "         \"needSupport\": true"
                + "       },"
                + "       {"
                + "         \"name\": \"student C\","
                + "         \"needSupport\": false"
                + "       }"
                + "     ]"
                + "   },"
                + "   {"
                + "     \"group\": 2,"
                + "     \"students\": ["
                + "       {"
                + "         \"name\": \"student D\","
                + "         \"needSupport\": false"
                + "       },"
                + "       {"
                + "         \"name\": \"student E\","
                + "         \"needSupport\": true"
                + "       }"
                + "     ]"
                + "   }"
                + " ],"
                + " \"naughtyList\": ["
                + "   {"
                + "     \"name\": \"student E\","
                + "     \"needSupport\": true"
                + "   }"
                + " ]"
                + "}";
        HashMap<String, Boolean> studentSupport = new HashMap<>();
        studentSupport.put("student A", false);
        studentSupport.put("student B", true);
        studentSupport.put("student C", false);
        studentSupport.put("student D", false);
        studentSupport.put("student E", true);
        JsonObject completeObject = parser.readJson(completeJson);
        assert Objects.equals(((JsonString) completeObject.get("className")).getValue(), "year 1");
        assert Objects.equals(((JsonString) completeObject.get("description")).getValue(), "class for year 1");
        assert Objects.equals(((JsonNumber) completeObject.get("numberOfStudents")).getValue(), 5);
        array = (JsonArray) completeObject.get("groups");
        assert array.getElements().size() == 2;

        for (JsonValue value : array.getElements()) {
            JsonArray s = (JsonArray) ((JsonObject) value).get("students");
            for (JsonValue sVal : s.getElements()) {
                JsonObject std = (JsonObject) sVal;
                String name = ((JsonString) std.get("name")).getValue();
                Boolean needSupport = ((JsonBoolean) std.get("needSupport")).getValue();
                assert studentSupport.get(name) == needSupport;
            }
        }

        array = (JsonArray) completeObject.get("naughtyList");
        assert array.getElements().size() == 1;
        JsonObject naughty = (JsonObject) array.get(0);
        assert Objects.equals(((JsonString) naughty.get("name")).getValue(), "student E");
        assert Objects.equals(((JsonBoolean) naughty.get("needSupport")).getValue(), true);






        // Random Incomplete Json with nested Objects
        String nestedJson = "{"
                + " \"class\": {\"name\" : \"Math\", \"teacher\" : {\"name\" : \"James\", \"age\" : 50}},"
                + " \"groups\": [[1,2,[3]],[20,300]]";
        JsonObject nestedObject = parser.readJson(nestedJson);
        JsonObject classObj = (JsonObject) nestedObject.get("class");
        assert Objects.equals(((JsonString) classObj.get("name")).getValue(), "Math");
        JsonObject teacherObj = (JsonObject) classObj.get("teacher");
        assert Objects.equals(((JsonString) teacherObj.get("name")).getValue(), "James");
        assert ((JsonNumber) teacherObj.get("age")).getValue().floatValue() ==  50;

        JsonArray nestedGroups = (JsonArray) nestedObject.get("groups");
        assert nestedGroups.getElements().size() == 2;
        JsonArray g1 = (JsonArray) nestedGroups.get(0);
        assert g1.getElements().size() == 3;
        assert ((JsonNumber) g1.get(0)).getValue().intValue() == 1;
        assert ((JsonNumber) g1.get(1)).getValue().intValue() == 2;

        assert g1.get(2) instanceof JsonArray;
        JsonArray nestedG1 = (JsonArray) g1.get(2);
        assert ((JsonNumber) nestedG1.get(0)).getValue().intValue() == 3;

        JsonArray g2 = (JsonArray) nestedGroups.get(1);
        assert ((JsonNumber) g2.get(0)).getValue().floatValue() == 20;
        assert ((JsonNumber) g2.get(1)).getValue().floatValue() == 300;

        String randomJson = "{"
                + "  \"company\": \"Tech Innovations Inc.\","
                + "  \"founded\": 2010,"
                + "  \"isActive\": true,"
                + "  \"departments\": ["
                + "    {"
                + "      \"name\": \"Research and Development\","
                + "      \"budget\": 500000,"
                + "      \"employees\": ["
                + "        {"
                + "          \"name\": \"John Doe\","
                + "          \"position\": \"Lead Scientist\","
                + "          \"age\": 40,"
                + "          \"isFullTime\": true"
                + "        },"
                + "        {"
                + "          \"name\": \"Jane Smith\","
                + "          \"position\": \"Research Assistant\","
                + "          \"age\": 28,"
                + "          \"isFullTime\": false"
                + "        }"
                + "      ]"
                + "    },"
                + "    {"
                + "      \"name\": \"Marketing\","
                + "      \"budget\": 150000,"
                + "      \"employees\": ["
                + "        {"
                + "          \"name\": \"Emily White\","
                + "          \"position\": \"Marketing Director\","
                + "          \"age\": 35,"
                + "          \"isFullTime\": true"
                + "        },"
                + "        {"
                + "          \"name\": \"Dave Brown\","
                + "          \"position\": \"Copywriter\","
                + "          \"age\": 32,"
                + "          \"isFullTime\": true"
                + "        }"
                + "      ]"
                + "    }"
                + "  ],"
                + "  \"address\": {"
                + "    \"street\": \"123 Tech Rd\","
                + "    \"city\": \"Innovate City\","
                + "    \"zipCode\": \"45678\""
                + "  }"
                + "}";

        JsonObject randomObject = parser.readJson(randomJson);

        String incompleteObj = "{"
                + "  \"address\": {"
                + "    \"street\": \"123 Tech Rd\","
                + "    \"city\": \"Innovate City\","
                + "    \"zipCode\": \"456";
        JsonObject incompleteRandomObj = parser.readJson(incompleteObj);
        JsonObject address = (JsonObject) incompleteRandomObj.get("address");
        assert Objects.equals(((JsonString) address.get("zipCode")).getValue(), "456");

    }
}
