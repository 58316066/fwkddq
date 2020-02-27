package com.gable.dos.compare;

import java.awt.Desktop;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.Clock;

import org.apache.commons.text.diff.StringsComparator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import com.gable.dos.compare.process.FileCommandsVisitor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;


public class FileDiff {
//    public static boolean checkStatusCompare = true;

    public void html() throws IOException {
        File f1 = new File("./actual_201408_trip_data_csv1.csv");
        File f2 = new File("./actual_201408_trip_data_csv2.csv");


        JSONObject j1 = new JSONObject();
        JSONObject j2 = new JSONObject();

        j1.put("a","aaa");
        j1.put("b","bbb");

        j2.put("a","aaa");
        j2.put("b","bbbb");
        FileInputStream a = new FileInputStream(f1);
        FileInputStream b = new FileInputStream(f2);


        InputStream inputStream1 = new ByteArrayInputStream(j1.toString().getBytes());
        InputStream inputStream2 = new ByteArrayInputStream(j2.toString().getBytes());

        LineIterator file1 = IOUtils.lineIterator(a, "utf-8");
        LineIterator file2 = IOUtils.lineIterator(b, "utf-8");


        // Initialize visitor.
        FileCommandsVisitor fileCommandsVisitor = new FileCommandsVisitor();

        // Read file line by line so that comparison can be done line by line.
        while (file1.hasNext() || file2.hasNext()) {
            /*
             * In case both files have different number of lines, fill in with empty
             * strings. Also append newline char at end so next line comparison moves to
             * next line.
             */
            String left = (file1.hasNext() ? file1.nextLine() : "") + "\n";
            String right = (file2.hasNext() ? file2.nextLine() : "") + "\n";

            // Prepare diff comparator with lines from both files.
            StringsComparator comparator = new StringsComparator(left, right);

            if (comparator.getScript().getLCSLength() > (Integer.max(left.length(), right.length()) * 0.4)) {

                /*
                 * If both lines have atleast 40% commonality then only compare with each other
                 * so that they are aligned with each other in final diff HTML.
                 */
                comparator.getScript().visit(fileCommandsVisitor);

            } else {
//				checkStatusCompare = false;
                /*
                 * If both lines do not have 40% commanlity then compare each with empty line so
                 * that they are not aligned to each other in final diff instead they show up on
                 * separate lines.
                 */
                StringsComparator leftComparator = new StringsComparator(left, "\n");
                leftComparator.getScript().visit(fileCommandsVisitor);
                StringsComparator rightComparator = new StringsComparator("\n", right);
                rightComparator.getScript().visit(fileCommandsVisitor);

            }
        }

        fileCommandsVisitor.generateHTML();


		String url ="C:\\Users\\Admin\\Desktop\\finalDiff.html";

//		String url ="D:\\fwkddq\\fwkddq\\src\\main\\java\\com\\gable\\dos\\compare\\file\\finalDiff.html";
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

//		if(Desktop.isDesktopSupported()){
//            Desktop desktop = Desktop.getDesktop();
//            try {
//                desktop.browse(new URI(url));
//            } catch (IOException | URISyntaxException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }else{
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec("xdg-open " + url);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
    }
}
