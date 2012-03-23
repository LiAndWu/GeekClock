package edu.crabium.android.geekclock;


import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	private TextView m_HelpActivityText;
	private Button m_BackButton;
	private LinearLayout layout;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		layout = (LinearLayout) findViewById(R.id.LinearLayout);
		layout.setBackgroundColor(Color.BLACK);
		
		m_HelpActivityText = (TextView) findViewById(R.id.helpText);      
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		if (dm.heightPixels > 600) {
	        m_HelpActivityText.setText(
	   
	        		  "\n\t应用说明：\n" +
	        		  "\t1)\t标准时间：显示UTC(世界标准时间), 与网\n\t\t\t络同步,再也不用担心手机时间不准了,亲.\n" +
	        		  "\t2)\t更多信息：查看当前经度,纬度,星期等信息.\n" +
	        		  "\t3)\t设置：设置各类参数.\n" +
	        		  "\t4)\t反馈频率：读取坐标等数据的频率。\n" +
	        		  "\t5)\t时间服务器：提供多个时间服务器以供选\n\t\t\t择。\n" +
	        		  "\t6)\t注册：读取数据，需要Geonames账户，\n\t\t\tGeoNames是一个免费的全球地理数据库。\n" +
	        		  "\t7)\t帮助：显示此帮助.\n\n" +
	        		  "\t8)\t鸣谢：\n" +
	        		  "\tGeoNames.org :\t时区查询服务\n" +
	        		  "\tNTP.org :\t\t\tNTP协议的开源Java实现\n" +
	        		  "\t时间服务器提供商 :UTC查询服务\n" +
	        		  "\tGoogle.com :\t\t各种资源\n\n"
	      );   
		} else {
	        m_HelpActivityText.setText(
	        		  "\n\t应用说明：\n" +
	        		  "\t1)\t标准时间：显示UTC(世界标准时间), 与网\n\t\t络同步,再也不用担心手机时间不准了,亲.\n" +
	        		  "\t2)\t更多信息：查看当前经度,纬度,星期等信息.\n" +
	        		  "\t3)\t设置：设置各类参数.\n" +
	        		  "\t4)\t反馈频率：读取坐标等数据的频率。\n" +
	        		  "\t5)\t时间服务器：提供多个时间服务器以供选\n\t\t择。\n" +
	        		  "\t6)\t注册：读取数据，需要Geonames账户，\n\t\tGeoNames是一个免费的全球地理数据库。\n" +
	        		  "\t7)\t帮助：显示此帮助.\n\n" +
	        		  "\t8)\t鸣谢：\n" +
	        		  "\tGeoNames.org :\t时区查询服务\n" +
	        		  "\tNTP.org :\t\t\tNTP协议的开源Java实现\n" +
	        		  "\t时间服务器提供商 :UTC查询服务\n" +
	        		  "\tGoogle.com :\t\t各种资源\n\n"
	      );   	
		}
        
        m_BackButton = (Button)findViewById(R.id.backButton);
        m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HelpActivity.this, SetActivity.class);
				startActivity(intent);
				HelpActivity.this.finish();
			}
		});
	}
	
}
