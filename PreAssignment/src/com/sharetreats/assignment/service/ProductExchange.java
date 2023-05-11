package com.sharetreats.assignment.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public final class ProductExchange {
    private static final int PRODUCE_COUNT = 20;
    private static final String COMMAND_CHECK = "CHECK";
    private static final String COMMAND_HELP = "HELP";
    private static final String COMMAND_CLAIM = "CLAIM";
    private final HashSet<String> productCode;
    private final HashMap<String, String> productExchangeShop;

    public ProductExchange(String[] products) {
        this.productCode = new HashSet<>(PRODUCE_COUNT);
        this.productExchangeShop = new HashMap<>(PRODUCE_COUNT);

        for (String temp : products) {
            String product = temp.replaceAll(" ", "");
            this.productCode.add(product);
            //System.out.println(String.format("상품등록 : %s", product));
        }

        assert this.productCode.size() == 20 : "상품 코드 개수는 20개여야 합니다.";
    }

    public ProductExchange(File file) {
        this.productCode = new HashSet<>(PRODUCE_COUNT);
        this.productExchangeShop = new HashMap<>(PRODUCE_COUNT);

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String product = scan.nextLine().replaceAll(" ", "");
                this.productCode.add(product.trim());
                //System.out.println(String.format("상품등록 : %s", product));
            }
        } catch (FileNotFoundException e) {
            // scanner 관련
            System.out.println("파일을 찾지 못했습니다.");
            System.out.println("상품이 하나도 등록되어 있지 않습니다.");
        }

        assert this.productCode.size() == 20 : "상품 코드 개수는 20개여야 합니다.";
    }

    public int service(String userInput) {
        String[] tokens = userInput.split(" ");
        String command = tokens[0].toUpperCase();

        if (command.equals(COMMAND_CHECK)) {
            return checkProduct(tokens);
        } else if (command.equals(COMMAND_HELP)) {
            System.out.println(ProductExchangeStatus.HELP.getDescription());
            return ProductExchangeStatus.HELP.getCode();
        } else if (command.equals(COMMAND_CLAIM)) {
            return exchangeProduct(tokens);
        }

        System.out.println(ProductExchangeStatus.BAD_COMMAND.getDescription());
        return ProductExchangeStatus.BAD_COMMAND.getCode();
    }

    private int checkProduct(String[] tokens) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < tokens.length; ++i) {
            builder.append(tokens[i]);
        }

        String product = builder.toString();
        if (this.productCode.contains(product)) {
            if (this.productExchangeShop.containsKey(product)) {
                System.out.println(String.format("상품 코드 : %s, 상점 코드 : %s, %s", product, this.productExchangeShop.get(product), ProductExchangeStatus.ALREADY_EXCHANGE.getDescription()));
                return ProductExchangeStatus.ALREADY_EXCHANGE.getCode();
            } else {
                System.out.println(String.format("상품 코드 : %s, %s", product, ProductExchangeStatus.CAN_CHANGE.getDescription()));
                return ProductExchangeStatus.CAN_CHANGE.getCode();
            }
        }

        System.out.println(ProductExchangeStatus.NO_PRODUCT.getDescription());
        return ProductExchangeStatus.NO_PRODUCT.getCode();
    }

    private int exchangeProduct(String[] tokens) {
        String shopCode = tokens[1];
        if (isBadShopCode(shopCode)) {
            System.out.println(ProductExchangeStatus.INVALID_SHOP_CODE.getDescription());
            return ProductExchangeStatus.INVALID_SHOP_CODE.getCode();
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < tokens.length; ++i) {
            builder.append(tokens[i]);
        }

        String product = builder.toString();
        if (this.productCode.contains(product)) {
            if (this.productExchangeShop.containsKey(product)) {
                System.out.println(String.format("상품 코드 : %s, 상점 코드 : %s, %s", product, this.productExchangeShop.get(product), ProductExchangeStatus.ALREADY_EXCHANGE.getDescription()));
                return ProductExchangeStatus.ALREADY_EXCHANGE.getCode();
            } else {
                this.productExchangeShop.put(product, shopCode);
                System.out.println(String.format("상점 코드 : %s, 상품 코드 : %s %s", shopCode, product, ProductExchangeStatus.CHANGE_SUCCESS.getDescription()));
                return ProductExchangeStatus.CHANGE_SUCCESS.getCode();
            }
        }

        System.out.println(ProductExchangeStatus.NO_PRODUCT.getDescription());
        return ProductExchangeStatus.NO_PRODUCT.getCode();
    }

    private boolean isBadShopCode(String shopCode) {
        int length = shopCode.length();
        if (length != 6) {
            return true;
        }

        for (int i = 0; i < length; ++i) {
            char check = shopCode.charAt(i);
            // 영문 소문자도 아니면서 영문 대문자도 아니라면
            if (!Character.isLowerCase(check) && !Character.isUpperCase(check)) {
                return true;
            }
        }

        return false;
    }
}
