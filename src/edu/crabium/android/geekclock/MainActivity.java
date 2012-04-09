package edu.crabium.android.geekclock;

import edu.crabium.android.geekclock.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        TabHost tabHost = this.getTabHost();
        SettingProvider.SetContext(this);
        
        tabHost.addTab(tabHost.newTabSpec("tab1")
        		.setIndicator(composeLayout("标准时间", R.drawable.clock))
        		.setContent(new Intent(this,  ShowTimeActivity.class)));
       
        tabHost.addTab(tabHost.newTabSpec("tab2")
        		.setIndicator(composeLayout("更多信息", R.drawable.more))
        		.setContent(new Intent(this,  MoreActivity.class)));
        
        tabHost.addTab(tabHost.newTabSpec("tab3")
        		.setIndicator(composeLayout("设置", R.drawable.thanks))
        		.setContent(new Intent(this,  PreferencesActivity.class)));   
        
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
        Intent intent = new Intent(this, TimeService.class);
        stopService(intent);
    }
    
    public View composeLayout(String s, int i){
    	LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setText(s);
        layout.addView(tv, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        
        ImageView iv = new ImageView(this);
        iv.setImageResource(i);
        layout.addView(iv, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        return layout;
    }
    
}