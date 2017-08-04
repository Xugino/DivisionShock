package com.example.DivisionShock;

import java.io.IOException;
import com.example.hellow.o.r.l.d.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener{
	public static int APP_EXIT=255255;
	private TextView text;
	private ImageView btn0,btn1,btn2,btn3,btn4;
	private GeneralData app;
	public static  MediaPlayer mp = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (GeneralData)getApplication();
		mp = MediaPlayer.create(this, R.raw.menu_music);
		mp.setLooping(true);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		setContentView(R.layout.activity_menu);	
		initKJ();
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		mp.start();
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		mp.pause();
		super.onStop();	
	}
	
	private void initKJ(){
		
		text= (TextView) findViewById(R.id.version);
		
		btn0=(ImageView) findViewById(R.id.title);
		btn1=(ImageView) findViewById(R.id.startbtn);
		btn1.setOnClickListener(this);
		btn2=(ImageView) findViewById(R.id.continuebtn);
		btn2.setOnClickListener(this);
		btn3=(ImageView) findViewById(R.id.optionbtn);
		btn3.setOnClickListener(this);
		btn4=(ImageView) findViewById(R.id.exitbtn);
		btn4.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.equals(btn1)){
			app.setLevel(0);
			Intent intent = new Intent();
			intent.setClass(this, MainGameActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.alphain, R.anim.alphaout); 
		}
		if(v.equals(btn2)){
			Intent intent = new Intent();
			intent.setClass(this, MainGameActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.alphain, R.anim.alphaout); 
		}
		if(v.equals(btn3)){
			Intent intent = new Intent();
			intent.setClass(this, OptionsActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.exit_from_right); 
		}
		if(v.equals(btn4)){
			showDialog(APP_EXIT);
		}
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			 showDialog(APP_EXIT);
		return true;
		}
		 return false;
		}
	
	 @Override  
	    protected Dialog onCreateDialog(int id) {  
	        if (id == APP_EXIT) {  
	            return new AlertDialog.Builder(MenuActivity.this)  
	                    .setMessage("是否要退出DivisionShock?")  
	                    .setTitle("退出游戏")  
	                    .setPositiveButton("确认退出",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                public void onClick(DialogInterface dialog,  
	                                        int which) {  
	                                    dialog.dismiss();  
	                                    android.os.Process  
	                                            .killProcess(android.os.Process  
	                                                    .myPid());  
	                                    finish();  
	  
	                                }  
	                            })  
	                    .setNegativeButton("继续游玩",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                public void onClick(DialogInterface dialog,  
	                                        int which) {  
	                                    dialog.dismiss();  
	  
	                                }  
	                            }).create();  
	  
	        }  
	        return null;  
	  
	    }  

}
