package com.example.eggrish.mysqlapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Windown7 on 10/2/2560.
 */

public class TodoListDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public TodoListDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public ArrayList<TodoList> getAlltodoList() {
        ArrayList<TodoList> todoList = new ArrayList<TodoList>();
        Cursor cursor = database.rawQuery("SELECT * FROM tbtodo_list;", null);
        cursor.moveToFirst();
//add TodoList
        TodoList todoList1;
        while (!cursor.isAfterLast()) {
//add TodoList
            todoList1 = new TodoList();
            todoList1.setTaskid(cursor.getInt(0));
            todoList1.setTaskname(cursor.getString(1));
            todoList.add(todoList1);
            cursor.moveToNext();
        }
        cursor.close();
        return todoList;
    }

    public void add(TodoList todoList) {
//รับค่าแล้วแปลงเป็น Object ชื่อ TodoList
        TodoList newTodoList = new TodoList();
        newTodoList = todoList;
//เอาค่ามาใส่ใน ContentValues
        ContentValues values = new ContentValues();
//ส่งค่าไปเก็บในฐานข้อมูล
        values.put("taskname", newTodoList.getTaskname());
        this.database.insert("tbtodo_list", null, values);
// กำหนดให้แสดงข้อมูลใน log ว่าข้อมูลมีการบันทึกแล้ว()
//อาจใช้คำสั่ง Toast แทนก็ได้
        Log.d("Todo List Demo :::", "Add OK");
    }
    public void update(TodoList todoList){
        TodoList updateTodoList = todoList;
        ContentValues values = new ContentValues();
        values.put("taskname",updateTodoList.getTaskname());
        values.put("taskid",updateTodoList.getTaskid());
        String where = "taskid=" + updateTodoList.getTaskid();
        this.database.update("tbtodo_list",values,where,null);
        Log.d("Todo List Demo :::", "Update OK!!!");
    }
    public void delete(TodoList todoList){
        TodoList delTodoList = todoList;
        String sqlText = "DELETE FROM tbtodo_list WHERE taskid=" + delTodoList.getTaskid();
        this.database.execSQL(sqlText);
    }
}
