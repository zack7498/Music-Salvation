package com.LLC.MusicSelvation;


import com.example.musicsalvationsdkverson.R;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint({ "ViewConstructor", "WrongCall" })
public class MainView extends SurfaceView
implements SurfaceHolder.Callback{

	Bitmap main_back;
	Bitmap main_left;
	Bitmap main_right;
	Bitmap main_title;
	Bitmap main_touchstart;
	int mainFlag=0;

	int toEditView=0;

	int pointx;//觸控到螢幕的x座標
	int pointy;//觸控到螢幕的y座標

	int mtx=640;
	int mty=-200;
	int mty1=360;
	int mty2=200;

	int mtoy=600;
	int mtoa=0;
	int mtoc=20;

	int mlx=-220;
	int mlx1=220-60;
	int mly=360+140;

	int mrx=1280+333;
	int mrx1=1280-333+190;
	int mry=360+140;


	Paint paint;			//畫筆的參考
	int i=0,j=5;
	MainActivity activity;

	public MainView(MainActivity mainActivity) {
		super(mainActivity);
		this.activity = mainActivity;
		this.getHolder().addCallback(this);//設定生命周期回調接口的實現者
		

	}
	
	public Bitmap LoadBitmap(int r){
		return BitmapFactory.decodeResource(getResources(), r);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint();//建立畫筆
		paint.setAntiAlias(true);//開啟抗鋸齒
		main_back=			Graphic.bitSize(LoadBitmap( R.drawable.main_back), Constant.DEFULT_WITH, Constant.DEFULT_HIGHT);
		main_title=			Graphic.bitSize(LoadBitmap( R.drawable.main_title ),730 ,269 );
		main_touchstart=Graphic.bitSize(LoadBitmap( R.drawable.main_touchstart ), 594, 85);
		main_left=			Graphic.bitSize(LoadBitmap( R.drawable.main_left ),(440/2), (583/2));
		main_right=			Graphic.bitSize(LoadBitmap( R.drawable.main_right), (666/2), (644/2));
		Constant.Flag=true;
		new Thread(){
			@SuppressLint("WrongCall")
			public void run()
			{
				while(Constant.Flag){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SurfaceHolder myholder=MainView.this.getHolder();
					Canvas canvas = myholder.lockCanvas();//取得畫布
					onDraw(canvas);
					if(canvas != null){
						myholder.unlockCanvasAndPost(canvas);
					}
				}

			}
		}.start();
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {//重新定義的繪制方法
		if(canvas!=null){
			super.onDraw(canvas);
			canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HIGHT));//只在螢幕範圍內繪制圖片
			canvas.drawColor(Color.BLACK);//界面設定為黑色
			Graphic.drawPic(canvas, main_back, 1280/2, 720/2, 0, 255, paint);//背景

			if(mainFlag==0){
				if(i<250)
					i+=j;//透明度參數
				Graphic.drawPic(canvas, main_title, mtx, mty, 0, i, paint);//Title
				mty=Coordinate.AnalogSpeedMove(mty, mty1);
				if(mty==mty1){
					mtoa+=mtoc;
					Graphic.drawPic(canvas, main_touchstart, 1280/2, mtoy, 0, mtoa, paint);
					if(mtoa>235)
						mtoc=-7;
					if(mtoa<20)
						mtoc=7;
				}

				paint.reset();
			}
			if(mainFlag==1){
				Graphic.drawPic(canvas, main_title, mtx, mty, 0, 255, paint);//Title
				mty=Coordinate.AnalogSpeedMove(mty, mty2);

				Graphic.drawPic(canvas, main_left, mlx, mly, 0, 255, paint);//Left
				mlx=Coordinate.AnalogSpeedMove(mlx, mlx1);

				Graphic.drawPic(canvas, main_right, mrx, mry, 0, 255, paint);//Right
				mrx=Coordinate.AnalogSpeedMove(mrx, mrx1);
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		pointx=(int) event.getX();
		pointy=(int) event.getY();
		if(mainFlag==0){
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN://按下
				mainFlag=1;
				break;
			}
		}
		if(mainFlag==1){
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN://按下
				/*this.toEditView++;
				if(this.toEditView>10){
					Constant.Flag=false;
					activity.changeView(6);
				}*/
				break;
			case MotionEvent.ACTION_UP:
				this.toEditView++;
				if(this.toEditView>2){
					//Constant.Flag=false;
					this.toEditView=1;
					activity.changeView(6);
				}
				break;
			}
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//銷毀時被呼叫
		Constant.Flag=false;
	}


}
