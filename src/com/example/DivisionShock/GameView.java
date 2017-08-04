package com.example.DivisionShock;

import com.example.hellow.o.r.l.d.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView implements Callback, Runnable {
	RectF rKeyLeft,rKeyUp;
	RectF rKeyRight;
	Resources res = getResources();
	Bitmap bmp = BitmapFactory. decodeResource (res, R.drawable.user_right );
	Bitmap bmpbg = BitmapFactory.decodeResource(res, R.drawable.bglevel1);
	Bitmap bmp2;
    public int width, height; 
    private Canvas mCanvas;  
    private Thread mThread; 
    private boolean mIsRunning = false; 
    public int level,judge;
    boolean anime=true,maped=false;
    private SurfaceHolder mSurfaceHolder; 
    public int[][] map;
    private long levelStartTime;
    private int levelTime;
    public boolean[][] pass;
    private int TIME_IN_FRAME = 25; 
    MainGameActivity gameActivity; 
    private UserItem user;
	protected OnEndOfGameInterface mOnEndOfGame ;
    public boolean leftKeyDown,rightKeyDown,upKeyDown,drawed=true;
    
    public GameView(Context context, int width, int height,int level) { 
        super(context); 
        setFocusable(true);
        gameActivity = (MainGameActivity) context; 
        this.width = width; 
        this.height = height; 
        this.level=level;
        
        map=new int[height/64+2][width/64+2];
        pass=new boolean[height/64+2][width/64+2];
        user=new UserItem(this);
        rKeyLeft = new RectF(200, height-200, 400, height); 
    	rKeyRight = new RectF(400, height-200, 600,height);
    	rKeyUp = new RectF(width-300, height-200, width-100,height);
        mSurfaceHolder = getHolder(); 
        mSurfaceHolder.addCallback(this); 
        
    } 
 
    
    public void mDraw() { 
    	//bmpbg = Bitmap.createScaledBitmap(bmpbg, width, height, true);
    	//mCanvas.drawBitmap(bmpbg, 0, 0,new Paint());
    	mCanvas.drawColor(Color.WHITE);
    	drawBG(mCanvas); 
        drawGM(mCanvas);
        judge=user.draw(mCanvas,bmp);
        if(judge==1){
        	gameActivity.app.setLevel(++level);
        	user.reset();
        	anime=true;
        	drawed=true;
        } else if(judge==2){
        	bmp2 = BitmapFactory. decodeResource (res, R.drawable.lose );
        	bmp2 = Bitmap.createScaledBitmap(bmp2, width, height, true);
        	mCanvas.drawBitmap(bmp2, 0, 0, new Paint());
        	if(gameActivity.app.getVibrationM()) VibratorUtil.Vibrate(gameActivity, 500);
        	mSurfaceHolder.unlockCanvasAndPost(mCanvas); 
    		long startTime1 = System.currentTimeMillis(); 
        	while(System.currentTimeMillis()-startTime1<1000);
        	mCanvas = mSurfaceHolder.lockCanvas(); 
        	user.reset();
        	drawed=true;
        }
    } 
    
    @Override
	public boolean onTouchEvent(MotionEvent arg0) { 
    	// TODO Auto-generated method stub 
    	int ax = (int) arg0.getRawX(); 
		int ay = (int) arg0.getRawY(); 
    	switch (arg0.getAction()& MotionEvent.ACTION_MASK) {
        	
        	case MotionEvent.ACTION_DOWN:		
        		if (rKeyLeft.contains(ax, ay)) { 
        			leftKeyDown=true;
        			rightKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.run_left );
        		}  else if (rKeyRight.contains(ax, ay)) { 
        			rightKeyDown=true; 
        			leftKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.run_right );
        		}  else if (rKeyUp.contains(ax, ay)) { 
        			upKeyDown=true; 
        		} 
        		break;
        	
        	
        	case MotionEvent.ACTION_POINTER_DOWN:  	
        		
        		//Toast.makeText(getContext(), String.valueOf(arg0.getX(1)) + " " +String.valueOf(arg0.getY(1)), Toast.LENGTH_SHORT).show();
        		
        		ax = (int)arg0.getX(1);
        		ay = (int)arg0.getY(1);
        		
        		if (rKeyUp.contains(ax, ay)) { 
        			upKeyDown=true; 
        		} 
        		break;
        		
        	case MotionEvent.ACTION_MOVE:

        		ax = (int)arg0.getX();
        		ay = (int)arg0.getY();
        		
        		if (!rKeyLeft.contains(ax,ay) && leftKeyDown==true) { 
        			leftKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_left );
        		}
        		else if (!rKeyRight.contains(ax,ay) && rightKeyDown==true) { 
        			rightKeyDown=false; 
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_right );
        			
        		} 
        		
        		if(rKeyRight.contains(ax,ay)){
    				rightKeyDown=true; 
        			leftKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.run_right );
    			}
        		else if(rKeyLeft.contains(ax,ay)){
    				leftKeyDown=true;
        			rightKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.run_left );
    			}
        		break;
        
        	case MotionEvent.ACTION_POINTER_UP:
        		
        		ax = (int)arg0.getX(1);
        		ay = (int)arg0.getY(1);
        		
        		if (rKeyLeft.contains(ax, ay) && leftKeyDown==true) { 
        			leftKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_left );
        		}
        		else if (rKeyRight.contains(ax, ay) && rightKeyDown==true) { 
        			rightKeyDown=false; 
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_right );
        		} 
        		break;
        	 
        	case MotionEvent.ACTION_UP:
        		
        		ax = (int)arg0.getX();
        		ay = (int)arg0.getY();
        		
        		if (rKeyLeft.contains(ax, ay)) { 
        			leftKeyDown=false;
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_left );
        		} else if (rKeyRight.contains(ax, ay)) { 
        			rightKeyDown=false; 
        			bmp = BitmapFactory. decodeResource (res, R.drawable.user_right );
        		} 
        		break;
    	}
    	return true; 
   	}
 
    public void drawGM(Canvas mCanvas){
    	Bitmap bp=BitmapFactory. decodeResource (res, R.drawable.box_normal_b );
    	Bitmap bp5=BitmapFactory. decodeResource (res, R.drawable.door );
    	Bitmap bp6=BitmapFactory. decodeResource (res, R.drawable.box_up_key );
    	Bitmap bp7=BitmapFactory. decodeResource (res, R.drawable.box_normal );
    	/*
    	 * bp:普通方格
    	 * bp5：出门
    	 * bp6:把人向上弹
    	 * bp7:碰到就会死
    	 * bp8:
    	 */
    	int i,x,y,j;
    	if(drawed){
    		for(i=0;i<height/64;i++)
    			for(j=0;j<width/64;j++) map[i][j]=0;
    		if(level==0){
    			for(i=3;i<width/64;i++)map[height/128][i]=1;
    			map[height/128-1][i-4]=2;
    		}
    		if(level==1){
    			for(i=10;i<width/64-8;i+=8) {map[3][i]=1;map[3][i-1]=1;}
    			map[6][7]=1;map[5][5]=1;
    			for(i=0;i<width/64-4;i++)map[8][i]=1;
    			for(j=7;j>2;j--)map[j][--i]=1;
    			for(i=25;i<width/64;i++)map[12][i]=1;
    			for(i=25;i>0;i-=6) {map[12][i]=1;map[12][i+1]=1;}
    			map[11][i+6]=2;
    		}
    		if(level==2){
    			for(i=5;i<height/64-4;i+=3)map[i][2]=1;
    			for(i=3;i<height/64-4;i+=3)map[i][5]=1;
    			for(i=5;i<height/64-4;i+=3)map[i][17]=1;
    			for(i=3;i<height/64-4;i+=3)map[i][20]=1;
    			map[3][29]=2;
    		}
    		if(level==3){
    			for(i=0;i<width/64-4;i++)
    			{
    				if(i%5==2)map[3][i]=3;
    				else map[3][i]=1;
    			}
    			for(i=width/64;i>4;i--)
    			{
    				if(i%5==3)map[7][i]=3;
    				else map[7][i]=1;
    			}
    			for(i=0;i<width/64-4;i++)
    			{
    				if(i%5==4)map[11][i]=3;
    				else map[11][i]=1;
    			}
    			map[11][width/64-4]=2;
    		}
    		if(level==4){
        		for(i=11;i>2;i--){
        			for(j=0;j<4;j++){
        				map[i][(11-i)*3+j]=1;
        			}
        			map[i-1][(11-i)*3+2]=3;
        			map[i-2][(11-i)*3+2]=3;
        		}
        		for(i=4;i<14;i++)map[i][25]=3;
        		for(i=4;i<9;i++)map[i][26]=3;
        		for(i=4;i<8;i++)map[i][27]=3;
        		for(i=11;i<15;i++)map[i][28]=3;
        		for(i=10;i<15;i++)map[i][29]=3;
        		map[13][26]=2;
        	}
    		if(level==5){
    			for(i=0;i<30;i++){
    				if(i%4==3){
    					map[2][i]=3;map[9][i]=4;
    				}
    				else {
    					map[2][i]=3;map[9][i]=1;
    				}
    			}
    			map[6][29]=2;
    		}
    		if(level==6){
        		for(i=3;i<width/64;i++)map[height/128][i]=6;
    			map[height/128-1][i-4]=2;
        	}
    		if(level==7){
        		for(i=3;i<width/64;i++){
        			if(i%7==3 || i%7==6)map[height/128][i]=6;
        		}
    			map[height/128-1][i-4]=2;
        	}
    		if(level==8){
    			levelStartTime = System.currentTimeMillis(); 
    			for(i=3;i<=7;i++)map[height/128-2][i]=1;
    			for(i=7;i<width/64;i++)map[height/128-2][i]=5;
    			for(i=7;i<width/64;i++)map[height/128+6][i]=6;
    			for(i=1;i<8;i++)map[i+height/128-2][7]=6;
    			for(i=1;i<8;i++)map[i+height/128-2][19]=6;
    			map[height/128-3][width/64-4]=2;
    		}
    		drawed=false;
    	}
    	for(x=0;x<width/64;x++)
    		for(y=0;y<height/64;y++){
    			switch(map[y][x]){
    			case 0:
    				pass[y][x]=true;
    				break;
    	    	case 1:
    	    		mCanvas.drawBitmap(bp, x*64, y*64+26, new Paint());
    	    		pass[y][x]=false;
    	    		break;
    	    	case 2:
    	    		mCanvas.drawBitmap(bp5, x*64, y*64+26, new Paint());
    	    		pass[y][x]=true;
    	    		break;
    	    	case 3:
    	    		mCanvas.drawBitmap(bp7, x*64, y*64+26, new Paint());
    	    		pass[y][x]=true;
    	    		break;
    	    	case 4:
    	    		mCanvas.drawBitmap(bp6, x*64, y*64+26, new Paint());
    	    		pass[y][x]=false;
    	    		break;
    	    	case 5:
    	    		mCanvas.drawBitmap(bp, x*64, y*64+26, new Paint());
    	    		pass[y][x]=true;
    	    		break;
    	    	case 6:
    	    		pass[y][x]=false;
    	    		break;
    	    	}
    		}
    }
    public void drawBG(Canvas mCanvas) { 
        Bitmap bp2=BitmapFactory. decodeResource (res, R.drawable.button_jump );
        Bitmap bp3=BitmapFactory. decodeResource (res, R.drawable.button_left );
        Bitmap bp4=BitmapFactory. decodeResource (res, R.drawable.button_right );
        mCanvas.drawBitmap(bp3, 250, height-140, new Paint());
        mCanvas.drawBitmap(bp4, 450, height-140, new Paint());
        mCanvas.drawBitmap(bp2, width-250, height-140, new Paint());
    } 
    
    
    
    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, 
            int height) { 
    } 
 
    @Override
	public void surfaceCreated(SurfaceHolder holder) { 
    	mIsRunning = true; 
        mThread = new Thread(this); 
        mThread.start(); 
    } 
 
    @Override
	public void surfaceDestroyed(SurfaceHolder holder) { 
    	 mIsRunning = false; 
    } 
    
    public void printanime(){
    	bmp2 = Bitmap.createScaledBitmap(bmp2, width, height, true);
     	mCanvas.drawBitmap(bmp2, 0, 0, new Paint());
     	mSurfaceHolder.unlockCanvasAndPost(mCanvas); 
 		long startTime1 = System.currentTimeMillis(); 
     	while(System.currentTimeMillis()-startTime1<2000);
     	mCanvas = mSurfaceHolder.lockCanvas();
    }
 
    @Override
	public void run() { 
    	while (mIsRunning) {
    		if(anime){
        		mCanvas = mSurfaceHolder.lockCanvas(); 
        		if(level==0){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e1 );
        			printanime();
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e2 );
        			printanime();
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e3 );
        			printanime();
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e4 );
        			printanime();
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e5 );
        			printanime();
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level0e6 );
        			printanime();
        		}
        		if(level==1){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level1e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level1e2 );
                 	printanime();
        		}
        		if(level==2){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level2e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level2e2 );
                 	printanime();
        		}
        		if(level==3){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level3e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level3e2 );
                 	printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level3e3 );
                 	printanime();
        		}
        		if(level==4){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level4e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level4e2 );
                 	printanime();
        		}
        		if(level==5){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level5e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level5e2 );
                 	printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level5e3 );
                 	printanime();
        		}
        		if(level==6){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level6e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level6e2 );
                 	printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level6e3 );
                 	printanime();
        		}
        		if(level==7){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level7e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level7e2 );
                 	printanime();
        		}
        		if(level==8){
        			bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e1 );
        			printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e2 );
                 	printanime();
                 	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e3 );
                 	printanime();
        		}
        		anime=false;
        		mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        	}
            long startTime = System.currentTimeMillis(); 
            synchronized (mSurfaceHolder) { 
                mCanvas = mSurfaceHolder.lockCanvas(); 
                mDraw(); 
                mSurfaceHolder.unlockCanvasAndPost(mCanvas); 
            } 
             
            long endTime = System.currentTimeMillis(); 
            
            levelTime = (int) (endTime - levelStartTime);
            if(levelTime>20000 && gameActivity.app.getLevel()==8)
            {
            	mCanvas = mSurfaceHolder.lockCanvas();
            	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e4 );
    			printanime();
             	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e5 );
             	printanime();
             	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e6 );
             	printanime();
             	bmp2=BitmapFactory. decodeResource (res, R.drawable.level8e7 );
    			printanime();
    			mSurfaceHolder.unlockCanvasAndPost(mCanvas);
    			gameActivity.app.setLevel(0);
             	gameActivity.onEndOfGame();
            }
            int diffTime = (int) (endTime - startTime); 
             
            while (diffTime < TIME_IN_FRAME) { 
                diffTime = (int) (System.currentTimeMillis() - startTime); 
                Thread.yield(); 
            } 
            
        } 
    }
    
    public void setOnEndOfGame(OnEndOfGameInterface xOnEndOfGame){  
        mOnEndOfGame = xOnEndOfGame;  
    } 
}
