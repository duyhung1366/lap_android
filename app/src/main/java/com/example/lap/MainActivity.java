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

import com.example.lap.adapter.DepartmentAdapter;
import com.example.lap.model.Department;
import com.example.lap.model.Employee;
import com.example.lap.util.AppUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;

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

    private EditText code_employee;
    private EditText name_employee;
    RadioButton gender_male_employee;
    RadioButton gender_female_employee;

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
        code_employee = dialog_add_employee.findViewById(R.id.code_employee);
        name_employee = dialog_add_employee.findViewById(R.id.name_employee);
        gender_male_employee = dialog_add_employee.findViewById(R.id.gender_male_employee);
        gender_female_employee = dialog_add_employee.findViewById(R.id.gender_female_employee);

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
                    clearTextDepartment();
                    adapterDepartment.notifyDataSetChanged();
                }
            }
        });

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Thêm nhân viên"
                // ...
//                Log.d("TAG", "selected data: " + selectedDepartment.getName().toString());
                dialog_add_employee.show();
                dialog_department.dismiss(); // Đóng popup
            }
        });

        employeeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút "Danh sách nhân viên"
                // ...
                Intent intent = new Intent(MainActivity.this, ListEmployeeActivity.class);
//                Bundle extra = new Bundle();
//                extra.putSerializable("data_employees", dataEmployees);
//                intent.putExtras(extra);

                // filter employees by code department

                AppUtil.dataEmployees = dataEmployees;
                AppUtil.dataEmployeesByCodeDepartment = fillterByCodeDepartment(selectedDepartment.getCode());
                AppUtil.dataDepartments = dataDepartments;
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
                    if(findIndexEmployeeByCode(code) != -1) {
                        // Tồn tại
                        Toast.makeText(MainActivity.this, "Mã code đã tồn tại, chọn mã khác", Toast.LENGTH_SHORT).show();
                    } else {
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
                        clearTextEmployee();
                    }
                } else {
//                    thong bao
                    Toast.makeText(MainActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearText_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTextEmployee();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppUtil.dataDepartments.size() > 0) {
            dataDepartments = AppUtil.dataDepartments;
            adapterDepartment.notifyDataSetChanged();
        }
        if(AppUtil.dataEmployees.size() > 0) {
            dataEmployees = AppUtil.dataEmployees;
            adapterDepartment.notifyDataSetChanged();
        }
        clearTextDepartment();
    }

    private void clearTextDepartment() {
        name_department.setText("");
        code_department.setText("");
    }

    private void clearTextEmployee() {
        code_employee.setText("");
        name_employee.setText("");
        gender_female_employee.setChecked(false);
        gender_male_employee.setChecked(false);
    }

    private int findIndexEmployeeByCode(String code) {
        for (int i = 0; i < dataEmployees.size(); i++) {
            if (dataEmployees.get(i).getCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public static int findIndex(ArrayList<Department> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCode().equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<Employee> fillterByCodeDepartment(String codeDepartment) {
        ArrayList<Employee> resultfillter = new ArrayList<>();
        for (Employee employee : dataEmployees) {
            if (employee.getCodeDepartment().equals(codeDepartment)) {
                resultfillter.add(employee);
            }
        }

        return resultfillter;
    }
}