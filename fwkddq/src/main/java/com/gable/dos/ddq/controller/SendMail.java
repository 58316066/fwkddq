package com.gable.dos.ddq.controller;


import com.gable.dos.ddq.model.HistoryLists;
import com.gable.dos.ddq.model.HistoryRepo;
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class SendMail {

    // for example, smtp.mailgun.org
    private static final String SMTP_SERVER = "smtp.office365.com";
    private static final String USERNAME = "nantawatm58@nu.ac.th";
    private static final String PASSWORD = "Benz10148";

    private static final String EMAIL_FROM = "nantawatm58@nu.ac.th";
    private static final String EMAIL_TO = "nantawat10148@gmail.com, nantawatm58@email.nu.ac.th";
    private static final String EMAIL_TO_CC = "";

    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP (HTML)";
    private static String EMAIL_TEXT = "Hello Java Mail \n ABC123";

    public void SendMail(HistoryRepo historyRepo) {
//        EMAIL_TEXT = txt;
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        ArrayList<HistoryLists> historyListsArrayList = historyRepo.getHistoryLists();

//        List<String> interviewTimingToFrom1 = Arrays.asList("aa","aaa","aaaa");
//        List<String> interviewTimingToFrom2 = Arrays.asList("bb","bbb","bbbb");
//        List<String> listOfinterviewerName = Arrays.asList("cc","ccc","cccc");

        StringBuilder buf = new StringBuilder();
        buf.append("<html> ")
                .append("<head> <style>\n" +
                        "table { border-collapse: collapse; width: 100%; }\n" +
                        "th, td { text-align: left;  padding: 8px; }\n" +
                        "tr:nth-child(even){background-color: #f2f2f2}\n" +
                        "th {background-color: #4CAF50;color: white;}\n" +
                        "</style> </head>")
                .append("<body>" +
                        "<table  border=\"1\">" +
                        "<tr>" +
                        "<th>Job name</th>" +
                        "<th>start_Time</th>" +
                        "<th>end_Time</th>" +
                        "<th>status</th>" +
                        "<th>exit_Code</th>" +
                        "<th>exit_Message</th>" +
                        "</tr>");
        for (int i = 0; i < historyListsArrayList.size(); i++) {
            buf.append("<tr><td>")
                    .append(historyListsArrayList.get(i).getJob_Name())
                    .append("</td><td>")
                    .append(historyListsArrayList.get(i).getStart_Time())
                    .append("</td><td>")
                    .append(historyListsArrayList.get(i).getEnd_Time())
                    .append("</td><td>")
                    .append(historyListsArrayList.get(i).getStatus())
                    .append("</td><td>")
                    .append(historyListsArrayList.get(i).getExit_Code())
                    .append("</td><td>")
                    .append(historyListsArrayList.get(i).getExit_Message())
                    .append("</td></tr>");
        }
        buf.append("</table>" +
                "</body>" +
                "</html>");
        EMAIL_TEXT = buf.toString();
        try {

            msg.setFrom(new InternetAddress(EMAIL_FROM));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));

            msg.setSubject(EMAIL_SUBJECT);

//             TEXT email
            msg.setText(EMAIL_TEXT);

            // HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));


            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}



