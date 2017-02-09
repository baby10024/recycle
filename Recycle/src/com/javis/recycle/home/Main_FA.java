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

	// ����ײ��Ĳ˵���ť
	private ImageView[] bt_menu = new ImageView[4];
	// ����ײ��Ĳ˵���ťid
	private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1,
			R.id.iv_menu_2, R.id.iv_menu_3 };

	// ����ײ���ѡ�в˵���ť��Դ
	private int[] select_on = { R.drawable.guide_home_on,
			R.drawable.guide_mail_on, R.drawable.guide_secondhand_on,
			R.drawable.guide_user_on };
	// ����ײ���δѡ�в˵���ť��Դ
	private int[] select_off = { R.drawable.bt_menu_0_select,
			R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select,
			R.drawable.bt_menu_3_select };

	/** ������ */
	private Home_F home_F;
	/** �̳ǽ��� */
	private Mail_F mail_F;
	/** ���ֽ��� */
	private SecondHand_F secondhand_F;
	/** �ҵĽ��� */
	private User_F user_F;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fa);
		getSaveData();
		activity_manager = ActivityManager.getInstance();
		activity_manager.addActivity(this);
		initView();
	}

	 /** �õ�����Ĺ��ﳵ���� */
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

	// ��ʼ�����
	private void initView() {
		// �ҵ��ײ��˵��İ�ť�����ü���
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}

		// ��ʼ��Ĭ����ʾ�Ľ���
		if (home_F == null) {
			home_F = new Home_F();
			addFragment(home_F);
			showFragment(home_F);
		} else {
			showFragment(home_F);
		}
		// ����Ĭ����ҳΪ���ʱ��ͼƬ
		bt_menu[0].setImageResource(select_on[0]);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// �����˽���
			if (home_F == null) {
				home_F = new Home_F();
				// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
				addFragment(home_F);
				showFragment(home_F);
			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);
				}
			}

			break;
		case R.id.iv_menu_1:
			// �̳ǽ���
			if (mail_F == null) {
				mail_F = new Mail_F();
				// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
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
			// ���ֽ���
			if (secondhand_F == null) {
				secondhand_F = new SecondHand_F();
				// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
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
			// �ҵĽ���
			if (user_F != null) {
				removeFragment(user_F);
				user_F = null;
			}
			user_F = new User_F();
			// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
			addFragment(user_F);
			showFragment(user_F);

			break;

		}

		// ���ð�ť��ѡ�к�δѡ����Դ
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** ���Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** ɾ��Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** ��ʾFragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		// ����Fragment���л�����
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// �ж�ҳ���Ƿ��Ѿ�����������Ѿ���������ô�����ص�
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

	/** ���ذ�ť�ļ��� */
	@Override
	public void onBackPressed() {
		activity_manager.exitAllProgress();
		super.onBackPressed();
	}

	/** Fragment�Ļص����� */
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
	 * ��Ӧ��Fragment�д���������Ϣ
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

		System.out.println("��Fragment�д���������Ϣ");
	}



}
