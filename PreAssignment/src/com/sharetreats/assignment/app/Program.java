package com.sharetreats.assignment.app;

import com.sharetreats.assignment.service.ProductExchange;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String[] products = {
                "123456780", "123456781", "123456782", "123456783", "123456784", "123456785", "123456786", "123456787", "123456788", "123456789",
                "456789120", "456789121", "456789122", "456789123", "456789124", "456789125", "456789126", "456789127", "456789128", "456789129"
        };

        ProductExchange productExchange = new ProductExchange(products);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) {
                break;
            } else {
                productExchange.service(userInput);
            }
        }
    }
}
