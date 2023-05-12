package com.sharetreats.assignment.service.pachinko;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public final class PachinkoMachine {
    private static final int PRODUCT_SIZE = 1024;
    private static final String DELIMITER = ",";
    private static final Product NOTHING_PRODUCT = new Product("NOTHING", Rank.NOTHING, OffsetDateTime.now().withNano(0));
    private final HashMap<Rank, ArrayList<Product>> productByRank;
    private final HashMap<Rank, Integer> productIndex;

    public PachinkoMachine(String[] products) {
        int rankLength = Rank.values().length;
        this.productByRank = new HashMap<>(rankLength);
        this.productIndex = new HashMap<>(rankLength);
        for (Rank r : Rank.values()) {
            this.productByRank.put(r, new ArrayList<>(PRODUCT_SIZE));
            this.productIndex.put(r, 0);
        }

        for (String temp : products) {
            String[] split = temp.split(DELIMITER);
            String productName = split[0].replaceAll(" ", "").toUpperCase();
            String rankName = split[1].replaceAll(" ", "");
            String dateTimeStr = split[2].replaceAll(" ", "");

            Rank rank = null;
            for (Rank r : Rank.values()) {
                if (r.getName().equalsIgnoreCase(rankName)) {
                    rank = r;
                    break;
                }
            }
            assert rank != null : "해당 등급은 없는 등급입니다. 등급을 확인 하세요.";

            OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeStr);
            this.productByRank.get(rank).add(new Product(productName, rank, dateTime));
        }
    }

    public PachinkoMachine(final File file) {
        int rankLength = Rank.values().length;
        this.productByRank = new HashMap<>(rankLength);
        this.productIndex = new HashMap<>(rankLength);
        for (Rank r : Rank.values()) {
            this.productByRank.put(r, new ArrayList<>(PRODUCT_SIZE));
        }

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String temp = scan.nextLine();

                String[] split = temp.split(DELIMITER);
                String productName = split[0].replaceAll(" ", "").toUpperCase();
                String rankName = split[1].replaceAll(" ", "");
                String dateTimeStr = split[2].replaceAll(" ", "");

                Rank rank = null;
                for (Rank r : Rank.values()) {
                    if (r.getName().equalsIgnoreCase(rankName)) {
                        rank = r;
                        break;
                    }
                }
                assert rank != null : "해당 등급은 없는 등급입니다. 등급을 확인 하세요.";

                OffsetDateTime dateTime = OffsetDateTime.parse(dateTimeStr);
                this.productByRank.get(rank).add(new Product(productName, rank, dateTime));
            }
        } catch (FileNotFoundException e) {
            // scanner 관련
            System.out.println("파일을 찾지 못했습니다.");
        }
    }

    public Product draw(final OffsetDateTime drawTime, final boolean withB) {
        assert this.productByRank.get(Rank.A).size() >= 2 && this.productByRank.get(Rank.B).size() >= 2
                : "A, B 상품은 최소 2종류 이상이어야 합니다.";

        // A 등급부터 확인
        Product product = drawProduct(Rank.A, drawTime);
        if (product.getRank().equals(Rank.A)) {
            return product;
        }

        product = drawProduct(Rank.B, drawTime);

        // B 등급 확인
        if (withB && product.getRank().equals(Rank.B)) {
            return product;
        }

        System.out.printf("뽑기 결과 | 꽝입니다.%s", System.lineSeparator());
        return NOTHING_PRODUCT;
    }

    private Product drawProduct(final Rank rank, final OffsetDateTime drawTime) {
        if (isWin(rank)) {
            int startIndex = this.productIndex.get(rank);
            ArrayList<Product> products = this.productByRank.get(rank);

            // 유통기한이 남아있을 때
            Product product = products.get(startIndex);
            //System.out.println(drawTime.compareTo(product.getExpirationDate()));
            if (drawTime.compareTo(product.getExpirationDate()) <= 0) {
                System.out.printf("뽑기 결과 | 상품 : %s, 등급 : %s%s", product.getName(), product.getRank().getName(), System.lineSeparator());
                this.productIndex.put(rank, (startIndex + 1) % products.size());
                return products.get(startIndex);
            }

            int index = (startIndex + 1) % products.size();
            while (index != startIndex) {
                product = products.get(index);
                //System.out.println(drawTime.compareTo(product.getExpirationDate()));
                if (drawTime.compareTo(product.getExpirationDate()) <= 0) {
                    System.out.printf("뽑기 결과 | 상품 : %s, 등급 : %s%s", product.getName(), product.getRank().getName(), System.lineSeparator());
                    this.productIndex.put(rank, (index + 1) % products.size());
                    return products.get(index);
                }
                index = (index + 1) % products.size();
            }
        }
        return NOTHING_PRODUCT;
    }

    private boolean isWin(Rank rank) {
        Random random = new Random(System.nanoTime());
        int percentage = (int) (rank.getPercentage() * 100.0f);
        int value = random.nextInt(100);
        return value < percentage;
    }
}
