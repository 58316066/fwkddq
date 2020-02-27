package com.gable.dos.ddq.controller.itemProcess;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.gable.dos.ddq.controller.SpringConfig.row_number;
import static com.gable.dos.ddq.controller.itemReader.readDataPumpArguments.*;


@Slf4j
public class processDataPump implements ItemProcessor<Object, Object> {
    private List<String> createLine;
    public static List<List<String>> listDataPump = new ArrayList<>();
    private int[] count;
    private int counter;
    private String firstName;
    private String lastName;
    private String days;
    private int random = 0;
    private boolean check = false;
    int length_row_number = 0;

    @Override
    public Object process(Object item) throws Exception {

        listDataPump.add(field_arg_name);

//        length_row_number = String.valueOf(row_number).length();
        count = new int[4];

        for (int loop = 0; loop < row_number; loop++) {
            random = 0;
            createLine = new ArrayList<>();
            for (int i = 0; i < field_arg_name.size(); i++) {
                String o = field_arg_name.get(i);
                check = false;
                for (int j = 0; j < field_config_name.size(); j++) {
                    if (o.contains(field_config_name.get(j))) {
                        check = true;
                        String f = field_config_formats.get(j); // field_config_formats
                        if (f.toUpperCase().contains("SSSS")) {
                            countUp();
                            String format1 = f.replace("SSSS", "" + count[3] + count[2] + count[1] + count[0]);
//                            String counts = "";
//                            for (int c = length_row_number - 1; c >= 0; c--) {
//                                counts = counts.concat("" + count[c]);
//                            }
//                            String format1 = f.replace("SSSS", counts);
                            log.info(format1);
                            createLine.add(format1);
                        } else if (f.toUpperCase().contains("NNNNNN")) {
                            randomIdentifier();
                            String format2 = f.replace("NNNNNN", firstName + " " + lastName);
                            log.info(format2);
                            createLine.add(format2);
                        } else if (f.toUpperCase().contains("FFFFFFFF")) {
                            String format3 = f.replace("FFFFFFFF", firstName);
                            log.info(format3);
                            createLine.add(format3);
                        } else if (f.toUpperCase().contains("LLLLLL")) {
                            String format4 = f.replace("LLLLLL", lastName);
                            log.info(format4);
                            createLine.add(format4);
                        } else if (f.toUpperCase().contains("JJ")) {
                            String format5 = f.replace("JJ", String.valueOf(RandomNumber(10, 20)));
                            log.info(format5);
                            createLine.add(format5);
                        } else if (f.toUpperCase().contains("CC")) {
                            String contract = randomChar();
                            String format6 = f.replace("CC", contract);
                            log.info(format6);
                            createLine.add(format6);
                        } else if (f.toUpperCase().contains("YYYY-MM-DD")) {
                            random++;
                            switch (random) {
                                case 1:
                                    days = randomDates(1957, 1, 1, 1997, 1, 1);
                                    break;
                                case 2:
                                    days = randomDates(2019, 1, 1, 2020, 1, 1);
                                    break;
                                case 3:
                                    days = randomDates(2018, 1, 1, 2018, 12, 31);
                                    break;
                                default:
                                    days = randomDates(1997, 1, 1, 2020, 1, 1);
                                    break;
                            }
                            String format7 = f.replace("YYYY-MM-DD", days);
                            log.info(format7);
                            createLine.add(format7);
                        } else if (f.contains("Single|Marry")) {
                            String[] status = {"Single", "Marry"};
                            String format8 = f.replace("Single|Marry", status[radgrt()]);
                            log.info(format8);
                            createLine.add(format8);
                        } else if (f.contains("Male|Female")) {
                            String[] sex = {"Male", "Female"};
                            String format9 = f.replace("Male|Female", sex[radgrt()]);
                            log.info(format9);
                            createLine.add(format9);
                        } else if (f.toUpperCase().contains("AA")) {
                            String format10 = f.replace("AA", String.valueOf(RandomNumber(27, 50)));
                            log.info(format10);
                            createLine.add(format10);
                        } else {
                            log.info("matching NO == > Default! " + field_config_name.get(field_config_name.size() - 1) + "create format ==> " + field_config_formats.get(field_config_name.size() - 1));
                            String formatDefault = "DDDDDDDD";
                            log.info(formatDefault);
                            createLine.add(formatDefault);
                        }
                    }
                }
                if (!check) {
                    log.info("matching NO == > Default! " + field_config_name.get(field_config_name.size() - 1) + "create format ==> " + field_config_formats.get(field_config_name.size() - 1));
                    String formatDefault = "DDDDDDDD";
                    log.info(formatDefault);
                    createLine.add(formatDefault);
                }
            }
            listDataPump.add(createLine);
        }
        return listDataPump;
    }


    private void countUp() {
        counter++;
//        for (int i = 0; i < length_row_number; i++){
//            count[i] = counter / i % 10;
//        }
        count[0] = counter % 10;
        count[1] = counter / 10 % 10;
        count[2] = counter / 100 % 10;
        count[3] = counter / 1000 % 10;
    }

    private void randomIdentifier() {
        Faker faker = new Faker();
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
    }

    private String randomChar() {
        String chars = "CP";
        Random rnd = new Random();
        return String.valueOf(chars.charAt(rnd.nextInt(chars.length())));
    }

    private String randomDates(int firstYear, int m, int d, int lastYear, int mm, int dd) {
        LocalDate startDate = LocalDate.of(firstYear, m, d); //start date
        long start = startDate.toEpochDay();

        LocalDate endDate = LocalDate.of(lastYear, mm, dd); //end date
        long end = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return String.valueOf(LocalDate.ofEpochDay(randomEpochDay));
    }

    private int radgrt() {
        int x = (int) Math.ceil(Math.random() * 2) - 1;
        return x;
    }

    private int RandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
