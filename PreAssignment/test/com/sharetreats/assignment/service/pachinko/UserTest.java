package com.sharetreats.assignment.service.pachinko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    String[] products = {
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

    private PachinkoMachine pachinkoMachine;
    private User user;
    @BeforeEach
    void init() {
        this.pachinkoMachine = new PachinkoMachine(products);
        this.user = new User(UUID.randomUUID());
    }


    @DisplayName("올바른 뽑기 테스트")
    @Test
    void validDrawTest() {
        this.user.charge(100L);
        assertEquals(100L, this.user.getAmount());
        this.user.draw(this.pachinkoMachine, 1, OffsetDateTime.now().withNano(0));
        assertEquals(0L, this.user.getAmount());
        this.user.draw(this.pachinkoMachine, 1, OffsetDateTime.now().withNano(0));
        assertEquals(0L, this.user.getAmount());
    }

    @DisplayName("돈 충전이 최대값 보다 클 경우")
    @Test
    void chargeOverflowTest() {
        this.user.charge(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, this.user.getAmount());
        this.user.charge(10L);
        assertEquals(Long.MAX_VALUE, this.user.getAmount());
    }

    @DisplayName("B등급 상품 3개 이하 확인")
    @Test
    void underThreeBTest() {
        long money = 100L * 5000L;
        this.user.charge(money);
        this.user.draw(this.pachinkoMachine, Integer.MAX_VALUE, OffsetDateTime.now().withNano(0));
    }

    @DisplayName("유통기한 확인 테스트")
    @Test
    void expirationDateTest() {
        long money = 100L * 100L;
        this.user.charge(money);
        String time = "2023-05-24T01:00:32+09:00";
        this.user.draw(this.pachinkoMachine, Integer.MAX_VALUE, OffsetDateTime.parse(time));
    }
}