package com.example.guidebooking.util;

public class EmailUtil {

    public static void sendEmail(String to, String subject, String message){

        System.out.println("----- EMAIL SENT -----");

        System.out.println("To: " + to);

        System.out.println("Subject: " + subject);

        System.out.println("Message: " + message);

    }

}