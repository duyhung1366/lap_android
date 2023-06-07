package com.example.lap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lap.adapter.EmployeeAdapter;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;
import com.example.lap.util.AppUtil;

import java.util.ArrayList;

public class ListEmployeeActivity extends AppCompatActivity {

    private ArrayList<Employee> dataEmployees;

    private EmployeeAdapter adapterEmployee;
    private Employee employeeSelected;
    private int indexEmployeeSelected;

    private Dialog dialog_employee;
    private Dialog dialog_edit_employee;
    private Button editEmployeeBtn;
    private Button moveDepartmentBtn;
    private Button removeEmployee;

    private EditText name_employee;
    private EditText code_employee;
    private RadioButton gender_male_employee;
    private RadioButton gender_female_employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        // element
        Button btn_back = findViewById(R.id.back_home);
        ListView listView_employees = findViewById(R.id.list_employee);
        dataEmployees = new ArrayList<Employee>();
        adapterEmployee = new EmployeeAdapter(this, dataEmployees);
        // dialog
        dialog_employee =  new Dialog(this);
        dialog_edit_employee = new Dialog(this);
        dialog_edit_employee.setContentView(R.layout.popup_add_employee);
        dialog_employee.setContentView(R.layout.popup_setting_employee);

        editEmployeeBtn = dialog_employee.findViewById(R.id.editEmployee);

        Button save_employee = dialog_edit_employee.findViewById(R.id.add_employee_btn);
        Button clearText_employee = dialog_edit_employee.findViewById(R.id.remove_text_btn);
        name_employee = dialog_edit_employee.findViewById(R.id.name_employee);
        code_employee = dialog_edit_employee.findViewById(R.id.code_employee);
        gender_male_employee = dialog_edit_employee.findViewById(R.id.gender_male_employee);
        gender_female_employee = dialog_edit_employee.findViewById(R.id.gender_female_employee);

        listView_employees.setAdapter(adapterEmployee);

        if(AppUtil.dataEmployeesSelected.size() > 0) {
//            dataEmployees.clear();
            for (int i = 0; i < AppUtil.dataEmployeesSelected.size(); i++) {
//                Log.d("TAG", "Element at index " + i + ": " + AppUtil.dataEmployeesByCodeDepartment.get(i).getName());
                dataEmployees.add(AppUtil.dataEmployeesSelected.get(i));
            }
            adapterEmployee.notifyDataSetChanged();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView_employees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // click item list view
                employeeSelected = AppUtil.dataEmployeesSelected.get(position);
                indexEmployeeSelected = position;
                dialog_employee.show();
            }
        });

        editEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_edit_employee.show();
                name_employee.setText(employeeSelected.getName());
                code_employee.setText(employeeSelected.getCode());
                if(employeeSelected.getGender() == 1) {
                    gender_male_employee.setChecked(true);
                } else {
                    gender_female_employee.setChecked(true);
                }
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
                    if(!code.equals(employeeSelected.getCode()) && AppUtil.findIndexEmployeeByCode(code) != -1) {
                        // Tồn tại
                        Toast.makeText(ListEmployeeActivity.this, "Mã code đã tồn tại, chọn mã khác", Toast.LENGTH_SHORT).show();
                    } else {
                        if(gender_male_employee.isChecked()) {
                            male = 1;
                        } else {
                            male = 2;
                        }
                        Employee employee = new Employee(name, code, male, (byte)3, employeeSelected.getCodeDepartment());
                        dataEmployees.set(indexEmployeeSelected, employee);
                        AppUtil.dataEmployeesSelected.set(indexEmployeeSelected, employee);
                        employeeSelected = employee;
                        adapterEmployee.notifyDataSetChanged();
                        dialog_edit_employee.dismiss();

                    }
                } else {
//                    thong bao
                    Toast.makeText(ListEmployeeActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}