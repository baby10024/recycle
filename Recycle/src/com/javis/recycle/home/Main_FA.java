package com.javis.recycle.home;

import java.util.HashMap;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.recycle.ActivityManager;
import com.example.recycle.R;
import com.javis.date.Data;
import com.javis.mytools.IBtnCallListener;
import com.javis.recycle.mail.Mail_F;
import com.javis.recycle.secondhand.SecondHand_F;
import com.javis.recycle.user.User_F;

public class Main_FA extends FragmentActivity implements OnClickListener,
		IBtnCallListener {
	
	private ActivityManager activity_manager;

	// 界面底部的菜单按钮
	private ImageView[] bt_menu = new ImageView[4];
	// 界面底部的菜单按钮id
	private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1,
			R.id.iv_menu_2, R.id.iv_menu_3 };

	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.guide_home_on,
			R.drawable.guide_mail_on, R.drawable.guide_secondhand_on,
			R.drawable.guide_user_on };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.bt_menu_0_select,
			R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select,
			R.drawable.bt_menu_3_select };

	/** 主界面 */
	private Home_F home_F;
	/** 商城界面 */
	private Mail_F mail_F;
	/** 二手界面 */
	private SecondHand_F secondhand_F;
	/** 我的界面 */
	private User_F user_F;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fa);
		getSaveData();
		activity_manager = ActivityManager.getInstance();
		activity_manager.addActivity(this);
		initView();
	}

	 /** 得到保存的购物车数据 */
	 private void getSaveData() {
	 HashMap<String, Object> hashMap = new HashMap<String, Object>();
	
	 SharedPreferences sp = getSharedPreferences("SAVE_CART",
	 Context.MODE_PRIVATE);
	 int size = sp.getInt("ArrayCart_size", 0);
	 for (int i = 0; i < size; i++) {
	 hashMap.put("type", sp.getString("ArrayCart_type_" + i, ""));
	 hashMap.put("color", sp.getString("ArrayCart_color_" + i, ""));
	 hashMap.put("num", sp.getString("ArrayCart_num_" + i, ""));
	 Data.arrayList_cart.add(hashMap);
	 }
	
	 }

	// 初始化组件
	private void initView() {
		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// 初始化默认显示的界面
		if (home_F == null) {
			home_F = new Home_F();
			addFragment(home_F);
			showFragment(home_F);
		} else {
			showFragment(home_F);
		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// 瑞赛克界面
			if (home_F == null) {
				home_F = new Home_F();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);
			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);
				}
			}

			break;
		case R.id.iv_menu_1:
			// 商城界面
			if (mail_F == null) {
				mail_F = new Mail_F();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!mail_F.isHidden()) {
					addFragment(mail_F);
					showFragment(mail_F);
				}
			} else {
				if (mail_F.isHidden()) {
					showFragment(mail_F);
				}
			}

			break;
		case R.id.iv_menu_2:
			// 二手界面
			if (secondhand_F == null) {
				secondhand_F = new SecondHand_F();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!secondhand_F.isHidden()) {
					addFragment(secondhand_F);
					showFragment(secondhand_F);
				}
			} else {
				if (secondhand_F.isHidden()) {
					showFragment(secondhand_F);
				}
			}

			break;
		case R.id.iv_menu_3:
			// 我的界面
			if (user_F != null) {
				removeFragment(user_F);
				user_F = null;
			}
			user_F = new User_F();
			// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
			addFragment(user_F);
			showFragment(user_F);

			break;

		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		// 设置Fragment的切换动画
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (home_F != null) {
			ft.hide(home_F);
		}
		if (mail_F != null) {
			ft.hide(mail_F);
		}
		if (secondhand_F != null) {
			ft.hide(secondhand_F);
		}
		if (user_F != null) {
			ft.hide(user_F);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

	/** 返回按钮的监听 */
	@Override
	public void onBackPressed() {
		activity_manager.exitAllProgress();
		super.onBackPressed();
	}

	/** Fragment的回调函数 */
	@SuppressWarnings("unused")
	private IBtnCallListener btnCallListener;

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			btnCallListener = (IBtnCallListener) fragment;
		} catch (Exception e) {
		}

		super.onAttachFragment(fragment);
	}

	/**
	 * 响应从Fragment中传过来的消息
	 */
	@Override
	public void transferMsg() {
		if (mail_F == null) {
			mail_F = new Mail_F();
			addFragment(mail_F);
			showFragment(mail_F);
		} else {
			showFragment(mail_F);
		}
		bt_menu[0].setImageResource(select_off[0]);
		bt_menu[1].setImageResource(select_on[1]);

		System.out.println("由Fragment中传送来的消息");
	}



}
