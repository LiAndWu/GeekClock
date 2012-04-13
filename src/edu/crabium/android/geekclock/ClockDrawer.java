package edu.crabium.android.geekclock;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.crabium.android.geekclock.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

public class ClockDrawer {
	private Context context;
	
	public ClockDrawer(Context context){
		this.context = context;
	}
	
	public void Draw(ImageView imageView, long secondsSinceEpoch){
		int heightOffSet = 5;
		
		int width = imageView.getLayoutParams().width;
		int height = imageView.getLayoutParams().height;
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888 );   
		Bitmap bitmapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		
        Canvas canvas = new Canvas(bitmap);    
        canvas.drawColor(Color.TRANSPARENT);    
            
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        int centerX = width/2;
        int centerY = height/2;
        int radius = width>height ? height/2 : width/2;
        
        float scaleWidth = ((float) 2*radius) / bitmapBackground.getWidth();
        float scaleHeight = ((float) 2*radius) / bitmapBackground.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap background = Bitmap.createBitmap(bitmapBackground, 0, 0,  bitmapBackground.getWidth(), bitmapBackground.getHeight(), matrix, true);
        
        canvas.drawBitmap(background,(float)(centerX - 0.5*background.getHeight()), (float)(centerY - 0.5*background.getWidth()) + heightOffSet , paint);

        double hourX = Math.cos(calculateDegreeHour(getHour(secondsSinceEpoch)))*radius*0.4;
        double hourY = Math.sin(calculateDegreeHour(getHour(secondsSinceEpoch)))*radius*0.4;
        paint.setStrokeWidth(4);
        canvas.drawLine(centerX, (float)centerY + heightOffSet,(float)(centerX + hourX), (float)(centerY - hourY) + heightOffSet, paint);

        double minuteX = Math.cos(calculateDegreeMinute(getMinute(secondsSinceEpoch))) * radius*0.6;
        double minuteY = Math.sin(calculateDegreeMinute(getMinute(secondsSinceEpoch))) * radius*0.6;
        paint.setStrokeWidth(2);
        canvas.drawLine(centerX, (float)centerY + heightOffSet,(float)(centerX + minuteX), (float)(centerY - minuteY) + heightOffSet, paint);

        double secondX = Math.cos(calculateDegreeSecond(getSecond(secondsSinceEpoch)))* radius*0.8;
        double secondy = Math.sin(calculateDegreeSecond(getSecond(secondsSinceEpoch)))* radius*0.8;
        paint.setStrokeWidth(1);
        canvas.drawLine(centerX, (float)centerY + heightOffSet,(float)(centerX + secondX), (float)(centerY - secondy) + heightOffSet, paint);
        imageView.setImageBitmap(bitmap);
	}
	
	private double calculateDegreeHour(double hour){
		return Math.toRadians((90 - hour*30));
	}
	
	private double calculateDegreeMinute(double minute){
		return Math.toRadians((90 - minute*6));
	}
	
	private double calculateDegreeSecond(double second){
		return Math.toRadians((90 - second*6));
	}
	private double getHour(long secondsSinceEpoch){
		return Double.parseDouble(new SimpleDateFormat("hh").format(new Date(secondsSinceEpoch*1000)));
	}
	
	private double getMinute(long secondsSinceEpoch){
		return Double.parseDouble(new SimpleDateFormat("mm").format(new Date(secondsSinceEpoch*1000)));
	}
	
	private double getSecond(long secondsSinceEpoch){
		return Double.parseDouble(new SimpleDateFormat("ss").format(new Date(secondsSinceEpoch*1000)));
	}
}
