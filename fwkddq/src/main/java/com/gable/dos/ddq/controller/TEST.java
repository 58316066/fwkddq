package com.gable.dos.ddq.controller;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class B {
    A4 obj;

    B(A4 obj) {
        this.obj = obj;
    }

    void display() {
        System.out.println(obj.data + "name = " + obj.name);//using data member of A4 class
    }
}

class A4 {
    int data = 10;
    String name = "Benz";

    A4() {
        B b = new B(this);
        b.display();
    }

    public static void main(String args[]) {
        A4 a = new A4();
    }
}


public class TEST {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String logLine = ".............................................\n";

        int a = 13 / 2;
        System.out.println(a);


        //
//        int i, m = 0, flag = 0;
//        int n = 6;//it is the number to be checked
//        m = n / 2;
//        System.out.println("/ = " + m);
//        if (n == 0 || n == 1) {
//            System.out.println(n + " is not prime number");
//        } else {
//            for (i = 2; i <= m; i++) {
//                if (n % i == 0) {
//                    System.out.println(n + " is not prime number");
//                    flag = 1;
//                    break;
//                }
//            }
//            if (flag == 0) {
//                System.out.println(n + " is prime number");
//            }
//        }//end of else

//        int input = 10;
//        int min = 0;
//        int max = 1;
//
//        System.out.print(min + " " + max + " ");
//        for (int i = 2; i < input; i++) {
//            int sum = min + max;
//            System.out.print(sum + " ");
//            min = max;
//            max = sum;
//
//        }

//        int i = 1;
//        do {
//            System.out.println("do..." + i);
//          i++;
//        }while (i<=10);
//        System.out.println("do't do..");

//        char ch='O';
//        switch(ch)
//        {
//            case 'a':
//                System.out.println("Vowel");
//                break;
//            case 'e':
//                System.out.println("Vowel");
//                break;
//            case 'i':
//                System.out.println("Vowel");
//                break;
//            case 'o':
//                System.out.println("Vowel");
//                break;
//            case 'u':
//                System.out.println("Vowel");
//                break;
//            case 'A':
//                System.out.println("Vowel");
//                break;
//            case 'E':
//                System.out.println("Vowel");
//                break;
//            case 'I':
//                System.out.println("Vowel");
//                break;
//            case 'O':
//                System.out.println("Vowel");
//                break;
//            case 'U':
//                System.out.println("Vowel");
//                break;
//            default:
//                System.out.println("Consonant");
//        }
//        int a=10;
//        int b=55;
//        int c=20;
//        System.out.println(a<b&&a<c);//false && true = false
//        System.out.println(a>b|a>c);//false & true = false

//        int a=10;
//        a+=3;//10+3   //13
//        System.out.println(a);
//        a-=4;//13-4   //9
//        System.out.println(a);
//        a*=2;//9*2  //18
//        System.out.println(a);
//        a/=2;//18/2   //9
//        System.out.println(a);

//        short a = 10;
//        short b = 10;
////a+=b;//a=a+b internally so fine
//        a = (short) (a + b);//Compile time error because 10+10=20 now int
//        System.out.println(a);
//
//       short aa = (short)(a + b);
        //        //For positive number, >> and >>> works same
//        System.out.println(20>>2); // 20/2*2 = 5  /000000000000101
//        System.out.println(20>>>2); // 5 /00000000000101
//        //For negative number, >>> changes parity bit (MSB) to 0
//        System.out.println(-20>>2); // -5
//        System.out.println(-20>>>2); //1073741819
//
//        System.out.println(20>>3); // 20/2*2*2 = 2  /0000000000000010
//        System.out.println(20>>>3); // 2 /000000000000010
//        System.out.println(-20>>3); // -3
//        System.out.println(-20>>>3); //536870909
//        BenzCal();
//        int a = 70;
//        int b = -70;
//        int c = 0;
//        System.out.println("60  = " + a);
//        System.out.println("-60 = " + b);
//        System.out.println("60  = " + Integer.toBinaryString(a));
//        System.out.println("-60 = " + Integer.toBinaryString(b));
//
//        //signed shift
//        c = a >> 2;
//        System.out.println("60 >> 1  = " + c);
//        System.out.println("60 >> 1  = " + Integer.toBinaryString(c));
//
//        //unsigned shift
//        c = a >>> 2;
//        System.out.println("60 >>> 1 = " + c);
//        System.out.println("60 >>> 1 = " + Integer.toBinaryString(c));
//
//        c = b >> 1;
//        System.out.println("-60 >> 1  = " + c);
//        System.out.println("-60 >> 1  = " + Integer.toBinaryString(c));
//
//        c = b >>> 1;
//        System.out.println("-60 >>> 1 = " + c);
//
//        System.out.println("-60 >>> 1 = " + Integer.toBinaryString(c));
//        System.out.println(10<<2);//10*2^2=10*4=40
//        System.out.println(10<<3);//10*2^3=10*8=80
//        System.out.println(20<<2);//20*2^2=20*4=80
//        System.out.println(15<<4);//15*2^4=15*16=240
//        System.out.println(logLine);
//        System.out.println(10>>2);//10/2^2=10/4=2
//        System.out.println(20>>2);//20/2^2=20/4=5
//        System.out.println(20>>3);//20/2^3=20/8=2
//        System.out.println(logLine);
//        //For positive number, >> and >>> works same
//        System.out.println(20>>2); // 5
//        System.out.println(20>>>2); //
//        //For negative number, >>> changes parity bit (MSB) to 0
//        System.out.println(-20>>2); // -5
//        System.out.println(-20>>>2);
//        System.out.println(logLine);


////        int  a = 10 ;
////        int  b = 10 ;
////        System.out.println (a ++ + ++ a); // 10 + 12 = 22
////        System.out.println (b ++ + b ++); // 10 + 11 = 21
//
//        // 10 + 12 = 22
//        // 10 + 11
//
//        int a=-1;
//        int b=-10;
//        boolean c=true;
//        boolean d=false;
//        System.out.println(~a);//-11 (minus of total positive value which starts from 0)
//        System.out.println(~b);//9 (positive of total minus, positive starts from 0)
//        System.out.println(!c);//false (opposite of boolean value)
//        System.out.println(!d);//true
//
//        System.out.println("ss "+ ((20*2)^3));
//
//        int oo = 20*2;
//        int ll = oo*2*2;
//        System.out.println("ssdd"+ll);
//
//        System.out.println(10<<2);//10*2^2=10*4=40
//        System.out.println(10<<3);//10*2^3=10*8=80
//        System.out.println(20<<2);//20*2^2=20*4=80
//        System.out.println(15<<4);//15*2^4=15*16=240
//        System.out.println(20<<3);
//
//        int aaa = 5;
//        double a2 = (float) aaa;
//        System.out.println(a2);
    }

    private static void BenzCal() {
        String logLine = ".............................................\n";
        int กับข้าววันเสาร์ = 40;
        int พับแต่งหน้า = 10;
        int เต้าหู้ = 14;
        int ข้าวต้ม = 5;
        int ไข่เจียว = 30;
        int สแปรช = 13;
        int ผลรวมวันเสาร์ = 0;

//        System.out.println("sss = " + ((75 - 15) - 10));

        System.out.println("กับข้าววันเสาร์ = " + กับข้าววันเสาร์);
        System.out.println("พับแต่งหน้า = " + พับแต่งหน้า);
        System.out.println("เต้าหู้ = " + เต้าหู้);
        System.out.println("ข้าวต้ม = " + ข้าวต้ม);
        System.out.println("ไข่เจียว = " + ไข่เจียว);
        System.out.println("สแปรช = " + สแปรช);
        ผลรวมวันเสาร์ = (กับข้าววันเสาร์ + พับแต่งหน้า + เต้าหู้ + ข้าวต้ม + ไข่เจียว + สแปรช);
        System.out.println("ผลรวมวันเสาร์ = " + ผลรวมวันเสาร์);
        System.out.println(logLine);

        int กับข้าววันอาทิตย์ = 50 + 20;
        int ครีมทาหน้า = 15;
        int ลิปมัน = 39 / 2;
        int กาแฟ = 40;
        int ผลรวมวันอาทิตย์ = (กับข้าววันอาทิตย์ + ครีมทาหน้า + ลิปมัน + กาแฟ);

        System.out.println("กับข้าววันอาทิตย์ = " + กับข้าววันอาทิตย์);
        System.out.println("ครีมทาหน้า = " + ครีมทาหน้า);
        System.out.println("ลิปมัน = " + ลิปมัน);
        System.out.println("กาแฟ = " + กาแฟ);
        System.out.println("ผลรวมวันอาทิตย์ = " + ผลรวมวันอาทิตย์);
        System.out.println(logLine);

        System.out.println("ผลรวมวันอาทิตย์ (เบนซ์) - ผลรวมวันเสาร์ (ณัต) = " + (ผลรวมวันอาทิตย์ - ผลรวมวันเสาร์));

    }
}
