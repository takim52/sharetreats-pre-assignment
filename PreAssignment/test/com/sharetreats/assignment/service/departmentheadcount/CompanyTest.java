package com.sharetreats.assignment.service.departmentheadcount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTest {
    private Company company;
    @BeforeEach
    void init() {
        this.company = new Company("TEST");
        this.company.addDepartment("HO", 0);
        this.company.addDepartment("AS", 10);
        this.company.addDepartment("DEV", 20);
        this.company.addDepartment("QA", 970);
        this.company.addDepartment("BK", 0);
        this.company.addDepartment("GA", 20);
        this.company.addDepartment("MS", 80);
        this.company.addDepartment("MS", 80);

        this.company.addDepartment("TOPA", 10);
        this.company.addDepartment("TOPB", 10);
        this.company.addDepartment("TOPC", 10);
        this.company.addDepartment("TOPD", 10);
        this.company.addDepartment("TESTAA", 10);
        this.company.addDepartment("TESTAB", 10);
        this.company.addDepartment("TESTBA", 10);
        this.company.addDepartment("TESTBB", 10);
        this.company.addDepartment("TESTAAA", 10);
        this.company.addDepartment("TESTAAB", 10);
        this.company.addDepartment("TESTAAAA", 10);

        this.company.addDepartmentRelation("*", "TOPA");
        this.company.addDepartmentRelation("*", "TOPB");
        this.company.addDepartmentRelation("*", "TOPC");
        this.company.addDepartmentRelation("*", "TOPD");

        this.company.addDepartmentRelation("TOPA", "TESTAA");
        this.company.addDepartmentRelation("TOPA", "TESTAB");

        this.company.addDepartmentRelation("TOPB", "TESTBA");
        this.company.addDepartmentRelation("TOPB", "TESTBB");

        this.company.addDepartmentRelation("TESTAA", "TESTAAA");
        this.company.addDepartmentRelation("TESTAA", "TESTAAB");

        this.company.addDepartmentRelation("TESTAAA", "TESTAAAA");
    }

    @DisplayName("올바른 부서명 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"OP, 123", "MN, 100", "OO, 10", "KL, 20", "Z, 50"})
    void validDepartmentNameTest(final String userInput) {
        String[] split = userInput.split(",");
        String departmentName = split[0].trim();
        int headCount = Integer.parseInt(split[1].trim());
        assertEquals(ResultCode.SUCCESS, this.company.addDepartment(departmentName, headCount));
    }

    @DisplayName("잘못된 부서명 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"H1, 123", "ho, 100", "Ho, 10", "123, 0", "HHo, 50"})
    void invalidDepartmentNameTest(final String userInput) {
        String[] split = userInput.split(",");
        String departmentName = split[0].trim();
        int headCount = Integer.parseInt(split[1].trim());
        assertEquals(ResultCode.INVALID_DEPARTMENT_NAME, this.company.addDepartment(departmentName, headCount));
    }

    @DisplayName("잘못된 부서 인원 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"QW, -1", "ER, 1001", "OO, 10000", "KL, -50", "Z, 777777"})
    void invalidHeadCountTest(final String userInput) {
        String[] split = userInput.split(",");
        String departmentName = split[0].trim();
        int headCount = Integer.parseInt(split[1].trim());
        assertEquals(ResultCode.INVALID_HEAD_COUNT, this.company.addDepartment(departmentName, headCount));
    }

    @DisplayName("이미 등록된 부서 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"DEV, 10", "DEV, 0", "DEV, 1000"})
    void doubleAddDepartmentTest(final String userInput) {
        String[] split = userInput.split(",");
        String departmentName = split[0].trim();
        int headCount = Integer.parseInt(split[1].trim());
        assertEquals(ResultCode.ALREADY_ADD, this.company.addDepartment(departmentName, headCount));
    }

    @DisplayName("올바른 부서간의 관계 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"* > HO", "* > BK", "HO > AS", "HO > DEV", "HO > QA", "BK > GA", "BK > MS"})
    void validRelationTest(final String userInput) {
        String[] split = userInput.split(">");
        String superDepartmentName = split[0].trim();
        String subDepartmentName = split[1].trim();
        assertEquals(ResultCode.SUCCESS, company.addDepartmentRelation(superDepartmentName, subDepartmentName));
    }

    @DisplayName("잘못된 부서명 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"Ho > H1", "Qq > a", "* > as", "HO > dev", "H0 > QA", "bK > GA", "BK > mS"})
    void invalidDepartmentNameTest2(final String userInput) {
        String[] split = userInput.split(">");
        String superDepartmentName = split[0].trim();
        String subDepartmentName = split[1].trim();
        assertEquals(ResultCode.INVALID_DEPARTMENT_NAME, this.company.addDepartmentRelation(superDepartmentName, subDepartmentName));
    }

    @DisplayName("없는 부서명 입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"* > AA", "* > BB", "HO > QQ", "PP > DEV", "ZZ > QA", "BK > FF", "MIKI > MS"})
    void noDepartmentNameTest(final String userInput) {
        String[] split = userInput.split(">");
        String superDepartmentName = split[0].trim();
        String subDepartmentName = split[1].trim();
        assertEquals(ResultCode.NO_DEPARTMENT, this.company.addDepartmentRelation(superDepartmentName, subDepartmentName));
    }

    @DisplayName("이미 상위 부서가 있는 부서 재입력 테스트")
    @ParameterizedTest(name = "[{index}번째 테스트 : {0}]")
    @ValueSource(strings = {"* > TOPA", "TOPB > TESTAA", "TOPC > TESTAAA", "TOPD > TESTAAAA"})
    void hasSuperDepartmentTest(final String userInput) {
        String[] split = userInput.split(">");
        String superDepartmentName = split[0].trim();
        String subDepartmentName = split[1].trim();
        assertEquals(ResultCode.HAS_SUPER_DEPARTMENT, this.company.addDepartmentRelation(superDepartmentName, subDepartmentName));
    }
}