package com.example.lap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lap.adapter.DepartmentAdapter;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list_department;
    private EditText code_department;
    private EditText name_department;
    private Button add_department;

    private ArrayList<Department> dataDepartments;
    private DepartmentAdapter adapterDepartment;
    private Dialog dialog_department;
    private Dialog dialog_add_employee;
    private Department selectedDepartment;
    private byte indexSelected;

    private ArrayList<Employee> dataEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init
        dataDepartments = new ArrayList<Department>();
        dataEmployees = new ArrayList<Employee>();
        adapterDepartment = new DepartmentAdapter(this, dataDepartments, dataEmployees);

        list_department = findViewById(R.id.list_department);
        code_department = findViewById(R.id.code_department_input);
        name_department = findViewById(R.id.name_department_input);
        add_department = findViewById(R.id.add_department_btn);

        list_department.setAdapter(adapterDepartment);

        dialog_department = new Dialog(this);
        dialog_department.setContentView(R.layout.popup_department);
        dialog_add_employee = new Dialog(this);
        dialog_add_employee.setContentView(R.layout.popup_add_employee);

        Button addEmployeeButton = dialog_department.findViewById(R.id.addEmployeeButton);
        Button employeeListButton = dialog_department.findViewById(R.id.employeeListButton);
        Button createManagerButton = dialog_department.findViewById(R.id.createManagerButton);
        Button deleteDepartmentButton = dialog_department.findViewById(R.id.deleteDepartmentButton);

        Button save_employee = dialog_add_employee.findViewById(R.id.add_employee_btn);
        Button clearText_employee = dialog_add_employee.findViewById(R.id.remove_text_btn);
        EditText code_employee = dialog_add_employee.findViewById(R.id.code_employee);
        EditText name_employee = dialog_add_employee.findViewById(R.id.name_employee);
        RadioButton gender_male_employee = dialog_add_employee.findViewById(R.id.gender_male_employee);
        RadioButton gender_female_employee = dialog_add_employee.findViewById(R.id.gender_female_employee);

        list_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Department selectedDepartment = dataDepartments.get(position);
                String departmentCode = selectedDepartment.getCode();
                // Sử dụng mã phòng ban tùy ý ở đây
                name_department.setText(selectedDepartment.getName());
                code_department.setText(selectedDepartment.getCode());

            }
        });

        list_department.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                dialog_department.show();
                Department selected = dataDepartments.get(position);
                selectedDepartment = selected;
                indexSelected = (byte)position;
                return false;
            }
        });

        // add department
        add_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapterDepartment.clear();
                // logic
                String id = code_department.getText().toString();
                String name = name_department.getText().toString();
                if(!id.equals("") && !name.equals("")) {
                    int index = findIndex(dataDepartments, id);
                    if(index == -1) {
                        // create
                        dataDepartments.add(new Department(id, name));
                    } else {
                        // update
                        dataDepartments.set(index, new Department(id, name));
                    }
                    adapterDepartment.notifyDataSetChanged();
                    name_department.setText("");
                    code_department.setText("");
                }
            }
        });

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Thêm nhân viên"
                // ...
                Log.d("TAG", "selected data: " + selectedDepartment.getName().toString());
                dialog_add_employee.show();
                dialog_department.dismiss(); // Đóng popup
            }
        });

        employeeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Danh sách nhân viên"
                // ...
                Bundle extra = new Bundle();
                extra.putSerializable("data_employees", dataEmployees);
                Intent intent = new Intent(MainActivity.this, ListEmployee.class);
//                intent.putExtra("data_departments", dataDepartments);
//                for (int i = 0; i < dataEmployees.size(); i++) {
//                    intent.putExtra("data_employees" + i, dataEmployees.get(i).getName());
//                    Log.d("ArrayLog", "Element at index " + i + ": " + dataEmployees.get(i).getName());
//                }
                intent.putExtra("extra", extra);
                startActivity(intent);
                dialog_department.dismiss(); // Đóng popup
            }
        });

        createManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Lập trưởng phòng"
                // ...
                dialog_department.dismiss(); // Đóng popup
            }
        });

        deleteDepartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Xóa phòng ban"
                // ...

                dataDepartments.remove(indexSelected);
                adapterDepartment.notifyDataSetInvalidated();
                dialog_department.dismiss(); // Đóng popup
            }
        });

        save_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Lưu nhân viên"
                // ...
                String code = code_employee.getText().toString();
                String name = name_employee.getText().toString();
                byte male = 1;
                if(!code.equals("") && !name.equals("") && (gender_female_employee.isChecked() || gender_male_employee.isChecked())) {
                    if(gender_male_employee.isChecked()) {
                        male = 1;
                    } else {
                        male = 2;
                    }
                    Employee employee = new Employee(name, code, male, (byte)3, selectedDepartment.getCode());
                    dataEmployees.add(employee);
                    adapterDepartment.notifyDataSetInvalidated();
                    dialog_add_employee.dismiss(); // Đóng popup
                    dialog_department.show();
                } else {
//                    thong bao
                    Toast.makeText(MainActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static int findIndex(ArrayList<Department> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equals(target)) {
                return i;
            }
        }
        return -1;
    }
}