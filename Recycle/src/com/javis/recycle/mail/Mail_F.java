package com.javis.recycle.mail;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycle.R;
import com.jarvis.recycle.cart.Cart;
import com.javis.Adapter.Adapter_GridView;

import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;

/**
 * ��ҳ
 * @author http://yecaoly.taobao.com
 */
public class Mail_F extends Fragment {
	//����������
	private TextView mail_top_title;
	//ϴ��Һͼ��
	private ImageView iv_baby_detail;
	//���ﳵ
	private ImageView iv_cart;
	//��ҳ�ֲ�
	private AbSlidingPlayView viewPager;
	
	/**�洢��ҳ�ֲ��Ľ���*/
	private ArrayList<View> allListView;
	/**��ҳ�ֲ��Ľ������Դ*/
	private int[] resId = {  R.drawable.menu_viewpager_mail_1, R.drawable.menu_viewpager_mail_2, R.drawable.menu_viewpager_mail_3, R.drawable.menu_viewpager_mail_4, R.drawable.menu_viewpager_mail_5 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.mail_f, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		
		mail_top_title=(TextView) view.findViewById(R.id.mail_top_title);
		mail_top_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//��ս��������������
				Intent intent=new Intent(getActivity(),WareActivity.class);
				startActivity(intent);
			}
		});
		
		iv_baby_detail = (ImageView)view.findViewById(R.id.iv_baby_detail);
		iv_baby_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//��ս�������������
				Intent intent=new Intent(getActivity(),BabyActivity.class);
				startActivity(intent);
			}
		});
		//��ת�����ﳵҳ��
		iv_cart = (ImageView)view.findViewById(R.id.iv_cart);
		iv_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//��ת�����ﳵ����
				Intent intent=new Intent(getActivity(),Cart.class);
				startActivity(intent);
			}
		});
				
		
	
		viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
		//���ò��ŷ�ʽΪ˳�򲥷�
		viewPager.setPlayType(1);
		//���ò��ż��ʱ��
		viewPager.setSleepTime(3000);


		
		
		initViewPager();
	}

	private void initViewPager() {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		for (int i = 0; i < resId.length; i++) {
			//����ViewPager�Ĳ���
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			imageView.setImageResource(resId[i]);
			allListView.add(view);
		}
		
		
		viewPager.addViews(allListView);
		//��ʼ�ֲ�
		viewPager.startPlay();
//		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
//			@Override
//			public void onClick(int position) {
//				//��ת���������
//				Intent intent = new Intent(getActivity(), BabyActivity.class);
//				startActivity(intent);
//			}
//		});
		
		

	}

}
