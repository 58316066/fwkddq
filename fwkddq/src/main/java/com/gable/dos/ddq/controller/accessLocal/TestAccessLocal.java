//package com.gable.dos.ddq.springbatch.accessLocal;
//
//import javax.sound.midi.Soundbank;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestAccessLocal {
//    List<String> folderName, result;
//
//    public static void main(String[] args) {
//
//        final File folder = new File("D:\\");
//        //"C:\\Test"
//        //"D:\\fwkddq"
//
//        List<String> result = new ArrayList<>();
//        List<String> folderName = new ArrayList<>();
//        List<String> creationTime = new ArrayList<>();
//        System.out.println("\nDirectory Path : \"" + folder + "\"\n...............................................\n");
//
//        search(".*\\.*", folder, result, folderName, creationTime);
//
//        System.out.println(":::::::::::::::::::::::::::::");
//        System.out.println("Number of File = " + result.size() + " Files");
//        System.out.println("Number of Folder = " + folderName.size() + " Folders");
//        System.out.println(":::::::::::::::::::::::::::::");
//////        System.out.println("creationTime = " + creationTime);
////        System.out.println("");
////        for (String s : result) {
////            System.out.println("File Name ::> " + s);
////        }
////
////        System.out.println("");
////        for (String d : creationTime) {
////            System.out.println("creationTime ::> " + d);
////        }
////
////        System.out.println("");
////        for (String f : folderName) {
////            System.out.println("Folder Name ::> " + f);
////        }
//
//
//    }
//
//    public static void search(final String pattern, final File folder, List<String> result, List<String> folderName, List<String> creationTime) {
//
////        String tab = "|_";
//        for (final File f : folder.listFiles()) {
//
//            if (f.isDirectory()) {
////                folderName.add(f.getAbsolutePath());
//                System.out.println("Folder : " + f.getName());
//                System.out.print("|_ ");
//                search(pattern, f, result, folderName, creationTime);
//            }
//
//
//            if (f.isFile()) {
//                if (f.getName().matches(pattern)) {
//
//                    Path file = Paths.get(f.getAbsolutePath());
//                    try {
//                        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//                        System.out.println("File : " + f.getName());
//                        System.out.print("  |__ ");
//                        System.out.println("Size : " + f.length() + " bytes ");
//                        System.out.println("  |__ createTime : " + attr.creationTime());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
////                    result.add(f.getAbsolutePath() + " {Size ==> " + f.length() + " bytes }");
//                }
//            }
//
//        }
//        System.out.println("");
//    }
//}
