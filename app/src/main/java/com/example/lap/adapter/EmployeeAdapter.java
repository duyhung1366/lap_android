package com.example.lap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lap.R;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> employeeList;

    public EmployeeAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_employee, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title_employee);
        TextView sub_title = convertView.findViewById(R.id.sub_title_employee);

        Employee employee = employeeList.get(position);
        if (employee != null) {
            String titleData
                    = employee.getCode() + " - " + employee.getName();
            String SubTitleData =
                    "Chức vụ : " + renderRole(employee.getRole())
                    + "\n Giới tính : " + renderGender(employee.getGender());
            title.setText(titleData);
            sub_title.setText(SubTitleData);
        }

        return convertView;
    }

    private String renderRole(byte role) {
        if(role == 1) {
            return "Trưởng phòng";
        } else if(role == 2) {
            return "Phó phòng";
        } else if (role == 3) {
            return "Nhân viên";
        }
        return "chưa cập nhật";
    }

    private String renderGender(byte gender) {
        if(gender == 1) {
            return "Nam";
        } else if (gender == 2){
            return "Nữ";
        }
        return "Khác";
    }

}
