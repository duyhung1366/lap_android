package com.example.lap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lap.adapter.DepartmentAdapter;
import com.example.lap.model.Department;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list_department;
    private EditText code_department;
    private EditText name_department;
    private Button add_department;

    private ArrayList<Department> dataDepartments;
    private DepartmentAdapter adapterDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init
        dataDepartments = new ArrayList<Department>();
        adapterDepartment = new DepartmentAdapter(this, dataDepartments);

        list_department = findViewById(R.id.list_department);
        code_department = findViewById(R.id.code_department_input);
        name_department = findViewById(R.id.name_department_input);
        add_department = findViewById(R.id.add_department_btn);

        list_department.setAdapter(adapterDepartment);

        list_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Department selectedDepartment = dataDepartments.get(position);
                String departmentCode = selectedDepartment.getCode();
                // Sử dụng mã phòng ban tùy ý ở đây

                Log.d("TAG", "onItemClick: departmentCode : " + departmentCode);
            }
        });

        // add department
        add_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapterDepartment.clear();
                dataDepartments.add(new Department(code_department.getText().toString(), name_department.getText().toString()));
//                adapterDepartment.addAll(dataDepartments);
                adapterDepartment.notifyDataSetChanged();
            }
        });

    }
}