package com.javis.recycle.home;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.javis.Adapter.Adapter_GridView;

import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;
import com.javis.changeRegion.ChangeRegion;

/**
 * ��ҳ
 * @author http://yecaoly.taobao.com
 */
public class Home_F extends Fragment {
	//����������
	private TextView tv_top_title;
	//�������շ���ľŹ���
	private GridView gridView_classify1;
	//�����������ľŹ���
	private GridView gridView_classify2;
	//����������
	private Adapter_GridView adapter_GridView_classify1;
	private Adapter_GridView adapter_GridView_classify2;
	//��ҳ�ֲ�
	private AbSlidingPlayView viewPager;
	//�л�����
	private ImageView iv_regions;
	
	//�������շ���Ź������Դ�ļ�
	private int[] pic_path_classify1 = { R.drawable.menu_guide1_1, R.drawable.menu_guide1_2, R.drawable.menu_guide1_3, R.drawable.menu_guide1_4, R.drawable.menu_guide1_5, R.drawable.menu_guide1_6, R.drawable.menu_guide1_7, R.drawable.menu_guide1_8 };
	//�����������Ź������Դ�ļ�
	private int[] pic_path_classify2 = { R.drawable.menu_guide_home_housekeeping_1, R.drawable.menu_guide_home_housekeeping_2, R.drawable.menu_guide_home_housekeeping_3, R.drawable.menu_guide_home_housekeeping_4, R.drawable.menu_guide_home_housekeeping_5, R.drawable.menu_guide_home_housekeeping_6, R.drawable.menu_guide_home_housekeeping_7, R.drawable.menu_guide_home_housekeeping_8 };
	
	/**�洢��ҳ�ֲ��Ľ���*/
	private ArrayList<View> allListView;
	/**��ҳ�ֲ��Ľ������Դ*/
	private int[] resId = {  R.drawable.menu_viewpager_home_1, R.drawable.menu_viewpager_home_2, R.drawable.menu_viewpager_home_3, R.drawable.menu_viewpager_home_4, R.drawable.menu_viewpager_home_5 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
//		 Bundle bundle = getArguments();//��activity��������Bundle  
//	        if(bundle!=null){  
//	        	tv_top_title.setText(bundle.getString("str"));  
//	        	Log.i("������homef",bundle.getString("str"));
//	        }
		initView(view);
		
		return view;
	}

	private void initView(View view) {
		tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
		iv_regions=(ImageView) view.findViewById(R.id.fg1_local);
		iv_regions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//��ת���ı���н���
				Intent intent=new Intent(getActivity(),ChangeRegion.class);
				startActivity(intent);
			}
		});
		
		gridView_classify1 = (GridView) view.findViewById(R.id.my_gridview);
		gridView_classify1.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter_GridView_classify1 = new Adapter_GridView(getActivity(), pic_path_classify1);
		gridView_classify1.setAdapter(adapter_GridView_classify1);
		gridView_classify2 = (GridView) view.findViewById(R.id.my_gridview2);
		gridView_classify2.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter_GridView_classify2 = new Adapter_GridView(getActivity(), pic_path_classify2);
		gridView_classify2.setAdapter(adapter_GridView_classify2);
		
		viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
		//���ò��ŷ�ʽΪ˳�򲥷�
		viewPager.setPlayType(1);
		//���ò��ż��ʱ��
		viewPager.setSleepTime(3000);

		gridView_classify1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//��ת��ԤԼ�������ս���
				Intent intent = new Intent(getActivity(), AddGarbageOrder.class);
				intent.putExtra("��������", arg2+"");
				startActivity(intent);
			}
		});
		
		gridView_classify2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//��ת��ԤԼ�����������
				Intent intent = new Intent(getActivity(), AddHouseServiceOrder.class);
				intent.putExtra("��������", arg2+"");
				startActivity(intent);
			}
		});
		//���ճ�����
//		Intent intent = new Intent();
//		Bundle bundle = intent.getExtras();
//		String name = bundle.getString("city");
//		//String cla = bundle.getString("itemClass");
//		tv_top_title.setText(name);
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
