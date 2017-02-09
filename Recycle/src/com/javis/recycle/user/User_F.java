package com.javis.recycle.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycle.LogIn;
import com.example.recycle.R;

/**
 * 首页
 * 
 * @author http://yecaoly.taobao.com
 */
public class User_F extends Fragment {
	public static final String PREFS_NAME = "MyPrefsFile";
    public static final String FIRST_RUN = "first";
    private boolean first;

    private LinearLayout ll_garbage_order;
    private LinearLayout ll_house_order;
	private ImageView iv_check_order;
	private ImageView iv_check_garbage_order;
	private ImageView iv_check_house_order;
	private TextView tv_usertop_logout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_f,
				null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		
		ll_garbage_order = (LinearLayout) view.findViewById(R.id.ll_garbage_order);
		ll_garbage_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到查询垃圾订单界面
				Intent intent = new Intent(getActivity(),
						ShowGarbageOrder.class);
				startActivity(intent);
			}
		});
		iv_check_garbage_order = (ImageView) view
				.findViewById(R.id.iv_check_garbage_order);
		iv_check_garbage_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到查询垃圾订单界面
				Intent intent = new Intent(getActivity(),
						ShowGarbageOrder.class);
				startActivity(intent);
			}
		});
		ll_house_order = (LinearLayout) view.findViewById(R.id.ll_house_order);
		ll_house_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到查询家政服務界面
				Intent intent = new Intent(getActivity(),
						ShowHouseServiceOrder.class);
				startActivity(intent);
			}
		});
		iv_check_house_order = (ImageView) view
				.findViewById(R.id.iv_check_house_order);
		iv_check_house_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到查询家政服務界面
				Intent intent = new Intent(getActivity(),
						ShowHouseServiceOrder.class);
				startActivity(intent);
			}
		});
		tv_usertop_logout = (TextView) view.findViewById(R.id.tv_usertop_logout);
		tv_usertop_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder adb = new Builder(getActivity());
				adb.setTitle("确定登出吗？");
				adb.setMessage("");
				adb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getActivity(),
								LogIn.class);
						startActivity(intent);
					}
				});
				adb.setNegativeButton("取消", null);
				adb.show();
				// 跳转到登出界面
				
				
			}
		});
		
	}

}
