package edu.crabium.android;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
        m_HelpActivityText.setText(
   
        		  "\n\tӦ��˵����\n" +
        		  "\t1)\t��׼ʱ�䣺��ʾUTC(�����׼ʱ��), ����\n\t\t\t��ͬ��,��Ҳ���õ����ֻ�ʱ�䲻׼��,��.\n" +
        		  "\t2)\t������Ϣ���鿴��ǰ����,γ��,���ڵ���Ϣ.\n" +
        		  "\t3)\t���ã����ø������.\n" +
        		  "\t4)\t����Ƶ�ʣ���ȡ��������ݵ�Ƶ�ʡ�\n" +
        		  "\t5)\tʱ����������ṩ���ʱ��������Թ�ѡ\n\t\t\t��\n" +
        		  "\t6)\tע�᣺��ȡ���ݣ���ҪGeonames�˻���\n\t\t\tGeoNames��һ����ѵ�ȫ��������ݿ⡣\n" +
        		  "\t7)\t��������ʾ�˰���.\n\n" +
        		  "\t8)\t��л��\n" +
        		  "\tGeoNames.org :\tʱ����ѯ����\n" +
        		  "\tNTP.org :\t\t\tNTPЭ��Ŀ�ԴJavaʵ��\n" +
        		  "\tʱ��������ṩ�� :UTC��ѯ����\n" +
        		  "\tGoogle.com :\t\t������Դ\n\n"
      );   
        
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
