package com.sharetreats.assignment.service.departmentheadcount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public final class Department {
    private static final int DEPARTMENT_COUNT = 1024;
    private static final String DEPTH = "\t";
    private final String name;
    private final int headCount;
    private final HashSet<Department> subDepartmentSets;
    private final ArrayList<Department> subDepartments;

    public Department(final String name, final int headCount) {
        this.name = name;
        this.headCount = headCount;
        this.subDepartments = new ArrayList<>(DEPARTMENT_COUNT);
        this.subDepartmentSets = new HashSet<>(DEPARTMENT_COUNT);
    }

    public String getName() {
        return this.name;
    }

    public List<Department> getSubDepartments() {
        return Collections.unmodifiableList(this.subDepartments);
    }

    public ResultCode addSubDepartment(final Department department) {
        if (this.subDepartmentSets.contains(department)) {
            return ResultCode.ALREADY_ADD;
        }
        this.subDepartmentSets.add(department);
        this.subDepartments.add(department);
        return ResultCode.SUCCESS;
    }

    public boolean hasDepartment(final Department department) {
        if (this.subDepartmentSets.contains(department)) {
            return true;
        }

        for (Department subDepartment : this.subDepartments) {
            if (subDepartment.hasDepartment(department)) {
                return true;
            }
        }
        return false;
    }

    public int getHeadCount() {
        int count = this.headCount;
        for (Department department : this.subDepartments) {
            count += department.getHeadCount();
        }
        return count;
    }

    public void printDepartmentStructure(final int depth) {
        for (Department department : this.subDepartments) {
            System.out.printf("%s%s > %s%s", DEPTH.repeat(depth), this.name, department.getName(), System.lineSeparator());
            department.printDepartmentStructure(depth + 1);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Department)) {
            return false;
        }

        Department other = (Department) object;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}
