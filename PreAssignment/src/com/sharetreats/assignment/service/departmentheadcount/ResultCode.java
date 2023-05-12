package com.sharetreats.assignment.service.departmentheadcount;

public enum ResultCode {
    NO_DEPARTMENT("실패. 이 회사에 없는 부서명입니다."),
    ALREADY_ADD("실패. 이미 등록된 부서 입니다."),
    INVALID_DEPARTMENT_NAME("실패. 부서명은 A~Z로만 구성되어 있습니다."),
    INVALID_HEAD_COUNT("실패. 인원수는 0명 이상 1000명 이하 입니다."),
    HAS_SUPER_DEPARTMENT("실패. 해당 부서는 다른 부서의 하위 부서로 등록되어 있습니다."),
    SUCCESS("성공");

    private final String msg;
    private ResultCode(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
