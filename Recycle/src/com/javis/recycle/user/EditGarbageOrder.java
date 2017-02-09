package com.javis.recycle.user;

import java.util.Calendar;
import com.example.recycle.R;
import com.jarvis.database.DBHelper;
import com.jarvis.javabean.GarbageOrder;
import com.javis.recycle.home.AddGarbageOrder;
import com.javis.recycle.home.Main_FA;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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

public class EditGarbageOrder extends Activity {
	private ImageView ago_back;
	private EditText ago_set_ago_date;
	private EditText ago_set_ago_time;
	private EditText ago_set_address;
	private EditText ago_set_phone_number;
	private EditText ago_set_note;
	private TextView tv_ok;
	private TextView tv_delete;
	private Calendar cal = Calendar.getInstance();
	private String str_date;
	private String str_time;
	private String str_garbage_type;
	private int str_minute;
	private int myhour;
	private String str_date_old;
	private String str_time_old;
	private String str_date_new;
	private String str_time_new;
	public int id = 0;
	private boolean flag = false;
	private String garbage_type[] = { "�����ֻ�", "���·�", "����ƿ", "������", "ֽ��", "��װ��",
			"�Ͼɵ���", "����" };
	private Spinner sp_exchange;
	private GarbageOrder garbage_order;

	// �����˵���������
	private ArrayAdapter<?> exchange_adapter;
	private TextView sp_view;
	private String str_exchangetype;
	// ���ݿ�
	DBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_garbage_order);
		// �����ͼ
		Intent intent = getIntent();
		garbage_order = (GarbageOrder) intent
				.getSerializableExtra("garbage_order");
		id = garbage_order.garbage_order_id;
		Log.i("edit_garbage_order_id", id + "");

		// �������ݿ������
		helper = new DBHelper(EditGarbageOrder.this);
		// �����ݿ�
		helper.openDatabase();
		initView();

		setEditTextDisable();
	}

	private void initView() {

		
		str_date_old = garbage_order.date;
		str_time_old = garbage_order.time;
		str_date_new = garbage_order.date;
		str_time_new = garbage_order.time;
		ago_set_address = (EditText) findViewById(R.id.ago_set_address);
		ago_set_phone_number = (EditText) findViewById(R.id.ago_set_phone_number);
		ago_set_note = (EditText) findViewById(R.id.ago_set_note);
		sp_exchange = (Spinner) findViewById(R.id.sp_exchange);
		sp_view = (TextView) findViewById(R.id.sp_exchange_text);
		ago_set_ago_date = (EditText) findViewById(R.id.ago_set_ago_date);
		ago_set_ago_time = (EditText) findViewById(R.id.ago_set_ago_time);
		// shezhichushizhi
		ago_set_address.setText(garbage_order.address);
		ago_set_phone_number.setText(garbage_order.phone_number);
		ago_set_note.setText(garbage_order.note);
		// sp_exchange.set;
		ago_set_ago_date.setText(garbage_order.date);
		ago_set_ago_time.setText(garbage_order.time);

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
				Intent intent = new Intent(EditGarbageOrder.this, Main_FA.class);
				startActivity(intent);
			}
		});

		ago_set_ago_date.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal.setTimeInMillis(System.currentTimeMillis());
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				new DatePickerDialog(EditGarbageOrder.this,
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

		ago_set_ago_time.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cal.setTimeInMillis(System.currentTimeMillis());
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				new TimePickerDialog(EditGarbageOrder.this,
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
		tv_ok.setText("�޸�");
		tv_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (!flag) {
					tv_ok.setText("�����޸�");
					setEditTextAble();
					setTitle("�޸�");
					flag = true;
				} else {
					// �����ݿ������������
					setTitle("����");
					modify();

					if (!str_date_old.equals(str_date_new)
							|| !str_time_old.equals(str_time_new)) {
						Log.i("new alarm", str_date_new + str_time_new);

						baocunAction();
					}

					setEditTextDisable();
					setColorToGray();
					tv_ok.setText("�޸�");
					flag = false;
				}

			}

		});
		tv_delete = (TextView) findViewById(R.id.tv_delete);
		tv_delete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				helper.delete(garbage_order.garbage_order_id);
				setEditTextDisable();
				setColorToGray();
				Log.i("garbage_type_id", garbage_order.garbage_type_id + "");
				finish();
			}
		});

	}

	private void modify() {
		Log.i("garbage_order", garbage_order.garbage_order_id + "");
		GarbageOrder garbage_order1 = new GarbageOrder();
		garbage_order1.garbage_order_id = garbage_order.garbage_order_id;
		garbage_order1.flag = Integer.valueOf(0);
		garbage_order1.flag = garbage_order.flag;
		garbage_order1.date = ago_set_ago_date.getText().toString();
		garbage_order1.time = ago_set_ago_time.getText().toString();
		garbage_order1.address = ago_set_address.getText().toString();
		garbage_order1.phone_number = ago_set_phone_number.getText().toString();
		garbage_order1.exchange_type = str_exchangetype;
		garbage_order1.note = ago_set_note.getText().toString();
		garbage_order1.garbage_type_id = garbage_order.garbage_type_id;
		garbage_order1.garbage_type = garbage_order.garbage_type;

		if (str_date == null || str_date.equals("")) {

			Toast.makeText(EditGarbageOrder.this, "ԤԼ���ڲ�����Ϊ��",
					Toast.LENGTH_SHORT).show();
		}

		else if (str_time == null || str_time.equals("")) {

			Toast.makeText(EditGarbageOrder.this, "ԤԼʱ�䲻����Ϊ��",
					Toast.LENGTH_SHORT).show();
		} else if (garbage_order.address == null
				|| garbage_order.address.equals("")) {
			Toast.makeText(EditGarbageOrder.this, "ԤԼ��ַ������Ϊ��",
					Toast.LENGTH_SHORT).show();
		} else {

			Log.i("garbage_order_id_modify", garbage_order1.garbage_order_id
					+ "");
			helper.modify(garbage_order1);

		}
	}

	public void baocunAction() {

		Log.i("Edit_num_id_last", helper.lastId() + ".");
		cal.setTimeInMillis(System.currentTimeMillis());
		if (!str_time_new.equals("") && !str_date_new.equals("")) {
			String[] t1 = str_date_new.split("-");
			String[] t2 = str_time_new.split(":");
			Log.i("date", t1[0] + t1[1] + t1[2]);
			cal.set(Integer.parseInt(t1[0]), (Integer.parseInt(t1[1]) - 1),
					Integer.parseInt(t1[2]));
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t2[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(t2[1]));
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

		}
	}

	private void setEditTextDisable() {
		ago_set_ago_date.setEnabled(false);
		ago_set_ago_time.setEnabled(false);
		ago_set_address.setEnabled(false);
		ago_set_phone_number.setEnabled(false);
		ago_set_note.setEnabled(false);
		sp_exchange.setEnabled(false);
		sp_view.setEnabled(false);
		setColorToGray();

	}

	/**
	 * ������ʾ��������ɫΪ��ɫ
	 */
	private void setColorToGray() {
		ago_set_ago_date.setTextColor(Color.GRAY);
		ago_set_ago_time.setTextColor(Color.GRAY);
		ago_set_address.setTextColor(Color.GRAY);
		ago_set_phone_number.setTextColor(Color.GRAY);
		ago_set_note.setTextColor(Color.GRAY);
		sp_view.setTextColor(Color.GRAY);
	}

	/**
	 * ����EditTextΪ����״̬
	 */
	private void setEditTextAble() {
		ago_set_ago_date.setEnabled(true);
		ago_set_ago_time.setEnabled(true);
		ago_set_address.setEnabled(true);
		ago_set_phone_number.setEnabled(true);
		ago_set_note.setEnabled(true);
		sp_exchange.setEnabled(true);
		sp_view.setEnabled(true);
		setColorToBlack();
	}

	/**
	 * ������ʾ��������ɫΪ��ɫ
	 */
	private void setColorToBlack() {
		ago_set_ago_date.setTextColor(Color.BLACK);
		ago_set_ago_time.setTextColor(Color.BLACK);
		ago_set_address.setTextColor(Color.BLACK);
		ago_set_phone_number.setTextColor(Color.BLACK);
		ago_set_note.setTextColor(Color.BLACK);
		sp_view.setTextColor(Color.BLACK);
	}

	// public void baocunAction() {
	//
	// id = helper.lastId();
	// cal.setTimeInMillis(System.currentTimeMillis());
	// if(!str_time.equals("")&&!str_date.equals("")){
	// String[] t1 = str_date.split("-");
	// String[] t2 = str_time.split(":");
	// cal.set(Integer.parseInt(t1[0]),
	// (Integer.parseInt(t1[1])-1),Integer.parseInt(t1[2]));
	// cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t2[0]));
	// cal.set(Calendar.MINUTE, Integer.parseInt(t2[1]));
	// cal.set(Calendar.SECOND, 0);
	// cal.set(Calendar.MILLISECOND, 0);
	//
	// Intent intent = new Intent(AddGarbageOrder.this,CallAlarm.class);
	// String str_id = id+"";
	// long TIME1=cal.getTimeInMillis();
	// intent.putExtra("action_id", str_id);
	// intent.putExtra("action_time", TIME1+"");
	// Log.i("Ҫ����CLL��ʱ��",TIME1+"");
	// PendingIntent pi = PendingIntent.getBroadcast(Add.this, id, intent, 0);
	//
	// long TIME = (cal.getTimeInMillis() - System.currentTimeMillis())/5;
	//
	// //am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
	// am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+TIME,TIME,pi
	// );
	// }
	// }

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

}
