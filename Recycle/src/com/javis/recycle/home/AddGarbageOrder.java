package com.javis.recycle.home;

import java.util.Calendar;
import com.example.recycle.R;
import com.jarvis.database.DBHelper;
import com.jarvis.javabean.GarbageOrder;
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

public class AddGarbageOrder extends Activity implements IBtnCallListener {

	private ImageView ago_back;
	private EditText ago_set_ago_date;
	private EditText ago_set_ago_time;
	private EditText ago_set_address;
	private EditText ago_set_phone_number;
	private EditText ago_set_note;
	private TextView tv_ok, tv_top_edit;
	private Calendar cal = Calendar.getInstance();
	private String str_date;
	private String str_time;
	private String str_garbage_type;
	private int str_minute;
	private int myhour;
	public int id = 0;
	private String garbage_type[] = { "智能手机", "旧衣服", "塑料瓶", "易拉罐", "纸类", "包装袋",
			"废旧电子", "更多" };
	private Spinner sp_exchange;

	// 下拉菜单的适配器
	private ArrayAdapter<?> exchange_adapter;
	private TextView sp_view;
	private String str_exchangetype;
	// 数据库
	DBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_garbage_order);
		Intent intent = getIntent();
		// str_garbage_type = savedInstanceState.getString("垃圾种类").toString();
		str_garbage_type = intent.getStringExtra("垃圾种类").toString();
		Log.i("这里是addgarbageOrder，获取到的垃圾种类为", str_garbage_type);
		// setTitle(garbage_type[Integer.parseInt(str_garbage_type)]);
		// 请求网络数据

		// 创建数据库帮助类
		helper = new DBHelper(AddGarbageOrder.this);
		// 打开数据库
		helper.openDatabase();
		initView();

	}

	private void initView() {
		tv_top_edit = (TextView) findViewById(R.id.tv_top_edit);
		tv_top_edit.setText(garbage_type[Integer.parseInt(str_garbage_type)]);
		ago_set_address = (EditText) findViewById(R.id.ago_set_address);
		ago_set_phone_number = (EditText) findViewById(R.id.ago_set_phone_number);
		ago_set_note = (EditText) findViewById(R.id.ago_set_note);
		sp_exchange = (Spinner) findViewById(R.id.sp_exchange);
		sp_view = (TextView) findViewById(R.id.sp_exchange_text);
		// 将可选内容与ArrayAdapter连接起来
		exchange_adapter = ArrayAdapter.createFromResource(this,
				R.array.exchange,
				android.R.layout.simple_expandable_list_item_1);
		// 设置下拉列表的风格
		exchange_adapter
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		// 将adapter1 添加到spinner中
		sp_exchange.setAdapter(exchange_adapter);
		// 添加事件Spinner事件监听
		sp_exchange
				.setOnItemSelectedListener(new sp_exchangeXMLSelectedListener());
		// 设置默认值
		sp_exchange.setVisibility(View.VISIBLE);

		ago_back = (ImageView) findViewById(R.id.ago_back);
		ago_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到首页
				Intent intent = new Intent(AddGarbageOrder.this, Main_FA.class);
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
				new DatePickerDialog(AddGarbageOrder.this,
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
				new TimePickerDialog(AddGarbageOrder.this,
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
				GarbageOrder garbage_order = new GarbageOrder();
				garbage_order.flag = Integer.valueOf(0);
				garbage_order.date = ago_set_ago_date.getText().toString();
				garbage_order.time = ago_set_ago_time.getText().toString();
				garbage_order.address = ago_set_address.getText().toString();
				garbage_order.phone_number = ago_set_phone_number.getText()
						.toString();
				garbage_order.exchange_type = str_exchangetype;
				garbage_order.note = ago_set_note.getText().toString();

				// System.out.println(Integer.parseInt(str_garbage_type));

				if (Integer.parseInt(str_garbage_type) == 0) {
					garbage_order.garbage_type = "智能手机";
					garbage_order.garbage_type_id = Integer.valueOf(0);
				}

				if (Integer.parseInt(str_garbage_type) == 1) {
					garbage_order.garbage_type = "旧衣服";
					garbage_order.garbage_type_id = Integer.valueOf(1);
				}

				if (Integer.parseInt(str_garbage_type) == 2) {
					garbage_order.garbage_type = "塑料瓶";
					garbage_order.garbage_type_id = Integer.valueOf(2);
				}

				if (Integer.parseInt(str_garbage_type) == 3) {
					garbage_order.garbage_type = "易拉罐";
					garbage_order.garbage_type_id = Integer.valueOf(3);
				}

				if (Integer.parseInt(str_garbage_type) == 4) {
					garbage_order.garbage_type = "纸类";
					garbage_order.garbage_type_id = Integer.valueOf(4);
				}

				if (Integer.parseInt(str_garbage_type) == 5) {
					garbage_order.garbage_type = "包装袋";
					garbage_order.garbage_type_id = Integer.valueOf(5);
				}

				if (Integer.parseInt(str_garbage_type) == 6) {
					garbage_order.garbage_type = "废旧电子";
					garbage_order.garbage_type_id = Integer.valueOf(6);
				}

				if (Integer.parseInt(str_garbage_type) == 7) {
					garbage_order.garbage_type = "更多";
					garbage_order.garbage_type_id = Integer.valueOf(7);
				}

				if (str_date == null || str_date.equals("")) {

					Toast.makeText(AddGarbageOrder.this, "预约日期不可以为空",
							Toast.LENGTH_SHORT).show();
				}

				else if (str_time == null || str_time.equals("")) {

					Toast.makeText(AddGarbageOrder.this, "预约时间不可以为空",
							Toast.LENGTH_SHORT).show();
				} else if (garbage_order.address == null
						|| garbage_order.address.equals("")) {
					Toast.makeText(AddGarbageOrder.this, "预约地址不可以为空",
							Toast.LENGTH_SHORT).show();
				}

				else {

					// 把user存储到数据库里
					long result = helper.insert(garbage_order);

					// 通过结果来判断是否插入成功，若为-1，则表示插入数据失败
					if (result == -1) {
						Toast.makeText(AddGarbageOrder.this, "下单失败",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(AddGarbageOrder.this, "下单成功",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(AddGarbageOrder.this,
								Main_FA.class);
						startActivity(intent);
					}

				}

			}

		});

	}

	// 使用XML形式操作
	class sp_exchangeXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			sp_view.setText("请选择兑换方式:" + exchange_adapter.getItem(arg2));
			str_exchangetype = (String) exchange_adapter.getItem(arg2);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	public void transferMsg() {
		// TODO Auto-generated method stub

	}

}
