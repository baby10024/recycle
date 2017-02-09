package com.jarvis.recycle.cart;

import com.example.recycle.R;
import com.javis.Adapter.Adapter_ListView_cart;
import com.javis.Adapter.Adapter_ListView_cart.onCheckedChanged;
import com.javis.changeRegion.ChangeRegion;
import com.javis.date.Data;
import com.javis.mytools.IBtnCallListener;
import com.javis.recycle.home.AddGarbageOrder;
import com.javis.recycle.home.Main_FA;
import com.javis.recycle.mail.BabyActivity;
import com.javis.recycle.mail.BuynowActivity;
import com.javis.recycle.mail.Mail_F;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.recycle.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * ���ﳵ������
 * 
 *
 */
public class Cart extends Activity implements IBtnCallListener,OnClickListener,onCheckedChanged {
	IBtnCallListener btnCallListener;
	private ImageView iv_back;
	private TextView tv_goShop, tv_cart_Allprice, tv_cart_buy_Ordel;
	private LinearLayout ll_cart;
	private ListView listView_cart;
	private CheckBox cb_cart_all;
	private Adapter_ListView_cart adapter;
	private String str_del = "����(0)";
	private boolean[] is_choice;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		is_choice=new boolean[Data.arrayList_cart.size()];
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_all_f);
		//getSaveData();
		btnCallListener = Cart.this;
		initView();
	}
	
	

	@Override
	public void transferMsg() {
		// ������Ӧ��FragmentActivity�еĿؼ�����
		System.out.println("��Activity�д���������Ϣ");
	}


	private void initView() {
		tv_goShop = (TextView) findViewById(R.id.tv_goShop);
		tv_cart_Allprice = (TextView) findViewById(R.id.tv_cart_Allprice);
		tv_cart_buy_Ordel = (TextView) findViewById(R.id.tv_cart_buy_or_del);
		tv_cart_buy_Ordel.setText(str_del);
		cb_cart_all = (CheckBox) findViewById(R.id.cb_cart_all);
		
//		iv_back = (ImageView)findViewById(R.id.ago_back);
//		iv_back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				btnCallListener.transferMsg();
//				
//			}
//		});
		cb_cart_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				/*
				 * �ж�һ����ȫѡ��ťѡ�У�ȫѡ��ť�Ƿ�ѡ�����ѡ����ô�б�ÿһ�ж�ѡ��
				 * �ж϶�����ȫѡ��ťȡ������ȡ��ȫѡ��ťʱ�������������
				 * ����һ���������ȫѡ��ť����ʱֱ��ȡ���б����е�ѡ��״̬���ڶ���ȡ���б�ĳһ�У�����ȫѡȡ������ʱ�б���������Ȼ��ѡ��
				 * 
				 * �ж϶��ķ�����������ȡ�����ж��б�ÿһ�е�ѡ��״̬�����ȫ������ѡ��״̬����ô���б�ѡ����=�б�����������ʱ��������ȡ����
				 * ��ȡ�������е�ѡ��״̬����֮������ȡ��������Ӧ
				 */

				// ��¼�б�ÿһ�е�ѡ��״̬����
				int isChoice_all = 0;
				if (arg1) {
					// ����ȫѡ
					for (int i = 0; i < Data.arrayList_cart.size(); i++) {
						// ���ѡ����ȫѡ����ô�ͽ��б��ÿһ�ж�ѡ��
						((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(true);
					}
				} else {
					// ����ȫ��ȡ��
					for (int i = 0; i < Data.arrayList_cart.size(); i++) {
						// �ж��б�ÿһ���Ƿ���ѡ��״̬���������ѡ��״̬�������+1
						if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
							// ������б�ѡ��״̬������
							isChoice_all += 1;
						}
					}
					// �ж��б�ѡ�����Ƿ�����б��������������ڣ���ô����Ҫִ��ȫ��ȡ������
					if (isChoice_all == Data.arrayList_cart.size()) {
						// ���û��ѡ����ȫѡ����ô�ͽ��б��ÿһ�ж���ѡ
						for (int i = 0; i < Data.arrayList_cart.size(); i++) {
							// �б�ÿһ�ж�ȡ��
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
						}
					}
				}
			}
		});
		
		

		ll_cart = (LinearLayout) findViewById(R.id.ll_cart);
		listView_cart = (ListView) findViewById(R.id.listView_cart);
		// ������ﳵ�������ݣ���ô����ʾ���ݣ�������ʾĬ�Ͻ���
		if (Data.arrayList_cart != null && Data.arrayList_cart.size() != 0) {
			adapter = new Adapter_ListView_cart(Cart.this, Data.arrayList_cart);
			adapter.setOnCheckedChanged(Cart.this);
			listView_cart.setAdapter(adapter);
			ll_cart.setVisibility(View.GONE);
		} else {
			ll_cart.setVisibility(View.VISIBLE);
		}

		listView_cart.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(Cart.this, BabyActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		tv_cart_buy_Ordel.setOnClickListener(this);
		
	}

	/** adapter�Ļص������������CheckBox��ʱ�򴫵ݵ��λ�ú�checkBox��״̬ */
	@Override
	public void getChoiceData(int position, boolean isChoice) {
		//�õ��������һ��
		if (isChoice) {
			if (Data.arrayList_cart.size()!=0) {
				//49��ʾ��Ʒ�ļ۸�����͵��������û��ȥ��̬��ȡ��Ʒ�۸�
				Data.Allprice_cart += Float.valueOf(Data.arrayList_cart.get(position).get("num").toString())*88;
			}
		} else {
			if (Data.arrayList_cart.size()!=0) {
				Data.Allprice_cart -= Float.valueOf(Data.arrayList_cart.get(position).get("num").toString())*88;
			}
		}
		// ��¼�б���ѡ��״̬������
		int num_choice = 0;
		for (int i = 0; i < Data.arrayList_cart.size(); i++) {
			// �ж��б���ÿһ�е�ѡ��״̬�������ѡ�У�������1
			if (null!=listView_cart.getChildAt(i)&&((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
				// �б���ѡ��״̬������+1
				num_choice += 1;
				is_choice[i]=true;
			}
		}
		// �ж��б��е�CheckBox�Ƿ�ȫ��ѡ��
		if (num_choice == Data.arrayList_cart.size()) {
			// ���ѡ�е�״̬����=�б������������ô�ͽ�ȫѡ����Ϊѡ��
			cb_cart_all.setChecked(true);
		} else {
			// ���ѡ�е�״̬������=�б������������ô�ͽ�ȫѡ����Ϊȡ��
			cb_cart_all.setChecked(false);
		}

		tv_cart_Allprice.setText("�ϼƣ���"+Data.Allprice_cart + "");

		System.out.println("ѡ���λ��--->"+position);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_goShop://���ȥ���
			btnCallListener.transferMsg();
			
			break;
		case R.id.tv_cart_buy_or_del://�������/ɾ��
			boolean[] is_choice_copy=is_choice;
			if (tv_cart_buy_Ordel.getText().toString().equals("ɾ��")) {
				//ִ��ɾ������
				if (Data.arrayList_cart.size()!=0) {
					for (int i = is_choice_copy.length-1; i >=0; i--) {
						if (is_choice_copy[i]) {
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
							Data.arrayList_cart.remove(i);
							is_choice_copy=deleteByIndex(is_choice, i);
						}
					}
				}
				
				
				if (Data.arrayList_cart.size()==0) {
					ll_cart.setVisibility(View.VISIBLE);
				}
				
				adapter.notifyDataSetChanged();
				is_choice=new boolean[Data.arrayList_cart.size()];
				System.out.println("��ʱ�ĳ���---->"+is_choice.length);
			}else {
				//ִ�н������
				//Toast.makeText(Cart.this, "��ʱ�޷�����", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(Cart.this,BuynowActivity.class);
				startActivity(intent);
			}
			
			break;
		default:
			break;
		}
		
	}
	
	
	/**ɾ�������е�һ��Ԫ��*/
    public static boolean[] deleteByIndex(boolean[] array, int index) {
    	boolean[] newArray = new boolean[array.length - 1];
        for (int i = 0; i < newArray.length; i++) {
            if (i < index) {
                newArray[i] = array[i];
            } else {
                newArray[i] = array[i + 1];
            }
        }
        return newArray;
    }

}
