package com.sharetreats.assignment.service.pachinko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PachinkoMachineTest {
    String[] products = {
            "CIDER, A, 2023-05-22T01:00:00+09:00",
            "COLA, A, 2023-05-22T02:00:00+09:00",
            "TORE, A, 2023-05-22T03:00:00+09:00",
            "ELO, A, 2023-05-22T04:00:00+09:00",
            "PEPSI, A, 2023-05-22T05:00:00+09:00",
            "PIZZA, B, 2023-05-23T01:00:00+09:00",
            "POTATO, B, 2023-05-23T02:00:00+09:00",
            "GAGA, B, 2023-05-23T03:00:00+09:00",
            "KA, B, 2023-05-23T01:04:00+09:00",
            "CHICKEN, B, 2023-05-23T05:00:00+09:00"
    };

    private static final Product NOTHING_PRODUCT = new Product("NOTHING", Rank.NOTHING, OffsetDateTime.now().withNano(0));
    private PachinkoMachine pachinkoMachine;
    @BeforeEach
    void init() {
        this.pachinkoMachine = new PachinkoMachine(products);
    }

    @DisplayName("유통기한을 모두 지난 테스트")
    @Test
    void overAllExpirationDateTest() {
        String time = "2023-05-24T01:00:32+09:00";
        for (int i = 0; i < 100; ++i) {
            assertEquals(Rank.NOTHING, this.pachinkoMachine.draw(OffsetDateTime.parse(time), true).getRank());
        }
    }

    @DisplayName("A등급 유통기한을 모두 지난 테스트")
    @Test
    void overAExpirationDateTest() {
        String time = "2023-05-23T00:00:00+09:00";
        for (int i = 0; i < 100; ++i) {
            assertNotEquals(Rank.A, this.pachinkoMachine.draw(OffsetDateTime.parse(time), true).getRank());
        }
    }

    @DisplayName("B등급 제외한 뽑기 테스트")
    @Test
    void drawWithoutB() {
        String time = "2023-05-20T00:00:00+09:00";
        for (int i = 0; i < 100; ++i) {
            assertNotEquals(Rank.B, this.pachinkoMachine.draw(OffsetDateTime.parse(time), false).getRank());
        }
    }

    @DisplayName("뽑기 상품이 계속 바뀌는지 확인 테스트")
    @Test
    void changeWinProduct() {
        String time = "2023-05-20T00:00:00+09:00";
        Product productA = null;
        Product productB = null;
        for (int i = 0; i < 5000; ++i) {
            Product product = this.pachinkoMachine.draw(OffsetDateTime.parse(time), true);
            assertFalse(product.equals(productA) || product.equals(productB));

            if (product.getRank().equals(Rank.A)) {
                productA = product;
            } else if (product.getRank().equals(Rank.B)) {
                productB = product;
            }
        }
    }
}