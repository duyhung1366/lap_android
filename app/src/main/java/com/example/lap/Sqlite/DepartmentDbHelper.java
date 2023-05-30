package com.example.lap.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lap.model.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "departments";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
//    private static final String KEY_GENDER = "gender";
    private static final String KEY_CODE = "code";

    public DepartmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_CODE);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }

    public void addDepartment(Department department) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, department.getName());
        values.put(KEY_CODE, department.getCode());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Department getDepartment(int departmentId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(departmentId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Department department = new Department(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return department;
    }

    public List<Department> getAllDepartments() {
        List<Department>  departmentList = new ArrayList<Department>();
        String query = "SELECT * FROM" + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Department department = new Department(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            departmentList.add(department);
            cursor.moveToNext();
        }
        return departmentList;
    }

    public void updateDepartment(Department department) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, department.getName());
        values.put(KEY_CODE, department.getCode());

        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(department.getId()) });
        db.close();
    }


}
