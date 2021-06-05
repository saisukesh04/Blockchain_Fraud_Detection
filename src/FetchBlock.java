import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FetchBlock implements Serializable {

    private String hashOfBlock, prevHashValue;
    private long timestamp;
    private String instituteName;
    private List<FetchStudent> students;

    public FetchBlock() {
    }

    public void createBlockFromFile(JSONObject jsonObject) {
        timestamp = (long) jsonObject.get("timestamp");
        instituteName = (String) jsonObject.get("instituteName");
        prevHashValue = (String) jsonObject.get("prevHash");
        students = new ArrayList<>();

        JSONArray studentArray = (JSONArray) jsonObject.get("students");
        for (Object o : studentArray) {
            JSONObject tempStudent = (JSONObject) o;
            FetchStudent temp = new FetchStudent((String) tempStudent.get("name"),
                    (String) tempStudent.get("dob"),
                    (String) tempStudent.get("certificate"),
                    (long) tempStudent.get("rollNo"));
            students.add(temp);
        }
    }
}
