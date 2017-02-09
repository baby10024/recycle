package com.jarvis.database;

import java.util.ArrayList;
import java.util.HashMap;
import com.jarvis.javabean.GarbageOrder;
import com.jarvis.javabean.HouseServiceOrder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {

	public static final String DB_DBNAME = "recycle";

	public static final String DB_TABLENAME = "garbage";

	public static final String DB_HOUSE_TABLENAME = "houseservice";

	public static final int VERSION = 1;

	public static SQLiteDatabase dbInstance;

	public static SQLiteDatabase dbInstance1;

	private MyDBHelper myDBHelper;

	private Context context;

	public DBHelper(Context context) {
		this.context = context;
	}

	public void openDatabase() {
		if (dbInstance == null) {
			myDBHelper = new MyDBHelper(context, DB_DBNAME, VERSION);
			dbInstance = myDBHelper.getReadableDatabase();
		}
	}

	/**
	 * 往数据库里面的user表插入一条数据，若失败返回-1
	 * 
	 * @param user
	 * @return 失败返回-1
	 */

	// 插入垃圾回收訂單
	public long insert(GarbageOrder garbage_order) {
		ContentValues values = new ContentValues();
		values.put("flag", garbage_order.flag);
		values.put("garbage_type_id", garbage_order.garbage_type_id);
		values.put("garbage_type", garbage_order.garbage_type);
		values.put("date", garbage_order.date);
		values.put("time", garbage_order.time);
		values.put("address", garbage_order.address);
		values.put("phone_number", garbage_order.phone_number);
		values.put("exchange_type", garbage_order.exchange_type);
		values.put("note", garbage_order.note);
		return dbInstance.insert(DB_TABLENAME, null, values);
	}

	// 插入家政服務訂單
	public long insertHouse(HouseServiceOrder house_order) {
		ContentValues values = new ContentValues();
		values.put("flag", house_order.flag);
		values.put("house_type_id", house_order.house_type_id);
		values.put("house_type", house_order.house_type);
		values.put("date", house_order.date);
		values.put("time", house_order.time);
		values.put("address", house_order.address);
		values.put("phone_number", house_order.phone_number);
		values.put("allpay_type", house_order.allpay_type);
		values.put("note", house_order.note);
		return dbInstance.insert(DB_HOUSE_TABLENAME, null, values);
	}

	// 獲得所有垃圾訂單
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getAllgarbage_order() {
		ArrayList list = new ArrayList();

		// String SQL查询语句, String[] whereArgs 参选语句使用问号的替代参数
		Cursor cursor = dbInstance.query("garbage", new String[] {
				"garbage_order_id", "flag", "garbage_type_id", "garbage_type",
				"date", "time", "address", "phone_number", "exchange_type",
				"note" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, Comparable> item = new HashMap<String, Comparable>();
			item.put("garbage_order_id",
					cursor.getInt(cursor.getColumnIndex("garbage_order_id")));
			item.put("flag", cursor.getInt(cursor.getColumnIndex("flag")));
			item.put("garbage_type_id",
					cursor.getInt(cursor.getColumnIndex("garbage_type_id")));
			item.put("garbage_type",
					cursor.getString(cursor.getColumnIndex("garbage_type")));
			item.put("date", cursor.getString(cursor.getColumnIndex("date")));
			item.put("time", cursor.getString(cursor.getColumnIndex("time")));
			item.put("address",
					cursor.getString(cursor.getColumnIndex("address")));
			item.put("phone_number",
					cursor.getString(cursor.getColumnIndex("phone_number")));
			item.put("exchange_type",
					cursor.getString(cursor.getColumnIndex("exchange_type")));
			item.put("note", cursor.getString(cursor.getColumnIndex("note")));
			list.add(item);
		}
		return list;
	}

	// 獲得所有家政服務訂單
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getAllHouse_order() {
		ArrayList list = new ArrayList();

		// String SQL查询语句, String[] whereArgs 参选语句使用问号的替代参数
		Cursor cursor = dbInstance.query("houseservice", new String[] {
				"house_order_id", "flag", "house_type_id", "house_type",
				"date", "time", "address", "phone_number", "allpay_type",
				"note" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			HashMap<String, Comparable> item = new HashMap<String, Comparable>();
			item.put("house_order_id",
					cursor.getInt(cursor.getColumnIndex("house_order_id")));
			item.put("flag", cursor.getInt(cursor.getColumnIndex("flag")));
			item.put("house_type_id",
					cursor.getInt(cursor.getColumnIndex("house_type_id")));
			item.put("house_type",
					cursor.getString(cursor.getColumnIndex("house_type")));
			item.put("date", cursor.getString(cursor.getColumnIndex("date")));
			item.put("time", cursor.getString(cursor.getColumnIndex("time")));
			item.put("address",
					cursor.getString(cursor.getColumnIndex("address")));
			item.put("phone_number",
					cursor.getString(cursor.getColumnIndex("phone_number")));
			item.put("allpay_type",
					cursor.getString(cursor.getColumnIndex("allpay_type")));
			item.put("note", cursor.getString(cursor.getColumnIndex("note")));
			list.add(item);
		}
		return list;
	}

	// 刪除垃圾回收訂單
	public void delete(Integer integer) {
		dbInstance.delete(DB_TABLENAME, "garbage_order_id=?",
				new String[] { integer.toString() });
	}

	// 刪除家政服務訂單
	public void deleteHouse(Integer integer) {
		dbInstance.delete(DB_HOUSE_TABLENAME, "house_order_id=?",
				new String[] { integer.toString() });
	}

	// 更新垃圾回收訂單

	public void modify(GarbageOrder garbage_order) {
		Log.i("garbage_order_id1", garbage_order.garbage_order_id + "");
		ContentValues values1 = new ContentValues();
		values1.put("garbage_order_id", garbage_order.garbage_order_id);
		values1.put("flag", garbage_order.flag);
		values1.put("garbage_type_id", garbage_order.garbage_type_id);
		values1.put("garbage_type", garbage_order.garbage_type);
		values1.put("date", garbage_order.date);
		values1.put("time", garbage_order.time);
		values1.put("address", garbage_order.address);
		values1.put("phone_number", garbage_order.phone_number);
		values1.put("exchange_type", garbage_order.exchange_type);
		values1.put("note", garbage_order.note);
		Log.i("modify", "modify");
		Log.i("garbage_order_id2", garbage_order.garbage_order_id + "");
		dbInstance
				.update(DB_TABLENAME, values1, "garbage_order_id=?",
						new String[] { String
								.valueOf((garbage_order.garbage_order_id)) });
	}

	// 更新家政服務訂單
	public void modifyHouse(HouseServiceOrder house_order) {
		Log.i("house_order_id1", house_order.house_order_id + "");
		ContentValues values1 = new ContentValues();
		values1.put("house_order_id", house_order.house_order_id);
		values1.put("flag", house_order.flag);
		values1.put("house_type_id", house_order.house_type_id);
		values1.put("house_type", house_order.house_type);
		values1.put("date", house_order.date);
		values1.put("time", house_order.time);
		values1.put("address", house_order.address);
		values1.put("phone_number", house_order.phone_number);
		values1.put("allpay_type", house_order.allpay_type);
		values1.put("note", house_order.note);
		Log.i("modifyhouse", "modifyhouse");
		Log.i("house_order_id2", house_order.house_order_id + "");
		dbInstance.update(DB_HOUSE_TABLENAME, values1, "house_order_id=?",
				new String[] { String.valueOf((house_order.house_order_id)) });
	}

	// 查詢垃圾回收訂單
	public GarbageOrder query(int id) {
		GarbageOrder garbage_order = new GarbageOrder();
		Log.i("query_id", id + "");
		Cursor cursor = dbInstance.query("garbage", null, "garbage_order_id=?",
				new String[] { id + "" }, null, null, null);
		while (cursor.moveToNext()) {
			garbage_order.garbage_order_id = Integer.valueOf(id);
			garbage_order.garbage_type_id = Integer.parseInt(cursor
					.getString(cursor.getColumnIndex("garbage_type_id")));
			garbage_order.flag = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("flag")));
			garbage_order.garbage_type = cursor.getString(cursor
					.getColumnIndex("garbage_type"));
			garbage_order.address = cursor.getString(cursor
					.getColumnIndex("address"));
			garbage_order.phone_number = cursor.getString(cursor
					.getColumnIndex("phone_number"));
			garbage_order.date = cursor
					.getString(cursor.getColumnIndex("date"));
			garbage_order.time = cursor
					.getString(cursor.getColumnIndex("time"));
			garbage_order.exchange_type = cursor.getString(cursor
					.getColumnIndex("exchange_type"));
			garbage_order.note = cursor
					.getString(cursor.getColumnIndex("note"));

		}
		return garbage_order;
	}

	// 查詢家政服務訂單
	public HouseServiceOrder queryHouse(int id) {
		HouseServiceOrder house_order = new HouseServiceOrder();
		Log.i("query_id", id + "");
		Cursor cursor = dbInstance.query("houseservice", null,
				"house_order_id=?", new String[] { id + "" }, null, null, null);
		while (cursor.moveToNext()) {
			house_order.house_order_id = Integer.valueOf(id);
			house_order.house_type_id = Integer.parseInt(cursor
					.getString(cursor.getColumnIndex("house_type_id")));
			house_order.flag = Integer.parseInt(cursor.getString(cursor
					.getColumnIndex("flag")));
			house_order.house_type = cursor.getString(cursor
					.getColumnIndex("house_type"));
			house_order.address = cursor.getString(cursor
					.getColumnIndex("address"));
			house_order.phone_number = cursor.getString(cursor
					.getColumnIndex("phone_number"));
			house_order.date = cursor.getString(cursor.getColumnIndex("date"));
			house_order.time = cursor.getString(cursor.getColumnIndex("time"));
			house_order.allpay_type = cursor.getString(cursor
					.getColumnIndex("allpay_type"));
			house_order.note = cursor.getString(cursor.getColumnIndex("note"));

		}
		return house_order;
	}

	//查询最新一个垃圾订单id
	public int lastId() {

		Cursor cursor = dbInstance.rawQuery(
				"select last_insert_rowid() from garbage", null);
		int id = 0;
		if (cursor.moveToFirst())
			id = cursor.getInt(0);
		return id;
	}
	//查询最新一个家政服务订单id
		public int lastHouseId() {

			Cursor cursor = dbInstance.rawQuery(
					"select last_insert_rowid() from houseservice", null);
			int id = 0;
			if (cursor.moveToFirst())
				id = cursor.getInt(0);
			return id;
		}

	public class MyDBHelper extends SQLiteOpenHelper {

		private static final int VERSION = 1;

		public MyDBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		public MyDBHelper(Context context, String name) {
			this(context, name, VERSION);
		}

		public MyDBHelper(Context context, String name, int version) {
			this(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("hm", "create a Database");
			db.execSQL("create table garbage (garbage_order_id integer primary key autoincrement, flag integer "
					+ ",garbage_type_id integer,garbage_type text,date text,time text,"
					+ "address text,phone_number text,exchange_type text,note text)");

			db.execSQL("create table houseservice (house_order_id integer primary key autoincrement, flag integer "
					+ ",house_type_id integer,house_type text,date text,time text,"
					+ "address text,phone_number text,allpay_type text,note text)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "drop table if exists " + DB_TABLENAME;
			db.execSQL(sql);
			String sql1 = "drop table if exists " + DB_HOUSE_TABLENAME;
			db.execSQL(sql1);
			myDBHelper.onCreate(db);
		}

	}

}