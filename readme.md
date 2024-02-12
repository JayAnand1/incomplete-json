# Incomplete-Json 

## How to Run

In `Main` Create an instance of the `JsonParser`. Convert the JSON you want to parse into a JSON string. Call the 
`readJson()`
function, which will return a `JsonObject`.

```java
JsonParser parser = new JsonParser();'
        
String json = "{"
            + " \"className\": \"year 1\","
            + " \"description\": \"class for year 1\","
            + " \"numberOfStudents\": 5 "
            + "}";'] 
        
JsonObject parsedJson = parser.readJson(json);

```

The `JsonParser` will attempt to parse the entire JSON string. If the string finishes, it will attempt to store whatever data it could find.

If a key is parsed and only a portion of the value, the value will be saved. If only a key was parsed before EOF, the value will be set to null.

## Testing
Some basic testing has been done in the `Test.java` file. A feature I would have liked to add to this is the ability for the parsedJson to return a JSON String equivalent to what was provided, however, this would require maintaining the order of the keys and values in the input JSON string.

## Design
The JsonParser takes in an input JSON string and scans over the string using a global index. As we scan through, we 
identify delimiters `({, [, ", isDigit(), ...etc)` which identify a given data type. Each data type has its own parsing 
function for creating it. For objects/lists, we can use recursion to create nested objects/lists. A recursive call will return once we hit the end of that object/list or we reach EOF.

Each parsing function will also return a custom class which extends the base class `JsonValue`:

- `JsonObject`
- `JsonArray`
- `JsonBoolean`
- `JsonNumber`
- `JsonString`

Although this introduces some overhead, it ensures some level of type safety as I am not relying on `Object` classes. It can also prove useful if we were to implement features such as printing a `JsonObject.toString()`.

The runtime complexity of this algorithm is `O(n)`, we scan through once, creating objects as they are parsed.


## Assumptions

By incomplete JSON, we mean a JSON that starts with `{` but does not need to be complete. If an incomplete JSON is provided that is, for example, the bottom or middle of the file, my program will not work. I tried to come up with an approach to this but could not produce something meaningful in time. It would be interesting to explore.
# incomplete-json
