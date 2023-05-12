package com.sharetreats.assignment.app;

import com.sharetreats.assignment.service.pachinko.PachinkoMachine;
import com.sharetreats.assignment.service.pachinko.User;
import com.sharetreats.assignment.service.productexchange.ProductExchange;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Scanner;
import java.util.UUID;

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
        // 테스트를 위해 count 회 제한
        int count = 0;
        for (int i = 0; i < count; ++i) {
            String userInput = scanner.nextLine();
            productExchange.service(userInput);
        }


        String[] pachinkoProducts = {
                "CHICKEN, B, 2023-05-23T02:20:19+09:00",
                "CIDER, A, 2023-05-23T02:28:56+09:00",
                "COLA, A, 2023-05-23T01:00:32+09:00",
                "TORE, A, 2023-05-23T01:00:32+09:00",
                "ELO, A, 2023-05-23T01:00:32+09:00",
                "PEPSI, A, 2023-05-23T01:00:32+09:00",
                "PIZZA, B, 2023-05-23T01:00:32+09:00",
                "POTATO, B, 2023-05-23T01:00:32+09:00",
                "GAGA, B, 2023-05-23T01:00:32+09:00",
                "KA, B, 2023-05-23T01:00:32+09:00"
        };
        PachinkoMachine pachinkoMachine = new PachinkoMachine(pachinkoProducts);
        File pachinkoFile = new File("C:\\sharetreats-pre-assignment\\PreAssignment\\pachinko.txt");
        PachinkoMachine pachinkoMachine1 = new PachinkoMachine(pachinkoFile);

        User user = new User(UUID.randomUUID());
        user.charge(100000L);

        user.draw(pachinkoMachine, 1000, OffsetDateTime.now().withNano(0));
    }
}
