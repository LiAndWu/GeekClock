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
	protected void onCreate(Bundle savedInstanceState) {
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
    protected void onDestroy(){
    	super.onDestroy();
        Intent intent = new Intent(this, TimeService.class);
        stopService(intent);
    }
    
    private View composeLayout(String str, int id){
    	LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine(true);
        textView.setText(str);
        layout.addView(textView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        layout.addView(imageView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        return layout;
    }
    
}