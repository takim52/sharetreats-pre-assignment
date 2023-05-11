package com.sharetreats.assignment.service;

public enum ProductExchangeStatus {
    BAD_COMMAND(-1, "잘못된 커맨드 입력입니다."),
    ALREADY_EXCHANGE(-2, "이미 교환하여 교환할 수 없습니다."),
    NO_PRODUCT(-3, "해당 상품은 저희 서비스에 없습니다. 상품 코드를 다시 확인 해주세요."),
    INVALID_SHOP_CODE(-4, "올바르지 않은 상점 코드를 입력하였습니다."),
    CAN_CHANGE(1, "상품 교환이 가능합니다."),
    HELP(1, String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
            "커맨드는 CHECK, HELP, CLAIM 입니다.", System.lineSeparator(),
            "상품 코드 : 숫자 0~9로 이루어진 9자리입니다, 상점 코드 : a~z,A~Z로 이루어진 6자리입니다.", System.lineSeparator(),
            "CHECK [상품코드]", System.lineSeparator(),
            "EX) CHECK 132421122", System.lineSeparator(),
            "HELP", System.lineSeparator(),
            "EX) HELP", System.lineSeparator(),
            "CLAIM [상점코드][상품코드]", System.lineSeparator(),
            "EX) CLAIM ABCDEF 123231123", System.lineSeparator()
            )),
    CHANGE_SUCCESS(1, "상품 교환이 완료되었습니다.");

    private final int code;
    private final String description;
    private ProductExchangeStatus(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
