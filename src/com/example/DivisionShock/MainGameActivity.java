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
import android.util.DisplayMetrics;
import android.view.KeyEvent;


public class MainGameActivity extends Activity implements OnEndOfGameInterface{
	
	private static final int APP_EXIT = 0;
	public static  MediaPlayer mp = null;
	GeneralData app;
	GameView mView;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mp = MediaPlayer.create(this, R.raw.game_music);
		mp.setLooping(true);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		app = (GeneralData)getApplication();
		setContentView(R.layout.activity_main_game);	
        DisplayMetrics dm = new DisplayMetrics(); 
            getWindowManager().getDefaultDisplay().getMetrics(dm);
        	mView = new GameView(this, dm.widthPixels, dm.heightPixels,app.getLevel()); 
        	mView.setOnEndOfGame(this); 
        	setContentView(mView);  
        	overridePendingTransition(R.anim.alphain, R.anim.alphaout);   
	}
	
	
	@Override
	protected void onStart() {
		// TODO �Զ����ɵķ������
		mp.start();
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO �Զ����ɵķ������
		mp.pause();
		super.onStop();	
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
	            return new AlertDialog.Builder(MainGameActivity.this)  
	                    .setMessage("�Ƿ�Ҫ�ǳ�DShockersϵͳ?")  
	                    .setTitle("����ϵͳ�ǳ�����")  
	                    .setPositiveButton("ȷ�ϵǳ�",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                public void onClick(DialogInterface dialog,  
	                                        int which) {  
	                                    dialog.dismiss(); 
	                                    finish();  
	                                    overridePendingTransition(R.anim.alphain, R.anim.alphaout); 
	                                }  
	                            })  
	                    .setNegativeButton("����ϵͳ",  
	                            new DialogInterface.OnClickListener() {  
	  
	                                public void onClick(DialogInterface dialog,  
	                                        int which) {  
	                                    dialog.dismiss();  
	  
	                                }  
	                            }).create();  
	  
	        }  
	        return null;  
	  
	    }


	@Override
	public void onEndOfGame() {
		// TODO �Զ����ɵķ������
		this.finish();
		overridePendingTransition(R.anim.alphain, R.anim.alphaout);
	}  
	
}
