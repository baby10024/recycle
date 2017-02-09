package com.example.recycle;


import com.javis.recycle.home.Main_FA;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class WelcomePage extends Activity {
	  public static final String PREFS_NAME = "MyPrefsFile";
	    public static final String FIRST_RUN = "first";
	    private boolean first;

	

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	            first = settings.getBoolean(FIRST_RUN, true);
	            if (first) {	
			Log.d("debug", "第一次运行"); 
			 
			

			//隐藏标题栏函数
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//设置全屏
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.welcome_page);
			
			//倒计时
			new CountDownTimer(1000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
				}

				@Override
				public void onFinish() {
					
					
					
					Intent intent = new Intent(WelcomePage.this,Main_FA.class);
					startActivity(intent);
					
					@SuppressWarnings("deprecation")
					int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
					if (VERSION >= 5) {
						WelcomePage.this.overridePendingTransition(
								R.anim.alpha_in, R.anim.alpha_out);
					}
					finish();
				
				
				}
			}.start();
			
			} else
			{ 
			Log.d("debug", "不是第一次运行"); 

			//隐藏标题栏函数
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			//设置全屏
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.welcome_page);
			
			//倒计时
			new CountDownTimer(1000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
				}

				@Override
				public void onFinish() {
					//Intent intent = new Intent("com.example.softAccomary.guide.intent.action.zhidao");
					Intent intent = new Intent(WelcomePage.this,LogIn.class);
					startActivity(intent);
					
					@SuppressWarnings("deprecation")
					int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
					if (VERSION >= 5) {
						WelcomePage.this.overridePendingTransition(
								R.anim.alpha_in, R.anim.alpha_out);
					}
					finish();
				}
			}.start();
			
			}
			
			
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
		
		 protected void onStop() {
	            super.onStop();

	            // We need an Editor object to make preference changes.
	            // All objects are from android.context.Context
	            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	            SharedPreferences.Editor editor = settings.edit();
	            if (first) {
	                    editor.putBoolean(FIRST_RUN, false);
	            }
	            // Commit the edits!
	            editor.commit();
	    }

}

