package com.example.lap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lap.adapter.DepartmentAdapter;
import com.example.lap.adapter.EmployeeAdapter;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;

import java.util.ArrayList;

public class ListEmployee extends AppCompatActivity {

    private ArrayList<Department> dataDepartments;
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
        dataDepartments = new ArrayList<Department>();

        adapterEmployee = new EmployeeAdapter(this, dataEmployees);

        listView_employees.setAdapter(adapterEmployee);

        // get deparment
//        Intent intent = getIntent();
//        ArrayList<Department> receivedDepartments = intent.getParcelableExtra("data_departments");
//        ArrayList<Employee> receivedEmployees = intent.getParcelableExtra("data_employees");
            Bundle extra = getIntent().getBundleExtra("extra");
            ArrayList<Employee> receivedEmployees = (ArrayList<Employee>) extra.getSerializable("data_employees");

//        ArrayList<Department> intentDataDepartments = (ArrayList<Department>) getIntent().getSerializableExtra("data_departments");
//        ArrayList<Employee> receivedEmployees = (ArrayList<Employee>) intent.getSerializableExtra("data_employees");

        if(receivedEmployees.size() > 0) {
            for (int i = 0; i < receivedEmployees.size(); i++) {
                Log.d("ArrayLog", "Element at index " + i + ": " + receivedEmployees.get(i).getName());
            }
//            dataEmployees = receivedEmployees;
//            adapterEmployee.notifyDataSetChanged();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEmployee.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}