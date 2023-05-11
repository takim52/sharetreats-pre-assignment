package com.sharetreats.assignment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductExchangeTest {
    String[] products = {
            "123456780", "123456781", "123456782", "123456783", "123456784", "123456785", "123456786", "123456787", "123456788", "123456789",
            "456789120", "456789121", "456789122", "456789123", "456789124", "456789125", "456789126", "456789127", "456789128", "456789129"
    };

    ProductExchange productExchange;

    @BeforeEach
    void init() {
        this.productExchange = new ProductExchange(products);
        this.productExchange.service("claim takima 456789120");
        this.productExchange.service("claim takima 456789121");
        this.productExchange.service("claim takima 456789122");
    }

    @DisplayName("올바른 커맨드 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"check 123456789", "help", "claim abcdef 123456789", "c h e c k  1 2 3 4 5 6 7 8 9", "c l a i m a b c d e f 1 2 3 4 5 6 7 8 9"})
    void validCommandTest(final String userInput) {
        assertEquals(1, this.productExchange.service(userInput));
    }

    @DisplayName("잘못된 커맨드 입력")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"c he1ck", "cla1m", "he1p"})
    void invalidCommandTest(final String userInput) {
        assertEquals(ProductExchangeStatus.BAD_COMMAND.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("올바른 check 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"check 123456789", "check 123456781", "check 1  2 3   4  5  6  7 81"})
    void validCheckProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.CAN_CHANGE.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("없는 상품 check 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"check 133456789", "check 133456779", "check 133556789", "check 133466789"})
    void checkNoProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.NO_PRODUCT.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("허용되지 않는 상품 코드 check 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"check", "check 12345678a", "check 12345678", "check 1234567890"})
    void invalidProductCodeTest(final String userInput) {
        assertEquals(ProductExchangeStatus.INVALID_PRODUCT_CODE.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("이미 교환한 상품 check 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"check 456789120", "check 456789121", "check 456789122"})
    void checkAlreadyExchangeProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.ALREADY_EXCHANGE.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("올바른 claim 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim abcdef 456789127", "claim abcdef 45   6   7   8   9   1   2   8", "claim abcdef    4  5   6   7   8  9  1  2 9"})
    void validExchangeProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.CHANGE_SUCCESS.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("없는 상품 claim 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim abcdef 133456789", "claim abcdef 133456789", "claim abcdef 133556789"})
    void exchangeNoProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.NO_PRODUCT.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("허용되지 않는 상점 코드 또는 상품 코드 exchange 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim bcde 123456789", "claim abcdef 12345678"})
    void invalidExchangeTest(final String userInput) {
        assertEquals(ProductExchangeStatus.INVALID_PARAMETER.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("허용되지 않는 상점 코드 또는 상품 코드 exchange 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim abcde1 123456789", "claim 1bcdef 123456789"})
    void invalidShopCodeExchangeTest(final String userInput) {
        assertEquals(ProductExchangeStatus.INVALID_SHOP_CODE.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("허용되지 않는 상점 코드 또는 상품 코드 exchange 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim abcdef 12A456789", "claim abcdef 12345678a"})
    void invalidProductCodeExchangeTest(final String userInput) {
        assertEquals(ProductExchangeStatus.INVALID_PRODUCT_CODE.getCode(), this.productExchange.service(userInput));
    }

    @DisplayName("이미 교환한 상품 claim 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"claim takima 456789120", "claim takima 456789121", "claim takima 456789122"})
    void claimAlreadyExchangeProductTest(final String userInput) {
        assertEquals(ProductExchangeStatus.ALREADY_EXCHANGE.getCode(), this.productExchange.service(userInput));
    }
}