import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static MessageDigest md;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        md = MessageDigest.getInstance("SHA-256");

        System.out.print("1. Verify Data\n2. Add a Block\nOption: ");
        switch (sc.nextInt()) {
            case 1 -> verifyLedger();
            case 2 -> addNewBlock();
            default -> System.out.println("Please enter a valid input!");
        }
    }

    private static void addNewBlock() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nCreating new block");
        for (int i = 0; i < 6; i++) {
            Thread.sleep(500);
            System.out.print(".");
        }
        System.out.println("\n");
        List<Student> students = new ArrayList<>();
        String name, certificate, institute;
        long roll_no;

        System.out.print("Please enter the Institute name: ");
        institute = sc.nextLine();

        System.out.print("How many students data to be added? : ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            sc.nextLine();
            System.out.print("Name: ");
            name = sc.nextLine();
            System.out.print("Roll No: ");
            roll_no = sc.nextInt();
            System.out.print("Date of Birth (dd/MM/yyyy): ");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sc.next());
            System.out.print("File path: ");
            certificate = sc.next();
            File input = new File(certificate);

            students.add(new Student(name, roll_no, date, input));
        }

        Block block = new Block(System.currentTimeMillis(), institute, students);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(block);
        oos.flush();
        byte[] data = bos.toByteArray();
        byte[] digest = md.digest(data);
        block.setHashOfBlock(returnHex(digest));
        block.storeInLedger();
    }

    static String returnHex(byte[] inBytes) {
        String hexString = "";
        for (byte inByte : inBytes) {
            hexString += Integer.toString((inByte & 0xff) + 0x100, 16).substring(1);
        }
        return hexString;
    }

    private static void verifyLedger() throws IOException, ParseException {
        FileReader ledger = new FileReader("/Users/sukesh/IdeaProjects/Blockchain Fraud Detection/src/ledger.json");
        int r = 0;
        String data = "";
        while ((r = ledger.read()) != -1) {
            data += (char) r;
        }

        JSONObject json = (JSONObject) new JSONParser().parse(data);
        String recentHash = String.valueOf(json.get("recentHash"));
        System.out.println("Hash: " + recentHash);
        FetchBlock block = new FetchBlock();
        block.createBlockFromFile((JSONObject) json.get(recentHash));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(block);
        oos.flush();
        byte[] dataArray = bos.toByteArray();
        byte[] digest = md.digest(dataArray);

        if (!returnHex(digest).equals(recentHash)) {
            System.out.println("Hashing: " + returnHex(digest));
            System.out.println("The ledger has been tampered");
        } else {
            System.out.println("Hashing: " + recentHash);
            System.out.println("The data has not been tampered and is intact");
        }
    }
}