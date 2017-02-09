package com.javis.recycle.mail;

import com.example.recycle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 确认订单界面
 * @author http://yecaoly.taobao.com
 *
 */
public class BuynowActivity extends Activity {

	private TextView bt_ok,bt_back,tv_all_price;
	private CheckBox cb_jifen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_now_a);
		initView();
		
	}
	
	private void initView(){
		bt_back=(TextView) findViewById(R.id.bt_buy_back);
		bt_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		tv_all_price = (TextView)findViewById(R.id.tv_all_price);
		cb_jifen = (CheckBox)findViewById(R.id.cb_jifen);
		cb_jifen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tv_all_price.setText("合计：￥20.2");
			}
		});
		bt_ok=(TextView) findViewById(R.id.tv_buy_ok);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(BuynowActivity.this, "暂无法支付", Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}

}
