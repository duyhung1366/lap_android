package com.example.lap.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lap.R;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;
import com.example.lap.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends BaseAdapter {
    private Context context;
    private List<Department> departmentList;
    private ArrayList<Employee> employeeList;

    public DepartmentAdapter(Context context, List<Department> departmentList) {
        this.context = context;
        this.departmentList = departmentList;
    }

    public DepartmentAdapter(Context context, List<Department> departmentList, ArrayList<Employee> employeeList) {
        this.context = context;
        this.departmentList = departmentList;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return departmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return departmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_department, parent, false);
        }

        TextView departmentNameTextView = convertView.findViewById(R.id.departmentNameTextView);

        Department department = departmentList.get(position);
        if (department != null) {
            String departmentName
                    = department.getCode() + " - " + department.getName() + "( có : " + AppUtil.fillterEmployeeByCodeDepartment(department.getCode(), employeeList).size() + " nhân viên )"
                    + "\n Trưởng phòng : [" + getNameByRoleDepartment(department, (byte)1) + "]"
                    + "\n Phó phòng :" + getNameByRoleDepartment(department, (byte)2);
            departmentNameTextView.setText(departmentName);
        }

        return convertView;
    }

    private String getNameByRoleDepartment(Department department, byte role) {
        String code = department.getCode();
        String result = "";
        for(int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            if(employee.getCodeDepartment() == code && employee.getRole() == role) {
                if(i > 0) {
                    result += "\n";
                }
                result += "" + (i + 1) + "." + employee.getName();
            }
        }
        return result.equals("") ? "Chưa có" : result;
    }
}
