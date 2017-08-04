package com.example.DivisionShock;

import android.app.Application;

public class GeneralData extends Application {
	private static boolean vibrationM,soundsM;
	private static int levelNum,Volume;
	public boolean getVibrationM(){
		return vibrationM;
	}
	
	public void setVibrationM(boolean mode){
		vibrationM=mode;
	}
	
	public boolean getSoundsM(){
		return soundsM;
	}
	
	public void setSoundsM(boolean mode){
		soundsM=mode;
	}
	
	public int getLevel(){
		return levelNum;
	}
	
	public void setLevel(int level){
		levelNum=level;
	}
	
	public int getVolume(){
		return Volume;
	}
	
	public void setVolume(int volume){
		Volume=volume;
	}
	
	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		setVibrationM(true);
		setSoundsM(true);
		levelNum=0;
		Volume=15;
		super.onCreate();
		
		
	}
}
