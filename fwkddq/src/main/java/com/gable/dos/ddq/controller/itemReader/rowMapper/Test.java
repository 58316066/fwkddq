package com.gable.dos.ddq.controller.itemReader.rowMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws Exception {
//        Test1();
//        FilesCopy();
        runCommand();

    }

    private static void Test1() throws IOException {
        int i = 0, n = 0;

        FileReader fileReader = new FileReader("finalDiff.html");

        System.out.println("FileReader = " + fileReader);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        System.out.println("bufferedReader = " + bufferedReader.readLine().toLowerCase());

        byte c[] = new byte[1]; // ถ้าใช้ Char จะ compile ไม่ผ่าน

        FileInputStream fin2 = new FileInputStream("finalDiff.html");


        char b[] = new char[1];

        FileReader fin = new FileReader("finalDiff.html");

        while ((n = fin.read(b)) != -1) {

            System.out.println(i + " : " + b[0]);

            i = i + 1;

        }

        File file = new File("asdasda");
        BufferedReader br = null;
//        br = new BufferedReader(new FileReader());
        fin.close();

//        PrintWriter pw = new PrintWriter ("201408_trip_data.csv");
//        pw.println("Hi!");
//        pw.close();


        File filename = new File("201408_trip_data.csv");

        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(filename));
        String SprintBy = ",";
        String line = "";
        String lines[];
        while ((line = bufferedReader1.readLine()) != null) {

            lines = line.split(SprintBy);
            System.out.println(lines[0] + "...." + lines[5]);


        }
    }


    private static void FilesCopy() throws IOException {

        File newFile = new File("./newCSV.csv");
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        Files.copy(Paths.get("./201408_trip_data.csv"), fileOutputStream);
    }

    private static void runCommand() throws Exception {
        Process aaa = Runtime.getRuntime().exec("\"C:\\Program Files\\WinMerge\\WinMergeU.exe\" \"D:\\fwkddq\\Docs\\SQL for Monitor\\PostgreSQL\\ProgreSQL_all_field_Table.sql\" \"D:\\fwkddq\\Docs\\SQL for Monitor\\PostgreSQL\\ProgreSQL_list_all_BaseTable.sql\" /maximize");

//        InputStream asd = aaa.getInputStream();
//        System.out.println("lllll : " + asd.read());
//        System.out.println("lllll : " + asd);
//        final Process p = Runtime.getRuntime().exec("java -jar map.jar time.rel test.txt debug");
//
//        new Thread(new Runnable() {
//            public void run() {
//                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                String line = null;
//
//                try {
//                    while ((line = input.readLine()) != null)
//                        System.out.println(line);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        p.waitFor();
    }
}
