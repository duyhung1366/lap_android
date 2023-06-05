package com.example.lap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lap.adapter.EmployeeAdapter;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;
import com.example.lap.util.AppUtil;

import java.util.ArrayList;

public class ListEmployeeActivity extends AppCompatActivity {

    private ArrayList<Employee> dataEmployees;

    private EmployeeAdapter adapterEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        // element
        Button btn_back = findViewById(R.id.back_home);
        ListView listView_employees = findViewById(R.id.list_employee);
        dataEmployees = new ArrayList<Employee>();
        adapterEmployee = new EmployeeAdapter(this, dataEmployees);

        listView_employees.setAdapter(adapterEmployee);

        if(AppUtil.dataEmployeesByCodeDepartment.size() > 0) {
            for (int i = 0; i < AppUtil.dataEmployeesByCodeDepartment.size(); i++) {
                Log.d("TAG", "Element at index " + i + ": " + AppUtil.dataEmployeesByCodeDepartment.get(i).getName());
                this.dataEmployees.add(AppUtil.dataEmployeesByCodeDepartment.get(i));
            }
            adapterEmployee.notifyDataSetChanged();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(AppUtil.dataEmployeesByCodeDepartment.size() > 0) {
//            for (int i = 0; i < AppUtil.dataEmployeesByCodeDepartment.size(); i++) {
//                Log.d("TAG", "Element at index " + i + ": " + AppUtil.dataEmployeesByCodeDepartment.get(i).getName());
//            }
//            this.dataEmployees = AppUtil.dataEmployeesByCodeDepartment;
//            adapterEmployee.notifyDataSetChanged();
//        }
//    }
}