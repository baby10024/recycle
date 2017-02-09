package com.javis.changeRegion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycle.R;
import com.javis.changeRegion.MyLetterAlistView.OnTouchingLetterChangedListener;
import com.javis.mytools.IBtnCallListener;
import com.javis.recycle.home.Home_F;
import com.javis.recycle.home.Main_FA;

public class ChangeRegion extends Activity implements IBtnCallListener  {
	// ��������
	private EditText editText;
	// �����б�
	private ListView sortListView;
	// �Ҳ�A-Z��ĸ�б�
	private MyLetterAlistView letterListView;
	// dialog text
	private TextView overlay;
	// �����ǵ���dialog�߳�
	private OverlayThread overlayThread;
	// ����Adapter
	private SortAdapter adapter;
	private Handler handler;
	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;
	
	//��ת����ҳ
	IBtnCallListener btnCallListener;
	
	private FragmentManager manager;  
    private FragmentTransaction transaction;  
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_region);
		Log.i("����", "y");
		initView();
	}

	private void initView() {
		handler = new Handler();
		overlayThread = new OverlayThread();
		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();
		// ����ƴ������
		pinyinComparator = new PinyinComparator();

		editText = (EditText) findViewById(R.id.editText);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		letterListView = (MyLetterAlistView) findViewById(R.id.cityLetterListView);
		SourceDateList = filledData(getResources().getStringArray(
				R.array.province));
		// ����a-z��������Դ����
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		initOverlay();
		letterListView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
//				Toast.makeText(getApplication(),
//						((SortModel) adapter.getItem(position)).getName(),
//						Toast.LENGTH_SHORT).show();
				String city_name  =((SortModel) adapter.getItem(position)).getName();
				Log.i("��ȡ���ĵ���",city_name);
				Bundle bundle = new Bundle();
				bundle.putString("cityname", city_name);
//				Intent intent=new Intent(ChangeRegion.this,Home_F.class);
//				intent.putExtras(bundle);
//				startActivity(intent);
//				btnCallListener.transferMsg();
//				 Home_F fragment1 = new Home_F();  
//		           fragment1.setArguments(bundle);  
				Intent intent=new Intent(ChangeRegion.this,Main_FA.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		// �������������ֵ�ĸı�����������
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	// �Ҳ�A-Z��ĸ����
	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			// ����ĸ�״γ��ֵ�λ��
			int position = adapter.getPositionForSection(s.charAt(0));
			if (position != -1) {
				sortListView.setSelection(position);
				overlay.setText(SourceDateList.get(position).getSortLetters());
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// �ӳ�һ���ִ�У���overlayΪ���ɼ�
				handler.postDelayed(overlayThread, 1500);
			}
		}
	}

	// ��ʼ������ƴ������ĸ������ʾ��
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	// ����overlay���ɼ�
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	

	
	@Override
	public void transferMsg() {
		// ������Ӧ��FragmentActivity�еĿؼ�����
		
	}
}
