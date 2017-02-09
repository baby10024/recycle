package com.javis.recycle.user;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.recycle.R;
import com.jarvis.database.DBHelper;
import com.jarvis.javabean.GarbageOrder;
import com.jarvis.javabean.HouseServiceOrder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ShowGarbageOrder extends Activity {

	private Integer flag_i;
	private Integer flag_i_integer = Integer.valueOf(1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_garbage_order);
		reFreshFrinedList();

	}

	private void reFreshFrinedList() {

		ArrayList list1 = new ArrayList();
		ListView lv = (ListView) findViewById(R.id.garbage_DeuList); // 创建ListView对象
		// 创建数据库帮助类
		DBHelper helper = new DBHelper(ShowGarbageOrder.this);
		helper.openDatabase();

		ArrayList list = helper.getAllgarbage_order();// 拿到所有用户的list

		Log.i("garbage_size", list.size() + "");

		for (int i = 0; i < list.size(); i++) {

			HashMap item = (HashMap) list.get(i);
			flag_i = (Integer) item.get("flag");

			Log.i("garbage_flag_i", flag_i + "");

			// if (flag_i.compareTo(flag_i_integer) == 0) {
			list1.add(item);
			Log.i("garbage_lll", "ddd");
			// }
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list1,
				R.layout.show_order_list, new String[] { "garbage_type" },
				new int[] { R.id.show_order_deulist });

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 响应单击事件，单点击某一个选项的时候，跳转到用户详细信息页面
			 */

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap item = (HashMap) arg0.getItemAtPosition(arg2);
				int garbage_order_id = Integer.parseInt(String.valueOf(item
						.get("garbage_order_id")));
				int id = Integer.parseInt(String.valueOf(item
						.get("garbage_type_id")));

				Intent intent1 = new Intent(ShowGarbageOrder.this,
						EditGarbageOrder.class);
				GarbageOrder garbage_order = new GarbageOrder();
				garbage_order.garbage_order_id = Integer.parseInt(String
						.valueOf(item.get("garbage_order_id")));
				garbage_order.garbage_type_id = Integer.parseInt(String
						.valueOf(item.get("garbage_type_id")));
				garbage_order.flag = Integer.parseInt(String.valueOf(item
						.get("flag")));
				garbage_order.garbage_type = String.valueOf(item
						.get("garbage_type"));
				garbage_order.address = String.valueOf(item.get("address"));
				garbage_order.phone_number = String.valueOf(item
						.get("phone_number"));
				garbage_order.date = String.valueOf(item.get("date"));
				garbage_order.time = String.valueOf(item.get("time"));
				garbage_order.exchange_type = String.valueOf(item
						.get("exchange_type"));
				garbage_order.note = String.valueOf(item.get("note"));
				intent1.putExtra("garbage_order", garbage_order);

				/* 将arg2作为请求码传过去 用于标识修改项的位置 */
				startActivityForResult(intent1, arg2);

			}
		});
	}

	private void reFreshFrinedList1() {

		ArrayList list1 = new ArrayList();
		ListView lv = (ListView) findViewById(R.id.garbage_DeuList); // 创建ListView对象
		// 创建数据库帮助类
		DBHelper helper = new DBHelper(ShowGarbageOrder.this);
		helper.openDatabase();

		ArrayList list = helper.getAllHouse_order();// 拿到所有用户的list

		Log.i("house_size", list.size() + "");

		for (int i = 0; i < list.size(); i++) {

			HashMap item = (HashMap) list.get(i);
			flag_i = (Integer) item.get("flag");

			Log.i("house_flag_i", flag_i + "");

			// if (flag_i.compareTo(flag_i_integer) == 0) {
			list1.add(item);
			Log.i("house_lll", "ddd");
			// }
		}

		SimpleAdapter adapter = new SimpleAdapter(this, list1,
				R.layout.show_order_list, new String[] { "house_type" },
				new int[] { R.id.show_order_deulist });

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 响应单击事件，单点击某一个选项的时候，跳转到用户详细信息页面
			 */

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap item = (HashMap) arg0.getItemAtPosition(arg2);
				int house_order_id = Integer.parseInt(String.valueOf(item
						.get("house_order_id")));
				int id = Integer.parseInt(String.valueOf(item
						.get("house_type_id")));

				Intent intent1 = new Intent(ShowGarbageOrder.this,
						EditHouseServiceOrder.class);
				HouseServiceOrder house_order = new HouseServiceOrder();
				house_order.house_order_id = Integer.parseInt(String
						.valueOf(item.get("house_order_id")));
				house_order.house_type_id = Integer.parseInt(String
						.valueOf(item.get("house_type_id")));
				house_order.flag = Integer.parseInt(String.valueOf(item
						.get("flag")));
				house_order.house_type = String.valueOf(item.get("house_type"));
				house_order.address = String.valueOf(item.get("address"));
				house_order.phone_number = String.valueOf(item
						.get("phone_number"));
				house_order.date = String.valueOf(item.get("date"));
				house_order.time = String.valueOf(item.get("time"));
				house_order.allpay_type = String.valueOf(item
						.get("allpay_type"));
				house_order.note = String.valueOf(item.get("note"));
				intent1.putExtra("house_order", house_order);

				/* 将arg2作为请求码传过去 用于标识修改项的位置 */
				startActivityForResult(intent1, arg2);
			}
		});
	}

}
