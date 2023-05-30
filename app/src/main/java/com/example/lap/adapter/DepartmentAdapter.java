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

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends BaseAdapter {
    private Context context;
    private List<Department> departmentList;

    public DepartmentAdapter(Context context, List<Department> departmentList) {
        this.context = context;
        this.departmentList = departmentList;
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
                    = department.getCode() + " - " + department.getName()
                    + "\n Trưởng phòng : [chưa có]"
                    + "\n Phó phòng : [chưa có]";
            departmentNameTextView.setText(departmentName);
        }

        return convertView;
    }
}
