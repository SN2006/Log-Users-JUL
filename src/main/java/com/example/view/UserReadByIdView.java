package com.example.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserReadByIdView {

    private final Scanner INPUT = new Scanner(System.in);

    public Map<String, String> getData() {
        System.out.println("\nREAD BY ID FORM");
        Map<String, String> data = new HashMap<>();
        System.out.print("Input id: ");
        data.put("id", INPUT.nextLine().trim());
        return data;
    }

    public void getOutput(String output) {
        System.out.println("\nDATA BY ID:\n" + output);
    }

}
