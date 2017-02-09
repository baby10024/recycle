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
	private String garbage_type[] = { "�����ֻ�", "���·�", "����ƿ", "������", "ֽ��", "��װ��",
			"�Ͼɵ���", "����" };
	private Spinner sp_exchange;

	// �����˵���������
	private ArrayAdapter<?> exchange_adapter;
	private TextView sp_view;
	private String str_exchangetype;
	// ���ݿ�
	DBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_garbage_order);
		Intent intent = getIntent();
		// str_garbage_type = savedInstanceState.getString("��������").toString();
		str_garbage_type = intent.getStringExtra("��������").toString();
		Log.i("������addgarbageOrder����ȡ������������Ϊ", str_garbage_type);
		// setTitle(garbage_type[Integer.parseInt(str_garbage_type)]);
		// ������������

		// �������ݿ������
		helper = new DBHelper(AddGarbageOrder.this);
		// �����ݿ�
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
		// ����ѡ������ArrayAdapter��������
		exchange_adapter = ArrayAdapter.createFromResource(this,
				R.array.exchange,
				android.R.layout.simple_expandable_list_item_1);
		// ���������б�ķ��
		exchange_adapter
				.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
		// ��adapter1 ��ӵ�spinner��
		sp_exchange.setAdapter(exchange_adapter);
		// ����¼�Spinner�¼�����
		sp_exchange
				.setOnItemSelectedListener(new sp_exchangeXMLSelectedListener());
		// ����Ĭ��ֵ
		sp_exchange.setVisibility(View.VISIBLE);

		ago_back = (ImageView) findViewById(R.id.ago_back);
		ago_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ��ת����ҳ
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
					garbage_order.garbage_type = "�����ֻ�";
					garbage_order.garbage_type_id = Integer.valueOf(0);
				}

				if (Integer.parseInt(str_garbage_type) == 1) {
					garbage_order.garbage_type = "���·�";
					garbage_order.garbage_type_id = Integer.valueOf(1);
				}

				if (Integer.parseInt(str_garbage_type) == 2) {
					garbage_order.garbage_type = "����ƿ";
					garbage_order.garbage_type_id = Integer.valueOf(2);
				}

				if (Integer.parseInt(str_garbage_type) == 3) {
					garbage_order.garbage_type = "������";
					garbage_order.garbage_type_id = Integer.valueOf(3);
				}

				if (Integer.parseInt(str_garbage_type) == 4) {
					garbage_order.garbage_type = "ֽ��";
					garbage_order.garbage_type_id = Integer.valueOf(4);
				}

				if (Integer.parseInt(str_garbage_type) == 5) {
					garbage_order.garbage_type = "��װ��";
					garbage_order.garbage_type_id = Integer.valueOf(5);
				}

				if (Integer.parseInt(str_garbage_type) == 6) {
					garbage_order.garbage_type = "�Ͼɵ���";
					garbage_order.garbage_type_id = Integer.valueOf(6);
				}

				if (Integer.parseInt(str_garbage_type) == 7) {
					garbage_order.garbage_type = "����";
					garbage_order.garbage_type_id = Integer.valueOf(7);
				}

				if (str_date == null || str_date.equals("")) {

					Toast.makeText(AddGarbageOrder.this, "ԤԼ���ڲ�����Ϊ��",
							Toast.LENGTH_SHORT).show();
				}

				else if (str_time == null || str_time.equals("")) {

					Toast.makeText(AddGarbageOrder.this, "ԤԼʱ�䲻����Ϊ��",
							Toast.LENGTH_SHORT).show();
				} else if (garbage_order.address == null
						|| garbage_order.address.equals("")) {
					Toast.makeText(AddGarbageOrder.this, "ԤԼ��ַ������Ϊ��",
							Toast.LENGTH_SHORT).show();
				}

				else {

					// ��user�洢�����ݿ���
					long result = helper.insert(garbage_order);

					// ͨ��������ж��Ƿ����ɹ�����Ϊ-1�����ʾ��������ʧ��
					if (result == -1) {
						Toast.makeText(AddGarbageOrder.this, "�µ�ʧ��",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(AddGarbageOrder.this, "�µ��ɹ�",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(AddGarbageOrder.this,
								Main_FA.class);
						startActivity(intent);
					}

				}

			}

		});

	}

	// ʹ��XML��ʽ����
	class sp_exchangeXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			sp_view.setText("��ѡ��һ���ʽ:" + exchange_adapter.getItem(arg2));
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
