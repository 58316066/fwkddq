package com.gable.dos.ddq.library;

import com.gable.dos.ddq.FwkddQApplication;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.gable.dos.ddq.controller.SpringConfig.dateargs;

public class Config_Datetime {
    private String dateProp = "0000-00-00 00:00:00";
    public static Date dateString = new Date();
    //    public static Date dateString = new Date();
    public static String date_string = "0000-00-00 00:00:00";

    private Date date = new Date();
    private Date date2 = new Date();
    private Locale locale = new Locale("en", "EN");
    private TimeZone thaiTimeZone = TimeZone.getTimeZone("Asia/Bangkok");
    private Properties prop = new Properties();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", locale);

    private boolean setCurrentDate = true;

    public Date dateConfig() {
        try (InputStream input = FwkddQApplication.class.getClassLoader().getResourceAsStream("mongoconfig.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }
            prop.load(input);

            dateProp = prop.getProperty("dateSet.config");
            setCurrentDate = Boolean.parseBoolean(prop.getProperty("setCurrent.date"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
//            System.out.println(TimeZone.getTimeZone("GMT"));
            formatter.setTimeZone(thaiTimeZone);

            format.setTimeZone(thaiTimeZone);

            if (setCurrentDate) {

//                date = new Date();
//                dateString = new Date();

                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(new Date()); // sets calendar time/date
//                cal.add(Calendar.DAY_OF_MONTH, 1); // adds one day
                cal.add(Calendar.HOUR_OF_DAY, 7); // adds one hour
                cal.getTime(); // returns new date ob


                date = cal.getTime();
                dateString = cal.getTime();

//                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",locale);
                date_string = format.format(cal.getTime());
                System.out.println("date_string = " + date_string);
//                date = formatter.parse(strDate);
//                dateString = format.parse(dateProp);
//                dateString = format.format(date);

            } else {

//                date = formatter.parse(dateProp);

                String sDate1 = "31/12/1998";
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);


                date = formatter.parse(dateargs);

                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(1);
                dateString = date;
                LocalDateTime localDateTime2 = dateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusDays(1);
//                dateString = format.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                date_string = format.format(date);
                System.out.println("date_string = " + date_string);
            }

//            System.out.println("date = " + date);
//            System.out.println("dateString = " + dateString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date plustDate() {

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 7); // adds one hour
        return cal.getTime(); // returns new date ob
    }
}
