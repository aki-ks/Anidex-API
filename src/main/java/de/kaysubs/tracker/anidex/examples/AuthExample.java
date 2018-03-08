package de.kaysubs.tracker.anidex.examples;

import de.kaysubs.tracker.anidex.AnidexApi;
import de.kaysubs.tracker.anidex.AnidexAuthApi;

import java.util.Scanner;

public class AuthExample {

    public static void main(String[] args) {
        login();
    }

    public static AnidexAuthApi login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("username: ");
        String name = sc.next();
        System.out.print("password: ");
        String password = sc.next();

        return AnidexApi.getInstance().login(name, password);
    }

}
