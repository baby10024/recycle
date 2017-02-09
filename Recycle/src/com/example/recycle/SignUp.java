package com.example.recycle;

import java.io.File;

import com.javis.recycle.home.AddHouseServiceOrder;
import com.javis.recycle.home.Main_FA;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUp extends Activity implements OnClickListener {
	private ImageView addHead;
	private Button open_albums;
	private Button take_pictures;
	private Button cancel,btn_ok;
	private ImageView faceImage;
	private EditText et_username;
	private EditText et_realname;
	private EditText et_password;
	
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	
	private static final int PHOTO_REQUEST = 1;
	private static final int CAMERA_REQUEST = 2;
	private static final int PHOTO_CLIP = 3;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);

		LayoutInflater factorys = LayoutInflater.from(SignUp.this);
		final View textEntryView = factorys.inflate(
				R.layout.photo_choose_dialog, null);
		addHead = (ImageView) findViewById(R.id.add_head);
		addHead.setOnClickListener(this);
		faceImage = (ImageView) findViewById(R.id.add_head);
		et_username = (EditText) findViewById(R.id.et_username);
		et_realname = (EditText) findViewById(R.id.et_realname);
		et_password = (EditText) findViewById(R.id.et_password);
//		open_albums = (Button) textEntryView.findViewById(R.id.albums);
//		open_albums.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View textEntryView) {
//				
//				getPicFromPhoto();
//				Log.i("调用相册","yes");
//			}
//		});
		
		btn_ok= (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				Log.i("注册用户名", et_username.getText().toString());
				if(et_username.getText().toString().equals("昵称")==true){
					Toast.makeText(SignUp.this, "昵称为空",
							Toast.LENGTH_LONG).show();
				}
				else if(et_realname.getText().equals("真实姓名")) {
					Toast.makeText(SignUp.this, "姓名为空",
							Toast.LENGTH_LONG).show();
			
				}
				else if(et_password.getText().equals("密码"))
				{
					Toast.makeText(SignUp.this, "密码为空",
							Toast.LENGTH_LONG).show();
				}
				else {
					
					Toast.makeText(SignUp.this, "注册成功",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(SignUp.this,
							LogIn.class);
					startActivity(intent);}
			}
		});
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_head:
			showDialog();
			break;
//		case R.id.albums:
//			
//			getPicFromPhoto();
//			Log.i("调用相册","yes");
//			break;
//			
//		case R.id.take_pictures:
//			getPicFromCamera();
//			Log.i("调用相机","yes");
//			break;
//			
//		case R.id.cancel:
//			finish();
//			break;
		
		}

	}
	
	private void getPicFromPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST);
	}

	private void getPicFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "test.jpg")));
		startActivityForResult(intent, CAMERA_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CAMERA_REQUEST:
			switch (resultCode) {
			case -1:// -1表示拍照成功
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/test.jpg");
				if (file.exists()) {
					photoClip(Uri.fromFile(file));
				}
				break;
			default:
				break;
			}
			break;
		case PHOTO_REQUEST:
			if (data != null) {
				photoClip(data.getData());
			}
			break;
		case PHOTO_CLIP:
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					faceImage.setImageBitmap(photo);
				}
			}
			break;
		default:
			break;
		}

	}

	private void photoClip(Uri uri) {
		// 调用系统中自带的图片剪裁
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CLIP);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageDrawable(drawable);
		}
	}

	private void showDialog() {
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
		Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		open_albums = (Button) view.findViewById(R.id.albums);
		open_albums.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				
				getPicFromPhoto();
				Log.i("调用相册","yes");
			}
		});
		
		take_pictures = (Button) view.findViewById(R.id.take_pictures);
		take_pictures.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				
				getPicFromCamera();
				Log.i("调用相机","yes");
			}
		});
		//cancel = (Button) view.findViewById(R.id.cancel);
		//cancel.setOnClickListener(this);
	
		dialog.show();
		

	}
}
