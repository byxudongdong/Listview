package com.listview;

import com.sqlite.DatabaseHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //创建一个SQLiteHelper对象
        DatabaseHelper helper = new DatabaseHelper(MainActivity2.this,"stu.db");
        //使用getWritableDatabase()或getReadableDatabase()方法获得SQLiteDatabase对象
        SQLiteDatabase db = helper.getWritableDatabase();
        //插入记录
        db.execSQL("insert into userTb (name,age,sex) values ('张三',18,'女')");
        db.execSQL("insert into userTb (name,age,sex) values ('李四',19,'男')");
        db.execSQL("insert into userTb (name,age,sex) values ('王五',20,'女')");
        //获取游标对象
        Cursor queryResult = db.rawQuery("select * from userTb", null);
        if (queryResult != null) {
            //打印所有记录
            while (queryResult.moveToNext()) {
                Log.i("info", "id: " + queryResult.getInt(queryResult.getColumnIndex("_id"))
                        + " 姓名: " + queryResult.getString(queryResult.getColumnIndex("name"))
                        + " 年龄: " + queryResult.getInt(queryResult.getColumnIndex("age"))
                        + " 性别: " + queryResult.getString(queryResult.getColumnIndex("sex")));
            }
            //关闭游标对象
            queryResult.close();
        }
        //关闭数据库
        db.close();
    }
}