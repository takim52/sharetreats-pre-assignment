package com.sharetreats.assignment.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public final class ProductExchange {
    private static final int PRODUCE_COUNT = 1024;
    private static final String COMMAND_CHECK = "CHECK";
    private static final String COMMAND_HELP = "HELP";
    private static final String COMMAND_CLAIM = "CLAIM";
    private final HashSet<String> productCode;
    private final HashMap<String, String> productExchangeShop;

    public ProductExchange(final String[] products) {
        this.productCode = new HashSet<>(products.length);
        this.productExchangeShop = new HashMap<>(products.length);

        for (String temp : products) {
            String product = temp.replaceAll(" ", "");
            assert validProductCode(product) : "상품 코드는 숫자만 올 수 있습니다.";
            this.productCode.add(product);
            //System.out.println(String.format("상품등록 : %s", product));
        }
    }

    public ProductExchange(final File file) {
        this.productCode = new HashSet<>(PRODUCE_COUNT);
        this.productExchangeShop = new HashMap<>(PRODUCE_COUNT);

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String product = scan.nextLine().replaceAll(" ", "");
                assert validProductCode(product) : "상품 코드는 숫자만 올 수 있습니다.";
                this.productCode.add(product.trim());
                //System.out.println(String.format("상품등록 : %s", product));
            }
        } catch (FileNotFoundException e) {
            // scanner 관련
            System.out.println("파일을 찾지 못했습니다.");
            System.out.println("상품이 하나도 등록되어 있지 않습니다.");
        }
    }

    public int service(final String userInput) {
        String inputWithoutSpace = userInput.replaceAll(" ", "");

        if (isHelp(inputWithoutSpace)) {
            System.out.println(ProductExchangeStatus.HELP.getDescription());
            return ProductExchangeStatus.HELP.getCode();
        } else if (isCheck(inputWithoutSpace)) {
            return checkProduct(inputWithoutSpace.substring(5));
        } else if (isClaim(inputWithoutSpace)) {
            return exchangeProduct(inputWithoutSpace.substring(5));
        }

        System.out.println(ProductExchangeStatus.BAD_COMMAND.getDescription());
        return ProductExchangeStatus.BAD_COMMAND.getCode();
    }

    private int checkProduct(final String product) {
        if (product.length() != 9 || invalidProductCode(product)) {
            System.out.println(ProductExchangeStatus.INVALID_PRODUCT_CODE.getDescription());
            System.out.println(String.format("입력하신 상품 코드 [%s]", product));
            return ProductExchangeStatus.INVALID_PRODUCT_CODE.getCode();
        }

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

    private int exchangeProduct(final String input) {
        if (input.length() != 15) {
            System.out.println(ProductExchangeStatus.INVALID_PARAMETER.getDescription());
            //System.out.println(String.format("입력하신 값 [%s]", input));
            return ProductExchangeStatus.INVALID_PARAMETER.getCode();
        }

        String shop = input.substring(0, 6);
        String product = input.substring(6);
        if (invalidShopCode(shop)) {
            System.out.println(ProductExchangeStatus.INVALID_SHOP_CODE.getDescription());
            return ProductExchangeStatus.INVALID_SHOP_CODE.getCode();
        }

        if (invalidProductCode(product)) {
            System.out.println(ProductExchangeStatus.INVALID_PRODUCT_CODE.getDescription());
            return ProductExchangeStatus.INVALID_PRODUCT_CODE.getCode();
        }


        if (this.productCode.contains(product)) {
            if (this.productExchangeShop.containsKey(product)) {
                System.out.println(String.format("상품 코드 : %s, 상점 코드 : %s, %s", product, this.productExchangeShop.get(product), ProductExchangeStatus.ALREADY_EXCHANGE.getDescription()));
                return ProductExchangeStatus.ALREADY_EXCHANGE.getCode();
            } else {
                this.productExchangeShop.put(product, shop);
                System.out.println(String.format("상점 코드 : %s, 상품 코드 : %s %s", shop, product, ProductExchangeStatus.CHANGE_SUCCESS.getDescription()));
                return ProductExchangeStatus.CHANGE_SUCCESS.getCode();
            }
        }

        System.out.println(ProductExchangeStatus.NO_PRODUCT.getDescription());
        return ProductExchangeStatus.NO_PRODUCT.getCode();
    }

    private boolean invalidShopCode(final String shopCode) {
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

    private boolean invalidProductCode(final String product) {
        for (int i = 0; i < product.length(); ++i) {
            final char check = product.charAt(i);
            if (check < 48 || 57 < check ) {
                return true;
            }
        }

        return false;
    }

    private boolean validProductCode(final String product) {
        for (int i = 0; i < product.length(); ++i) {
            final char check = product.charAt(i);
            if (check < 48 || 57 < check ) {
                return false;
            }
        }

        return true;
    }

    private boolean isHelp(final String input) {
        return 3 < input.length() && input.substring(0, 4).equalsIgnoreCase(COMMAND_HELP);
    }

    private boolean isCheck(final String input) {
        return 4 < input.length() && input.substring(0, 5).equalsIgnoreCase(COMMAND_CHECK);
    }

    private boolean isClaim(final String input) {
        return 4 < input.length() && input.substring(0, 5).equalsIgnoreCase(COMMAND_CLAIM);
    }
}
