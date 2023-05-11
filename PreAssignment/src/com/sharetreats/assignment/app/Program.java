package com.sharetreats.assignment.app;

import com.sharetreats.assignment.service.ProductExchange;

import java.io.File;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String[] products = {
                "123456780", "123456781", "123456782", "123456783", "123456784", "123456785", "123456786", "123456787", "123456788", "123456789",
                "456789120", "456789121", "456789122", "456789123", "456789124", "456789125", "456789126", "456789127", "456789128", "456789129"
        };

        ProductExchange productExchange = new ProductExchange(products);
        File file = new File("C:\\sharetreats-pre-assignment\\PreAssignment\\productCode.txt");
        ProductExchange productExchangeWithFile = new ProductExchange(file);
        Scanner scanner = new Scanner(System.in);
        // 테스트를 위해 100회 제한
        for (int i = 0; i < 100; ++i) {
            String userInput = scanner.nextLine();
            productExchange.service(userInput);
        }


    }
}
