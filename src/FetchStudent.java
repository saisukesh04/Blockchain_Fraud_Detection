import java.io.Serializable;

public class FetchStudent implements Serializable {

    private String name, dob, certificate;
    private long roll_no;

    public FetchStudent() {
    }

    public FetchStudent(String name, String dob, String certificate, long roll_no) {
        this.name = name;
        this.dob = dob;
        this.certificate = certificate;
        this.roll_no = roll_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public long getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(long roll_no) {
        this.roll_no = roll_no;
    }
}