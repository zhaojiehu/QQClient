package com.QQServer.Server;

import java.util.Scanner;

public class Key {
    public static String getString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
    public static int getInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static double getDouble(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextDouble();
    }
}
