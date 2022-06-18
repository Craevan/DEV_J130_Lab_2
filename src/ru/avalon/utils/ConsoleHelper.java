package ru.avalon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleHelper {

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static void writeErrorMessage(String message) {
        System.err.println(message);
    }

    public static <T> void writeMessage(List<T> list) {
        if (list.isEmpty()) {
            System.err.println("Запрос вернул пустой список");
            return;
        }
        list.forEach(System.out::println);
    }

    public static String readLine() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
