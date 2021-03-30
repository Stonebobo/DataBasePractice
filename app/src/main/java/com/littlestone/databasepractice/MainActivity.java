package com.littlestone.databasepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper databaseHelper;//用于创建表
    private Button btn_create,btn_insert_book,btn_update_book,btn_del_book;
    private SQLiteDatabase sqLiteDatabase;//用于增删改查

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        //建表
        btn_create = findViewById(R.id.btn_createdb);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = databaseHelper.getWritableDatabase();
            }
        });
        //增加数据
        btn_insert_book=findViewById(R.id.btn_insert_book);
        btn_insert_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = databaseHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("author","白文磊");
                values.put("price",100.0);
                values.put("pages",456);
                values.put("name","第一行代码");
                sqLiteDatabase.insert("Book",null,values);
            }
        });
        //更新数据
        btn_update_book=findViewById(R.id.btn_update_book);
        btn_update_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db= databaseHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("author","郭涵博");
                db.update("Book",values,"author=?",new String[]{"白文磊"});
            }
        });
        //删除数据
        btn_del_book=findViewById(R.id.btn_del_book);
        btn_del_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=databaseHelper.getWritableDatabase();
                db.delete("Book","author=?",new String[]{"郭涵博"});
            }
        });
    }
}