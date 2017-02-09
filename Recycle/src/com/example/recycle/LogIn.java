package com.example.recycle;

import com.javis.recycle.home.AddGarbageOrder;
import com.javis.recycle.home.Main_FA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends Activity {

	private TextView tv_sign_up;
	private Button bn_Login;
	private EditText userNameText;
	private EditText passwdText;
	private ActivityManager activity_manager;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in);
		activity_manager = ActivityManager.getInstance();
		activity_manager.addActivity(this);
		initView();
	}

	private void initView() {

		userNameText = (EditText) findViewById(R.id.userNameText);
		passwdText = (EditText) findViewById(R.id.passwdText);
		tv_sign_up = (TextView) findViewById(R.id.tv_sign_up);
		tv_sign_up.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 跳转到注册界面
				Intent intent = new Intent(LogIn.this, SignUp.class);
				startActivity(intent);
			}
		});

		bn_Login = (Button) findViewById(R.id.bn_Login);
		bn_Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (userNameText.getText().toString().equals("瑞赛克")
						&& passwdText.getText().toString().equals("123456")) {

					// 跳转到改变城市界面
					Intent intent = new Intent(LogIn.this, Main_FA.class);
					startActivity(intent);
				}

				else {
					Toast.makeText(LogIn.this, "用户名或密码错误", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

	}

}
