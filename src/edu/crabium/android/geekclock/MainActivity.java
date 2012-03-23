package edu.crabium.android.geekclock;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.crabium.android.geekclock.R;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class MainActivity extends TabActivity {
    /** Called when the activity is first created. */
    private static boolean initialized = false;
    
    public TabWidget tw;
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TabHost mTabHost = this.getTabHost();
       // mTabHost.setBackgroundColor(Color.argb(0, 83, 74, 70));
        
        if(initialized == false){
        	initialized = true; 
        	@SuppressWarnings("unused")
			int tmp;
        try 
        {
			FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
			fileInputStream.close();
		} 
        catch (FileNotFoundException e) 
		{
        	AssetManager assets = getAssets();
        	try 
        	{
				FileOutputStream fileOutputStream =this.openFileOutput("geekclock.xml",Context.MODE_PRIVATE);
				InputStream fileInputStream = assets.open("geekclock.xml");

				byte[] buffer = new byte[1024];
				int length = -1;
				while((length = fileInputStream.read(buffer)) != -1 )
				{
				    fileOutputStream.write(buffer, 0, length);
				}

				fileOutputStream.flush();
				fileOutputStream.close();
			} 
        	catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}    			
		} catch (IOException e) {
			e.printStackTrace();
		}
        }
        

        Log.d("step11","func");
        mTabHost.addTab(mTabHost.newTabSpec("tab1")
        		//.setIndicator("更多信息",getResources().getDrawable(R.drawable.clock))
        		.setIndicator(composeLayout("标准时间", R.drawable.clock))
        		.setContent(new Intent(this,  ShowTimeActivity.class)));
       
        mTabHost.addTab(mTabHost.newTabSpec("tab2")
        		.setIndicator(composeLayout("更多信息", R.drawable.more))
        		.setContent(new Intent(this,  MoreActivity.class)));
        
        mTabHost.addTab(mTabHost.newTabSpec("tab3")
        		.setIndicator(composeLayout("设置", R.drawable.thanks))
        		.setContent(new Intent(this,  SetActivity.class)));   
        
        Log.d("step12","func");
    }
    
    public View composeLayout(String s, int i){
    	LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setText(s);
        layout.addView(tv, 
        		new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        
        ImageView iv = new ImageView(this);
        iv.setImageResource(i);
        layout.addView(iv, 
        		new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        return layout;
    }
}