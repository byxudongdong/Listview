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
        //����һ��SQLiteHelper����
        DatabaseHelper helper = new DatabaseHelper(MainActivity2.this,"stu.db");
        //ʹ��getWritableDatabase()��getReadableDatabase()�������SQLiteDatabase����
        SQLiteDatabase db = helper.getWritableDatabase();
        //�����¼
        db.execSQL("insert into userTb (name,age,sex) values ('����',18,'Ů')");
        db.execSQL("insert into userTb (name,age,sex) values ('����',19,'��')");
        db.execSQL("insert into userTb (name,age,sex) values ('����',20,'Ů')");
        //��ȡ�α����
        Cursor queryResult = db.rawQuery("select * from userTb", null);
        if (queryResult != null) {
            //��ӡ���м�¼
            while (queryResult.moveToNext()) {
                Log.i("info", "id: " + queryResult.getInt(queryResult.getColumnIndex("_id"))
                        + " ����: " + queryResult.getString(queryResult.getColumnIndex("name"))
                        + " ����: " + queryResult.getInt(queryResult.getColumnIndex("age"))
                        + " �Ա�: " + queryResult.getString(queryResult.getColumnIndex("sex")));
            }
            //�ر��α����
            queryResult.close();
        }
        //�ر����ݿ�
        db.close();
    }
}