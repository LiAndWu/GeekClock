package edu.crabium.android;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends Activity {
	private TextView m_AboutActivityText;
	private Button m_BackButton;
	private LinearLayout layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		layout = (LinearLayout) findViewById(R.id.LinearLayout);
		layout.setBackgroundColor(Color.BLACK);
		
		m_AboutActivityText = (TextView) findViewById(R.id.aboutText);      
		m_AboutActivityText.setText(
			 "\n\n\t\t\t    Geek Clock  Version 2.1\n" +
	         "\t\t\t   Released at 19 Oct 2011 \n\n\n" +
					  
   
    		  "\t\t\t ���ߣ�\n"+
    		  "\t\t\t ��ΰ:\n\t\t\t\t\thttp://mindlee.net\n" +
    		  "\t\t\t\t\tchinawelon@gmail.com\n"+
    		  "\t\t\t ����:\n\t\t\t\t\thttp://tikiet.blog.163.com\n" +
    		  "\t\t\t\t\twuxd@me.com\n\n\n" +
    		  "\t\t\tCrabium & Mabbage Workshop\n\t\t\t\t  LiWei and WuXudong\n\t\t\t\t\t\tCopyleft 2011."
    		  );   
        
        m_BackButton = (Button)findViewById(R.id.backButton);
        m_BackButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(AboutActivity.this, SetActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				AboutActivity.this.finish();
			}
		});
	}
	
}

