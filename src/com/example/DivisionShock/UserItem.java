package com.example.DivisionShock;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UserItem
{
    float x, y,x0,y0;
    float maxVerticalS = 24f, gravityA = 1f;//最大速度，重力加速度
    float jumpS=-20f, speed;//起跳速度，当前速度
    float runS=12f;//横向移动速度
    GameView gameView;

    public UserItem(GameView gameView)
    {
        this.gameView = gameView;
        reset();
        speed=0;
    }
    public void reset()
    {
    	switch(gameView.level){
    	case 5:
    		x0=56f;y0=384f;
    		break;
    	case 1:
    		x0=800f;y0=300f;
    		break;
    	case 2:
    		x0=128f;y0=640f;
    		break;
    	case 3:
    		x0=64f;y0=64f;
    		break;
    	case 4:
    		x0=32f;y0=640f;
    		break;
    	case 6:
    		x0=64f;y0=384f;
    		break;
    	default:
    		x0=256f;y0=256f;
    		break;
    	}
    	x=x0;y=y0;speed=0;
    }
    public void findhidings()
    {
    	if(gameView.map[(int)((y-30)/64)][(int)((x+runS)/64+1)]==5)
        	gameView.map[(int)((y-30)/64)][(int)((x+runS)/64+1)]=0;
        if(gameView.map[(int)((y-24)/64+1)][(int)((x+runS)/64+1)]==5)
        	gameView.map[(int)((y-24)/64+1)][(int)((x+runS)/64+1)]=0;
        if(gameView.map[(int)((y-30)/64)][(int)((x-runS)/64)]==5)
        	gameView.map[(int)((y-30)/64)][(int)((x-runS)/64)]=0;
        if(gameView.map[(int)((y-24)/64+1)][(int)((x-runS)/64)]==5)
        	gameView.map[(int)((y-24)/64+1)][(int)((x-runS)/64)]=0;
        if(gameView.map[(int)((y-30)/64)][(int)(x/64+0.5)]==5)
        	gameView.map[(int)((y-30)/64)][(int)(x/64+0.5)]=0;
        if(gameView.map[(int)((y-24)/64+1)][(int)(x/64+0.5)]==5)
        	gameView.map[(int)((y-24)/64+1)][(int)(x/64+0.5)]=0;
        if(gameView.map[(int)(y/64)][(int)((x+runS)/64+1)]==5)
        	gameView.map[(int)(y/64)][(int)((x+runS)/64+1)]=0;
        if(gameView.map[(int)(y/64)][(int)((x-runS)/64)]==5)
        	gameView.map[(int)(y/64)][(int)((x-runS)/64)]=0;
        if(gameView.map[(int)((y-30)/64)][(int)((x+runS)/64+1)]==6)
        	gameView.map[(int)((y-30)/64)][(int)((x+runS)/64+1)]=1;
        if(gameView.map[(int)((y-24)/64+1)][(int)((x+runS)/64+1)]==6)
        	gameView.map[(int)((y-24)/64+1)][(int)((x+runS)/64+1)]=1;
        if(gameView.map[(int)((y-30)/64)][(int)((x-runS)/64)]==6)
        	gameView.map[(int)((y-30)/64)][(int)((x-runS)/64)]=1;
        if(gameView.map[(int)((y-24)/64+1)][(int)((x-runS)/64)]==6)
        	gameView.map[(int)((y-24)/64+1)][(int)((x-runS)/64)]=1;
        if(gameView.map[(int)((y-30)/64)][(int)(x/64+0.5)]==6)
        	gameView.map[(int)((y-30)/64)][(int)(x/64+0.5)]=1;
        if(gameView.map[(int)((y-24)/64+1)][(int)(x/64+0.5)]==6)
        	gameView.map[(int)((y-24)/64+1)][(int)(x/64+0.5)]=1;
        if(gameView.map[(int)(y/64)][(int)((x+runS)/64+1)]==6)
        	gameView.map[(int)(y/64)][(int)((x+runS)/64+1)]=1;
        if(gameView.map[(int)(y/64)][(int)((x-runS)/64)]==6)
        	gameView.map[(int)(y/64)][(int)((x-runS)/64)]=1;
    }
    public int draw(Canvas canvas,Bitmap bmp)
    {
        move();
        canvas.drawBitmap(bmp, x, y, new Paint());
        findhidings();
        if(gameView.map[(int)((y-39)/64)][(int)((x+runS)/64+1)]==2 || 
        gameView.map[(int)((y-39)/64+1)][(int)((x+runS)/64+1)]==2 || 
        gameView.map[(int)((y-24)/64)][(int)((x-runS)/64)]==2 || 
        gameView.map[(int)((y-39)/64+1)][(int)((x-runS)/64)]==2){
        	return 1;
        } else if(y>=gameView.height-128 ||
        gameView.map[(int)((y-30)/64)][(int)((x-12)/64+1)]==3 || 
        gameView.map[(int)((y-24)/64+1)][(int)((x-12)/64+1)]==3 || 
        gameView.map[(int)((y-30)/64)][(int)(x/64)]==3 || 
        gameView.map[(int)((y-24)/64+1)][(int)(x/64)]==3)
        	return 2;
        else return 0;
    }

    public void move()
    {
    	if(y>=gameView.height-64)return;
    	if(x<0) x=0;
    	if (x > gameView.width-64) x = gameView.width-64;
        if(gameView.leftKeyDown==true)
        {
        	if(gameView.pass[(int)((y-20)/64)][(int)((x-runS)/64)]==true && 
        	gameView.pass[(int)((y-39)/64+1)][(int)((x-runS)/64)]==true)
        		x-=runS;
        	else x-=x%64;
        	if(x<32) x=32;
        	if (x > gameView.width-64) x = gameView.width-64;
        }
        else if(gameView.rightKeyDown==true)
        {
            if(gameView.pass[(int)((y-20)/64)][(int)((x+runS)/64+1)]==true && 
            gameView.pass[(int)((y-39)/64+1)][(int)((x+runS)/64+1)]==true)
            	x+=runS;
            else if(x%64!=63)x+=63-x%64;
            if(x<32) x=32;
        	if (x > gameView.width-64) x = gameView.width-64;
        }
        
        speed+=gravityA;

        if (speed > maxVerticalS)
        {
            speed = maxVerticalS;
        }
        
        
        if (gameView.pass[(int)((y-29)/64+1)][(int)(x/64)]==false || 
        gameView.pass[(int)((y-29)/64+1)][(int)(x/64+1)]==false)
        {
            if(gameView.upKeyDown==true) speed+=jumpS;
            if(gameView.map[(int)((y-29)/64+1)][(int)((x-24)/64+1)]==4 || 
            gameView.map[(int)((y-29)/64+1)][(int)((x+12)/64)]==4)
                speed+=3*jumpS;
            else if(speed>0)
            {
                speed=0;
                while(gameView.pass[(int)((y-30)/64+1)][(int)(x/64)]==false || 
                gameView.pass[(int)((y-30)/64+1)][(int)(x/64+1)]==false)
                	y-=0.1f;
            }
        }
        if(gameView.map[(int)((y-29)/64)][(int)(x/64)]==1 || 
        gameView.map[(int)((y-29)/64)][(int)(x/64+1)]==1)
        	if(speed<0){
        	speed=0;
        }
        y = y + speed;
        if(y<32)y=32;
        gameView.upKeyDown=false;
        
        	
    }
}
