
package utils;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class TestDataUtil {
    public static JSONObject loadTestData(String filePath) throws Exception {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(new FileReader(filePath));
    }
}
