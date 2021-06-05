import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;

public class Student implements Serializable {
    private String name;
    private long roll_no;
    private Date date_of_birth;
    private File certificate;

    public Student(String name, long roll_no, Date date_of_birth, File certificate) {
        this.name = name;
        this.roll_no = roll_no;
        this.date_of_birth = date_of_birth;
        this.certificate = certificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(long roll_no) {
        this.roll_no = roll_no;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public File getCertificate() {
        return certificate;
    }

    public void setCertificate(File certificate) {
        this.certificate = certificate;
    }

    public String getCertificateHash() throws Exception {

        BufferedImage buffImg = ImageIO.read(certificate);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        byte[] hash = md.digest();
        return Main.returnHex(hash);
    }
}
