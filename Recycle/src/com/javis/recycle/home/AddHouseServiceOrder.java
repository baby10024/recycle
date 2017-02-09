package com.javis.recycle.home;

import java.util.Calendar;
import com.example.recycle.R;
import com.jarvis.database.DBHelper;
import com.jarvis.javabean.GarbageOrder;
import com.jarvis.javabean.HouseServiceOrder;
import com.javis.mytools.IBtnCallListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddHouseServiceOrder extends Activity implements IBtnCallListener {

	private ImageView ago_back;
	private EditText ago_set_ago_date;
	private EditText ago_set_ago_time;
	private EditText ago_set_address;
	private EditText ago_set_phone_number;
	private EditText ago_set_note;
	private TextView tv_ok,tv_top_edit;
	private Calendar cal = Calendar.getInstance();
	private String str_date;
	private String str_time;
	private String str_house_type;
	private int str_minute;
	private int myhour;
	public int id = 0;
	private String house_type[] = { "钟点服务", "长期服务", "擦玻璃", "家具保养", "整家清洁",
			"月嫂", "保姆", "更多" };
	private Spinner sp_allpay;

	// 下拉菜单的适配器
	private ArrayAdapter<?> allpay_adapter;
	private TextView sp_view;
	private String str_allpaytype;
	// 数据库
	DBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_house_service_order);
		Intent intent = getIntent();
		str_house_type = intent.getStringExtra("家政种类").toString();
		Log.i("这里是AddHouseServiceOrder，获取到的家政种类为", str_house_type);
		//setTitle(house_type[Integer.parseInt(str_house_type)]);
		// 请求网络数据

		// 创建数据库帮助类
		helper = new DBHelper(AddHouseServiceOrder.this);
		// 打开数据库
		helper.openDatabase();
		initView();

	}

	private void initView() {
		tv_top_edit = (TextView) findViewById(R.id.tv_top_edit);
		tv_top_edit.setText(house_type[Integer.parseInt(str_house_type)]);
		ago_set_address = (EditText) findViewById(R.id.ago_set_address);
		ago_set_phone_number = (EditText) findViewById(R.id.ago_set_phone_number);
		ago_set_note = (EditText) findViewById(R.id.ago_set_note);
		sp_allpay = (Spinner) findViewById(R.id.sp_exchange);
		sp_view = (TextView) findViewById(R.id.sp_exchange_text);
		// 将可选内容与ArrayAdapter连接起来
		allpay_adapter = ArrayAdapter.createFromResource(this,
				R.array.houseexchange,
				android.R.layout.simple_expandable_list_item_1);
		// 设置下拉列表的风格
		allpay_adapter
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		// 将adapter1 添加到spinner中
		sp_allpay.setAdapter(allpay_adapter);
		// 添加事件Spinner事件监听
		sp_allpay.setOnItemSelectedListener(new sp_allpayXMLSelectedListener());
		// 设置默认值
		sp_allpay.setVisibility(View.VISIBLE);

		ago_back = (ImageView) findViewById(R.id.ago_back);
		ago_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到首页
				Intent intent = new Intent(AddHouseServiceOrder.this,
						Main_FA.class);
				startActivity(intent);
			}
		});
		ago_set_ago_date = (EditText) findViewById(R.id.ago_set_ago_date);
		ago_set_ago_date.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal.setTimeInMillis(System.currentTimeMillis());
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				new DatePickerDialog(AddHouseServiceOrder.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								str_date = year + "-" + (monthOfYear + 1) + "-"
										+ dayOfMonth;
								ago_set_ago_date.setText(str_date);
							}
						}, year, month, day).show();
			}
		});

		ago_set_ago_time = (EditText) findViewById(R.id.ago_set_ago_time);
		ago_set_ago_time.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal.setTimeInMillis(System.currentTimeMillis());
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				new TimePickerDialog(AddHouseServiceOrder.this,
						new TimePickerDialog.OnTimeSetListener() {
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								str_time = hourOfDay + ":" + minute;
								str_minute = minute;
								myhour = hourOfDay;
								ago_set_ago_time.setText(str_time);
							}
						}, hour, minute, true).show();
			}
		});

		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HouseServiceOrder house_order = new HouseServiceOrder();
				house_order.flag = Integer.valueOf(0);
				house_order.date = ago_set_ago_date.getText().toString();
				house_order.time = ago_set_ago_time.getText().toString();
				house_order.address = ago_set_address.getText().toString();
				house_order.phone_number = ago_set_phone_number.getText()
						.toString();
				house_order.allpay_type = str_allpaytype;
				house_order.note = ago_set_note.getText().toString();

				if (Integer.parseInt(str_house_type) == 0) {
					house_order.house_type = "钟点服务";
					house_order.house_type_id = Integer.valueOf(0);
				}

				if (Integer.parseInt(str_house_type) == 1) {
					house_order.house_type = "长期服务";
					house_order.house_type_id = Integer.valueOf(1);
				}

				if (Integer.parseInt(str_house_type) == 2) {
					house_order.house_type = "擦玻璃";
					house_order.house_type_id = Integer.valueOf(2);
				}

				if (Integer.parseInt(str_house_type) == 3) {
					house_order.house_type = "家具保养";
					house_order.house_type_id = Integer.valueOf(3);
				}

				if (Integer.parseInt(str_house_type) == 4) {
					house_order.house_type = "整家清洁";
					house_order.house_type_id = Integer.valueOf(4);
				}

				if (Integer.parseInt(str_house_type) == 5) {
					house_order.house_type = "月嫂";
					house_order.house_type_id = Integer.valueOf(5);
				}

				if (Integer.parseInt(str_house_type) == 6) {
					house_order.house_type = "保姆";
					house_order.house_type_id = Integer.valueOf(6);
				}

				if (Integer.parseInt(str_house_type) == 7) {
					house_order.house_type = "更多";
					house_order.house_type_id = Integer.valueOf(7);
				}

				if (str_date == null || str_date.equals("")) {

					Toast.makeText(AddHouseServiceOrder.this, "预约日期不可以为空",
							Toast.LENGTH_SHORT).show();
				}

				else if (str_time == null || str_time.equals("")) {

					Toast.makeText(AddHouseServiceOrder.this, "预约时间不可以为空",
							Toast.LENGTH_SHORT).show();
				} else if (house_order.address == null
						|| house_order.address.equals("")) {
					Toast.makeText(AddHouseServiceOrder.this, "预约地址不可以为空",
							Toast.LENGTH_SHORT).show();
				}

				else {

					// 把user存储到数据库里
					long result = helper.insertHouse(house_order);

					// 通过结果来判断是否插入成功，若为-1，则表示插入数据失败
					if (result == -1) {
						Toast.makeText(AddHouseServiceOrder.this, "下单失败",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(AddHouseServiceOrder.this, "下单成功",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(AddHouseServiceOrder.this,
								Main_FA.class);
						startActivity(intent);
					}


				}

			}

		});

	}

	// 使用XML形式操作
	class sp_allpayXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			sp_view.setText("请选择結算方式:" + allpay_adapter.getItem(arg2));
			str_allpaytype = (String) allpay_adapter.getItem(arg2);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void transferMsg() {
		// TODO Auto-generated method stub

	}

}
