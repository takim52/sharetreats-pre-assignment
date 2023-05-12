package com.sharetreats.assignment.app;

import com.sharetreats.assignment.service.departmentheadcount.Company;
import com.sharetreats.assignment.service.departmentheadcount.ResultCode;
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


        System.out.println("조직 인원수 파악 서비스입니다.");
        String[] departments =
                {
                        "HO, 0",
                        "AS, 10",
                        "DEV, 20",
                        "QA, 970",
                        "BK, 0",
                        "GA, 20",
                        "MS, 80",
                };

        String[] structure =
                {
                        "* > HO",
                        "* > BK",
                        "HO > AS",
                        "HO > DEV",
                        "HO > QA",
                        "BK > GA",
                        "BK > MS",
                };

        Company company = new Company("TEST");
        for (String department : departments) {
            String[] split = department.split(",");
            String departmentName = split[0].trim();
            int headCount = Integer.parseInt(split[1].trim());
            ResultCode resultCode = company.addDepartment(departmentName, headCount);
            System.out.printf("부서추가 | 부서명 : %s, 결과 : %s%s", departmentName, resultCode.getMsg(), System.lineSeparator());
        }

        for (String struct : structure) {
            String[] split = struct.split(">");
            String superDepartmentName = split[0].trim();
            String subDepartmentName = split[1].trim();
            ResultCode resultCode = company.addDepartmentRelation(superDepartmentName, subDepartmentName);
            System.out.printf("부서간의 관계 설정 | 상위부서 명 : %s, 하위부서 명 : %s%s", superDepartmentName, subDepartmentName, System.lineSeparator());
            System.out.printf("결과 : %s%s", resultCode.getMsg(), System.lineSeparator());
        }

        company.printHeadCount();
        company.printDepartmentStructure();
    }
}
