package com.sharetreats.assignment.service.departmentheadcount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class Company {
    private static final int DEPARTMENT_COUNT = 1024;
    private static final String TOP_DEPARTMENT = "*";
    private final String name;
    private final HashMap<String, Department> departments;
    private final ArrayList<Department> subDepartments;
    private final HashSet<Department> subDepartmentSets;

    public Company(final String name) {
        this.name = name;
        this.departments = new HashMap<>(DEPARTMENT_COUNT);
        this.subDepartments = new ArrayList<>(DEPARTMENT_COUNT);
        this.subDepartmentSets = new HashSet<>(DEPARTMENT_COUNT);
    }

    public ResultCode addDepartment(final String departmentName, final int headCount) {
        if (invalidDepartmentName(departmentName)) {
            return ResultCode.INVALID_DEPARTMENT_NAME;
        }

        if (this.departments.containsKey(departmentName)) {
            return ResultCode.ALREADY_ADD;
        }

        if (invalidHeadCount(headCount)) {
            return ResultCode.INVALID_HEAD_COUNT;
        }

        Department department = new Department(departmentName, headCount);
        this.departments.put(departmentName, department);
        return ResultCode.SUCCESS;
    }

    public ResultCode addDepartmentRelation(final String superDepartmentName, final String subDepartmentName) {
        // 이름이 잘못된 경우
        if ((!superDepartmentName.equals(TOP_DEPARTMENT) && invalidDepartmentName(superDepartmentName))
                || invalidDepartmentName(subDepartmentName)) {
            return ResultCode.INVALID_DEPARTMENT_NAME;
        }

        // 없는 부서일 경우
        if ((!superDepartmentName.equals(TOP_DEPARTMENT) && !this.departments.containsKey(superDepartmentName)) || !this.departments.containsKey(subDepartmentName)) {
            return ResultCode.NO_DEPARTMENT;
        }

        // 하위 부서는 1개의 상위 부서를 가져야하는데 또 등록하려는 경우
        Department department = this.departments.get(subDepartmentName);
        if (this.subDepartmentSets.contains(department)) {
            return ResultCode.HAS_SUPER_DEPARTMENT;
        }
        for (Department subDepartment : this.subDepartments) {
            if (subDepartment.hasDepartment(department)) {
                return ResultCode.HAS_SUPER_DEPARTMENT;
            }
        }

        // 여기까지 왔다면 등록해도 되는 부서임.
        if (superDepartmentName.equals(TOP_DEPARTMENT)) {
            this.subDepartmentSets.add(department);
            this.subDepartments.add(department);
            return ResultCode.SUCCESS;
        }

        Department superDepartment = this.departments.get(superDepartmentName);
        return superDepartment.addSubDepartment(this.departments.get(subDepartmentName));
    }

    public void printHeadCount() {
        String topDepartmentName = "";
        int headCount = 0;
        System.out.println("[최상위 부서명], [인원]");
        for (Department department : this.subDepartments) {
            topDepartmentName = department.getName();
            headCount = department.getHeadCount();
            System.out.printf("%s, %d%s", topDepartmentName, headCount, System.lineSeparator());
        }
    }

    public void printDepartmentStructure() {
        System.out.println("최상위 부서 표현은 * > A 로 표현합니다.");
        System.out.println("[상위부서] > [하위부서]");
        int depth = 0;
        for (Department department : this.subDepartments) {
            System.out.printf("* > %s%s", department.getName(), System.lineSeparator());
            department.printDepartmentStructure(depth + 1);
        }
    }

    private boolean invalidDepartmentName(final String str) {
        for (char check : str.toCharArray()) {
            if (!Character.isUpperCase(check)) {
                return true;
            }
        }
        return false;
    }

    private boolean invalidHeadCount(final int headCount) {
        return headCount < 0 || 1000 < headCount;
    }
}
