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

public class ShowHouseServiceOrder extends Activity {

	private Integer flag_i;
	private Integer flag_i_integer = Integer.valueOf(1);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_house_order);
		reFreshFrinedList();
	}


	private void reFreshFrinedList() {

		ArrayList list1 = new ArrayList();
		ListView lv = (ListView) findViewById(R.id.house_DeuList); // ����ListView����
		// �������ݿ������
		DBHelper helper = new DBHelper(ShowHouseServiceOrder.this);
		helper.openDatabase();
		
		ArrayList list = helper.getAllHouse_order();// �õ������û���list
		
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
			 * ��Ӧ�����¼��������ĳһ��ѡ���ʱ����ת���û���ϸ��Ϣҳ��
			 */

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap item = (HashMap) arg0.getItemAtPosition(arg2);
				int house_order_id = Integer.parseInt(String.valueOf(item
						.get("house_order_id")));
				int id = Integer.parseInt(String.valueOf(item.get("house_type_id")));

				Intent intent1 = new Intent(ShowHouseServiceOrder.this,
						EditHouseServiceOrder.class);
				HouseServiceOrder house_order = new HouseServiceOrder();
				house_order.house_order_id = Integer.parseInt(String
						.valueOf(item.get("house_order_id")));
				house_order.house_type_id = Integer.parseInt(String
						.valueOf(item.get("house_type_id")));
				house_order.flag = Integer.parseInt(String.valueOf(item
						.get("flag")));
				house_order.house_type = String.valueOf(item
						.get("house_type"));
				house_order.address = String.valueOf(item.get("address"));
				house_order.phone_number = String.valueOf(item
						.get("phone_number"));
				house_order.date = String.valueOf(item.get("date"));
				house_order.time = String.valueOf(item.get("time"));
				house_order.allpay_type = String.valueOf(item
						.get("allpay_type"));
				house_order.note = String.valueOf(item.get("note"));
				intent1.putExtra("house_order", house_order);

				/* ��arg2��Ϊ�����봫��ȥ ���ڱ�ʶ�޸����λ�� */
				startActivityForResult(intent1, arg2);
			}
		});
	}

	
}
