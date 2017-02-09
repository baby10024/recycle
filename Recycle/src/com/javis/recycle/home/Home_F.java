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
 * 首页
 * @author http://yecaoly.taobao.com
 */
public class Home_F extends Fragment {
	//顶部标题栏
	private TextView tv_top_title;
	//垃圾回收分类的九宫格
	private GridView gridView_classify1;
	//家政服务分类的九宫格
	private GridView gridView_classify2;
	//适配器声明
	private Adapter_GridView adapter_GridView_classify1;
	private Adapter_GridView adapter_GridView_classify2;
	//首页轮播
	private AbSlidingPlayView viewPager;
	//切换地区
	private ImageView iv_regions;
	
	//垃圾回收分类九宫格的资源文件
	private int[] pic_path_classify1 = { R.drawable.menu_guide1_1, R.drawable.menu_guide1_2, R.drawable.menu_guide1_3, R.drawable.menu_guide1_4, R.drawable.menu_guide1_5, R.drawable.menu_guide1_6, R.drawable.menu_guide1_7, R.drawable.menu_guide1_8 };
	//家政服务分类九宫格的资源文件
	private int[] pic_path_classify2 = { R.drawable.menu_guide_home_housekeeping_1, R.drawable.menu_guide_home_housekeeping_2, R.drawable.menu_guide_home_housekeeping_3, R.drawable.menu_guide_home_housekeeping_4, R.drawable.menu_guide_home_housekeeping_5, R.drawable.menu_guide_home_housekeeping_6, R.drawable.menu_guide_home_housekeeping_7, R.drawable.menu_guide_home_housekeeping_8 };
	
	/**存储首页轮播的界面*/
	private ArrayList<View> allListView;
	/**首页轮播的界面的资源*/
	private int[] resId = {  R.drawable.menu_viewpager_home_1, R.drawable.menu_viewpager_home_2, R.drawable.menu_viewpager_home_3, R.drawable.menu_viewpager_home_4, R.drawable.menu_viewpager_home_5 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
//		 Bundle bundle = getArguments();//从activity传过来的Bundle  
//	        if(bundle!=null){  
//	        	tv_top_title.setText(bundle.getString("str"));  
//	        	Log.i("这里是homef",bundle.getString("str"));
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
				//跳转到改变城市界面
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
		//设置播放方式为顺序播放
		viewPager.setPlayType(1);
		//设置播放间隔时间
		viewPager.setSleepTime(3000);

		gridView_classify1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//跳转到预约垃圾回收界面
				Intent intent = new Intent(getActivity(), AddGarbageOrder.class);
				intent.putExtra("垃圾种类", arg2+"");
				startActivity(intent);
			}
		});
		
		gridView_classify2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//跳转到预约家政服务界面
				Intent intent = new Intent(getActivity(), AddHouseServiceOrder.class);
				intent.putExtra("家政种类", arg2+"");
				startActivity(intent);
			}
		});
		//接收城市名
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
			//导入ViewPager的布局
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			imageView.setImageResource(resId[i]);
			allListView.add(view);
		}
		
		
		viewPager.addViews(allListView);
		//开始轮播
		viewPager.startPlay();
//		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
//			@Override
//			public void onClick(int position) {
//				//跳转到详情界面
//				Intent intent = new Intent(getActivity(), BabyActivity.class);
//				startActivity(intent);
//			}
//		});
	}
	
	

}
