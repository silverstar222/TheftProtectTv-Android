package com.esp.theftprotecttv.util;

public class Log {
    /* Logging and Console */
    public static boolean DO_SOP = false;

    public static void print(String mesg) {
        if (Log.DO_SOP) {
            System.out.println(mesg);
        }
    }

    public static void print(String title, String mesg) {
        if (Log.DO_SOP) {
            System.out.println(title + " :: " + mesg);
        }
    }

    public static void error(String title, Exception e) {
        if (Log.DO_SOP) {
            System.out.println("=========================" + title + "=========================");
            e.printStackTrace();
        }
    }

    public static void errot(Exception e) {
        if (Log.DO_SOP) {
            e.printStackTrace();
        }
    }
}