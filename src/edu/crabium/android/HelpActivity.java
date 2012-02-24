package edu.crabium.android;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	private TextView m_HelpActivityText;
	private Button m_BackButton;
	private LinearLayout layout;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		layout = (LinearLayout) findViewById(R.id.LinearLayout);
		layout.setBackgroundColor(Color.BLACK);
		
		m_HelpActivityText = (TextView) findViewById(R.id.helpText);      
        m_HelpActivityText.setText(
   
        		  "\n\tӦ��˵����\n" +
        		  "\t1)\t��׼ʱ�䣺��ʾUTC(�����׼ʱ��), ����\n\t��ͬ��,��Ҳ���õ����ֻ�ʱ�䲻׼��,��.\n" +
        		  "\t2)\t������Ϣ���鿴��ǰ����,γ��,���ڵ���Ϣ.\n" +
        		  "\t3)\t���ã����ø������.\n" +
        		  "\t4)\t����Ƶ�ʣ���ȡ��������ݵ�Ƶ�ʡ�\n" +
        		  "\t5)\tʱ����������ṩ���ʱ��������Թ�ѡ\n\t��\n" +
        		  "\t6)\tע�᣺��ȡ���ݣ���ҪGeonames�˻���\n\tGeoNames��һ����ѵ�ȫ��������ݿ⡣\n" +
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
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(HelpActivity.this, SetActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				HelpActivity.this.finish();
			}
		});
	}
	
}
