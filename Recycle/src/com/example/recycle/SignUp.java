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
	
	/* ͷ������ */
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
//				Log.i("�������","yes");
//			}
//		});
		
		btn_ok= (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				Log.i("ע���û���", et_username.getText().toString());
				if(et_username.getText().toString().equals("�ǳ�")==true){
					Toast.makeText(SignUp.this, "�ǳ�Ϊ��",
							Toast.LENGTH_LONG).show();
				}
				else if(et_realname.getText().equals("��ʵ����")) {
					Toast.makeText(SignUp.this, "����Ϊ��",
							Toast.LENGTH_LONG).show();
			
				}
				else if(et_password.getText().equals("����"))
				{
					Toast.makeText(SignUp.this, "����Ϊ��",
							Toast.LENGTH_LONG).show();
				}
				else {
					
					Toast.makeText(SignUp.this, "ע��ɹ�",
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
//			Log.i("�������","yes");
//			break;
//			
//		case R.id.take_pictures:
//			getPicFromCamera();
//			Log.i("�������","yes");
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
		// �������ָ������������պ����Ƭ�洢��·��
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
			case -1:// -1��ʾ���ճɹ�
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
		// ����ϵͳ���Դ���ͼƬ����
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CLIP);
	}

	/**
	 * ����ü�֮���ͼƬ����
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
		// ������ʾ����
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// ������������Ϊ�˱�֤��ť����ˮƽ����
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// ������ʾλ��
		dialog.onWindowAttributesChanged(wl);
		// ���õ����Χ��ɢ
		dialog.setCanceledOnTouchOutside(true);
		open_albums = (Button) view.findViewById(R.id.albums);
		open_albums.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				
				getPicFromPhoto();
				Log.i("�������","yes");
			}
		});
		
		take_pictures = (Button) view.findViewById(R.id.take_pictures);
		take_pictures.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View textEntryView) {
				
				getPicFromCamera();
				Log.i("�������","yes");
			}
		});
		//cancel = (Button) view.findViewById(R.id.cancel);
		//cancel.setOnClickListener(this);
	
		dialog.show();
		

	}
}
