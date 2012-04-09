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
import android.os.Handler;
import android.widget.ImageView;

public class ClockDrawer {
	Context context;
	
	public ClockDrawer(Context context){
		this.context = context;
	}
	public ClockDrawer(Handler handler) {
	}
	
	public void Draw(ImageView imageView, long SecSinceEpoch){
		
		int HeightOffSet = 5;
		
		int width = imageView.getLayoutParams().width;
		int height = imageView.getLayoutParams().height;
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888 );   
		Bitmap bitmapback = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		
        Canvas canvas = new Canvas(bitmap);    
        canvas.drawColor(Color.TRANSPARENT);    
            
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        int CenterX = width/2;
        int CenterY = height/2;
        int Radius = width>height?height/2:width/2;
        
        SimpleDateFormat dateformat = new SimpleDateFormat("hh");
        String _hour = dateformat.format(new Date(SecSinceEpoch*1000));
        dateformat = new SimpleDateFormat("mm");
        String _minute = dateformat.format(new Date(SecSinceEpoch*1000));
        dateformat = new SimpleDateFormat("ss");
        String _second = dateformat.format(new Date(SecSinceEpoch*1000));
        
        double hour = Double.parseDouble(_hour);
        double minute = Double.parseDouble(_minute);
        double second = Double.parseDouble(_second);
        
        double hour_degree = (90 - hour*30);
        double minute_degree = (90 - minute*6);
        double second_degree = (90 - second*6);
        
        hour_degree = Math.toRadians(hour_degree);
        minute_degree =Math.toRadians(minute_degree);
        second_degree =Math.toRadians(second_degree);
        
        double hourx = Math.cos(hour_degree)*Radius*0.4;
        double houry = Math.sin(hour_degree)*Radius*0.4;
        
        double minutex = Math.cos(minute_degree) * Radius*0.6;
        double minutey = Math.sin(minute_degree) * Radius*0.6;
        
        double secondx = Math.cos(second_degree)* Radius*0.8;
        double secondy = Math.sin(second_degree)* Radius*0.8;
        
        int _bgHeight  = bitmapback.getHeight();
        int _bgWidth = bitmapback.getWidth();
        int bgHeight = 2*Radius;
        int bgWidth = 2*Radius;
        float scaleWidth = ((float) bgWidth) / _bgWidth;
        float scaleHeight = ((float) bgHeight) / _bgHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap background = Bitmap.createBitmap(bitmapback, 0, 0, _bgWidth, _bgHeight, matrix, true);
        
        canvas.drawBitmap(background,(float)(CenterX - 0.5*background.getHeight()), (float)(CenterY - 0.5*background.getWidth()) + HeightOffSet , paint);
        
        paint.setStrokeWidth(4);
        canvas.drawLine(CenterX, (float)CenterY + HeightOffSet,(float)(CenterX + hourx), (float)(CenterY - houry) + HeightOffSet, paint);
        
        paint.setStrokeWidth(2);
        canvas.drawLine(CenterX, (float)CenterY + HeightOffSet,(float)(CenterX + minutex), (float)(CenterY - minutey) + HeightOffSet, paint);
        
        paint.setStrokeWidth(1);
        canvas.drawLine(CenterX, (float)CenterY + HeightOffSet,(float)(CenterX + secondx), (float)(CenterY - secondy) + HeightOffSet, paint);
        imageView.setImageBitmap(bitmap);
        
	}
}
