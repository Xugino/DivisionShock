package com.example.DivisionShock;

import com.example.hellow.o.r.l.d.R;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends MenuActivity implements OnClickListener{

	private TextView text;
	private ImageView title,st,vt,btn0,btn1,btn2,btn3,btn4;
	private AudioManager mAudioManager=null;
	private Vibrator vibrator=null;
    private GeneralData gd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		initKJ();
		gd=(GeneralData)getApplication();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);    
		gd.setVolume(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)); 
		vibrator=(Vibrator) super.getApplication().getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void initKJ(){
		text= (TextView) findViewById(R.id.version);
		st=(ImageView) findViewById(R.id.stitle);
		vt=(ImageView) findViewById(R.id.vtitle);
		title=(ImageView) findViewById(R.id.title);
		btn0=(ImageView) findViewById(R.id.exitbtn);
		btn0.setOnClickListener(this);
		btn1=(ImageView) findViewById(R.id.sonbtn);
		btn1.setOnClickListener(this);
		
		btn2=(ImageView) findViewById(R.id.soffbtn);
		btn2.setOnClickListener(this);
		
		btn3=(ImageView) findViewById(R.id.vonbtn);
		btn3.setOnClickListener(this);
		
		btn4=(ImageView) findViewById(R.id.voffbtn);
		btn4.setOnClickListener(this);
		
		gd=(GeneralData) getApplication();
		
		if(gd.getSoundsM()){
			btn1.setBackgroundResource(R.drawable.sound_on2);
			btn2.setBackgroundResource(R.drawable.sound_off);
		}
		else
		{
			btn1.setBackgroundResource(R.drawable.sound_on);
			btn2.setBackgroundResource(R.drawable.sound_off2);
		}
		
		if(gd.getVibrationM()){
			btn3.setBackgroundResource(R.drawable.vibration_on2);
			btn4.setBackgroundResource(R.drawable.vibration_off);  
		}
		else{
			btn3.setBackgroundResource(R.drawable.vibration_on);
			btn4.setBackgroundResource(R.drawable.vibration_off2);  
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.equals(btn1) && !gd.getSoundsM()){
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, gd.getVolume(), 0);
			Toast.makeText(getBaseContext(), "Sounds on", Toast.LENGTH_SHORT).show();
			gd.setSoundsM(true);
			btn1.setBackgroundResource(R.drawable.sound_on2);
			btn2.setBackgroundResource(R.drawable.sound_off);
		}
		if(v.equals(btn2) && gd.getSoundsM()){
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
			Toast.makeText(getBaseContext(), "Sounds Off", Toast.LENGTH_SHORT).show();
			gd.setSoundsM(false);
			btn1.setBackgroundResource(R.drawable.sound_on);
			btn2.setBackgroundResource(R.drawable.sound_off2);
		}
		if(v.equals(btn3) && !gd.getVibrationM()){
			vibrator.vibrate(new long[]{0,500}, -1);
			Toast.makeText(getBaseContext(), "Vibration On", Toast.LENGTH_SHORT).show();
			gd.setVibrationM(true);
			btn3.setBackgroundResource(R.drawable.vibration_on2);
			btn4.setBackgroundResource(R.drawable.vibration_off);
		}
		if(v.equals(btn4) && gd.getVibrationM()){
			Toast.makeText(getBaseContext(), "Vibration Off", Toast.LENGTH_SHORT).show();
			gd.setVibrationM(false);
			btn3.setBackgroundResource(R.drawable.vibration_on);
			btn4.setBackgroundResource(R.drawable.vibration_off2);
		}
		if(v.equals(btn0)){	
			finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.exit_from_left); 
		}
	}
	
}
