package com.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sqlite.DatabaseHelper;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class MainActivity extends Activity {
	
	DatabaseHelper dbsqlite;
	private SQLiteDatabase  db = null;
	private Cursor cursor = null;    
    //private SimpleCursorAdapter adapter = null;
    
    private ListView lv;
    private List<Map<String, Object>> data;
    MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lv = (ListView)this.findViewById(R.id.lv);
        //��ȡ��Ҫ�󶨵��������õ�data��
        data = getData();
        adapter = new MyAdapter(this);
        lv.setAdapter(adapter);
        
        //ÿ���������Լ������ݿ�
        //ͨ��openOrCreateDatabase���򿪻򴴽�һ�����ݿ�,����SQLiteDatabase����
        /**
         *  openOrCreateDatabase(String name,int mode,SQLiteDatabase.CursorFactory factory)
         *  name: ���ݿ���
         *  mode: ���ݿ�Ȩ�ޣ�MODE_PRIVATEΪ��Ӧ�ó���˽�У�MODE_WORLD_READABLE��MODE_WORLD_WRITEABLE�ֱ�Ϊȫ�ֿɶ��Ϳ�д��
         *  factory: ��������ʵ����һ��cusor����Ĺ�����
         */
        db = openOrCreateDatabase("user.db",MODE_PRIVATE,null);
        //����һ����
        db.execSQL("create table if not exists userTb (" +
                    "_id integer primary key," +
                    "name text not null,age integer not null," +
                    "sex text not null)");
        //����в����¼
        db.execSQL("insert into userTb (name,age,sex) values ('����',18,'Ů')");
        db.execSQL("insert into userTb (name,age,sex) values ('����',19,'��')");
        db.execSQL("insert into userTb (name,age,sex) values ('����',20,'Ů')");
        //dbsqlite.getWritableDatabase();
        //db= (new dbsqlite(getApplicationContext())).getWritableDatabase();    
        //CursorΪ��ѯ�������������JDBC�е�ResultSet
        Cursor queryResult = db.rawQuery("select * from userTb", null);
        if (queryResult != null) {
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
        
        //����Item�ĵ���¼�
        lv.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Map<String, Object> getObject = data.get(position);	//ͨ��position��ȡ������Ķ���
        		//int infoId = getObject.;	//��ȡ��Ϣid
        		String infoTitle = (String) getObject.get("title");	//��ȡ��Ϣ����
        		String infoDetails = (String) getObject.get("info");	//��ȡ��Ϣ����
        		
        		//Toast��ʾ����
        		Toast.makeText(MainActivity.this, "��ϢID:"+position+infoTitle,Toast.LENGTH_SHORT).show();
        	}
        });
        
      //�����˵���ʾ
        lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
        	public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {
        		conMenu.setHeaderTitle("�˵�");
        		conMenu.add(0, 0, 0, "��Ŀһ");
        		conMenu.add(0, 1, 1, "��Ŀ��");
        		conMenu.add(0, 2, 2, "��Ŀ��");
        	}
        });
    }
    
  //�����˵�������
  	public boolean onContextItemSelected(MenuItem aItem) {
          AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
          Map<String, Object> map;
          switch (aItem.getItemId()) {
               case 0:
            	 data.removeAll(data);
                 adapter.notifyDataSetChanged();
                 lv.invalidate();
              	 Toast.makeText(MainActivity.this, "��������Ŀһ",Toast.LENGTH_SHORT).show();
              	 return true;
               case 1:
            	   map = new HashMap<String, Object>();
                   map.put("img", R.drawable.ic_launcher);
                   map.put("title", "��ȭ��" + "add");
                   map.put("info", "����Դ������..." + "add");
                   data.add(0,map);
                   adapter.notifyDataSetChanged();
                   lv.invalidate();
              	 Toast.makeText(MainActivity.this, "��������Ŀ��",Toast.LENGTH_SHORT).show();            
              	 return true;
               case 2:
            	   data.remove(0);
            	   adapter.notifyDataSetChanged();
                   lv.invalidate();
              	 Toast.makeText(MainActivity.this, "��������Ŀ��",Toast.LENGTH_SHORT).show();
              	 return true;
          }
  		return false;
     }
                                                    
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<88;i++)
        {
            map = new HashMap<String, Object>();
            map.put("img", R.drawable.ic_launcher);
            map.put("title", "��ȭ��" + i);
            map.put("info", "����Դ������..." + i);
            list.add(map);
        }
        return list;
    }
                                                    
    //ViewHolder��̬��
    static class ViewHolder
    {
        public ImageView img;
        public TextView title;
        public TextView info;
    }
                                                    
    public class MyAdapter extends BaseAdapter
    {   
        private LayoutInflater mInflater = null;
        private MyAdapter(Context context)
        {
            //����context�����ļ��ز��֣��������Demo17Activity������this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            //How many items are in the data set represented by this Adapter.
            //�ڴ�������������������ݼ��е���Ŀ��
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the data set.
            //��ȡ���ݼ�����ָ��������Ӧ��������
            return position;
        }
        @Override
        public long getItemId(int position) {
            //Get the row id associated with the specified position in the list.
            //��ȡ���б�����ָ��������Ӧ����id
            return position;
        }
                                                        
        //Get a View that displays the data at the specified position in the data set.
        //��ȡһ�������ݼ���ָ����������ͼ����ʾ����
        public int counter=0;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //�������convertViewΪ�գ�����Ҫ����View
            if(convertView == null)
            {
                holder = new ViewHolder();
                //�����Զ����Item���ּ��ز���
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.title = (TextView)convertView.findViewById(R.id.tv);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                //�����úõĲ��ֱ��浽�����У�������������Tag��Ա���淽��ȡ��Tag
                convertView.setTag(holder);
            }else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
            holder.title.setText((String)data.get(position).get("title"));
            holder.info.setText((String)data.get(position).get("info"));
            Log.i("Count", String.valueOf(counter ++));                                                
            return convertView;
        }
                                                        
    }
}