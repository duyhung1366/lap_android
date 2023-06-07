package com.example.lap.util;

import com.example.lap.model.Department;
import com.example.lap.model.Employee;

import java.util.ArrayList;

public class AppUtil {
    public static ArrayList<Employee> dataEmployees = new ArrayList<Employee>();
    public static ArrayList<Department> dataDepartments = new ArrayList<Department>();
    public static ArrayList<Employee> dataEmployeesSelected = new ArrayList<Employee>();

    public static ArrayList<Employee> fillterEmployeeByCodeDepartment(String codeDepartment, ArrayList<Employee> listEmployee) {
        ArrayList<Employee> resultfillter = new ArrayList<>();
        for (Employee employee : listEmployee) {
            if (employee.getCodeDepartment().equals(codeDepartment)) {
                resultfillter.add(employee);
            }
        }

        return resultfillter;
    }

    public static int findIndexEmployeeByCode(String code) {
        for (int i = 0; i < dataEmployeesSelected.size(); i++) {
            if (dataEmployeesSelected.get(i).getCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public static void updateDataEmployees(Employee employeeBefore, Employee employeeUpdate) {
        dataEmployees.set(dataEmployees.indexOf(employeeBefore), employeeUpdate);
    }

}
