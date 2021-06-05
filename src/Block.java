import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Block implements Serializable {

    private String hashOfBlock, prevHashValue;
    private long timestamp;
    private String instituteName;
    private List<Student> students;

    public Block() {
    }

    public Block(long timestamp, String instituteName, List<Student> students) {
        this.timestamp = timestamp;
        this.instituteName = instituteName;
        this.students = students;
    }

    public void storeInLedger() throws Exception {
        FileReader ledger = new FileReader("/Users/sukesh/IdeaProjects/Blockchain Fraud Detection/src/ledger.json");
        int r = 0;
        String data = "";
        while ((r = ledger.read()) != -1) {
            data += (char) r;
        }

        JSONObject json = (JSONObject) new JSONParser().parse(data);
        JSONObject blockData = new JSONObject();
        JSONArray studentArray = new JSONArray();

        String recentHash = String.valueOf(json.get("recentHash"));

        for (Student student : students) {
            JSONObject studentJson = new JSONObject();
            studentJson.put("name", student.getName());
            studentJson.put("dob", student.getDate_of_birth().toString());
            studentJson.put("rollNo", student.getRoll_no());
            studentJson.put("certificate", student.getCertificateHash());
            studentArray.add(studentJson);
        }
        blockData.put("prevHash", recentHash);
        blockData.put("timestamp", timestamp);
        blockData.put("instituteName", instituteName);
        blockData.put("students", studentArray);

        json.put(getHashOfBlock(), blockData);
        json.put("recentHash", getHashOfBlock());

        FileWriter myWriter = new FileWriter("/Users/sukesh/IdeaProjects/Blockchain Fraud Detection/src/ledger.json");
        myWriter.write(json.toString());
        myWriter.close();

        System.out.println("\nAdding new block to the ledger.....");
        Thread.sleep(5000);
        System.out.println("Successfully added the block to the ledger.");
    }

    public String getHashOfBlock() {
        return hashOfBlock;
    }

    public void setHashOfBlock(String hashOfBlock) {
        this.hashOfBlock = hashOfBlock;
    }

    public String getPrevHashValue() {
        return prevHashValue;
    }

    public void setPrevHashValue(String prevHashValue) {
        this.prevHashValue = prevHashValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
