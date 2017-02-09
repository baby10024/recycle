package com.javis.recycle.secondhand;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.Toast;
import com.example.recycle.R;
import com.javis.Adapter.Adapter_GridView;
import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;

/**
 * ��ҳ
 * @author http://yecaoly.taobao.com
 */
public class SecondHand_F extends Fragment {
	
	private ImageView iv_phone1,iv_message1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = LayoutInflater.from(getActivity()).inflate(R.layout.secondhand_f, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		iv_phone1 = (ImageView) view.findViewById(R.id.iv_phone1);
		iv_phone1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder adb = new Builder(getActivity());
				adb.setTitle("��ϵ�绰");
				adb.setMessage("18647374104");
				adb.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), "�������",
								Toast.LENGTH_SHORT).show();
					}
				});
				adb.setNegativeButton("ȡ��", null);
				adb.show();
			}
			
		});
		
		iv_message1 = (ImageView) view.findViewById(R.id.iv_message1);
		iv_message1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				AlertDialog.Builder adb = new Builder(getActivity());
				adb.setTitle("��ϵ�绰");
				adb.setMessage("18647374104");
				adb.setPositiveButton("������", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), "�������",
								Toast.LENGTH_SHORT).show();
					}
				});
				adb.setNegativeButton("ȡ��", null);
				adb.show();
			}
			
		});
		
		
		
		
	}

}

