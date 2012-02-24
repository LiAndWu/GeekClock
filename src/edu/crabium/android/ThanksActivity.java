package edu.crabium.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThanksActivity extends Activity {
	
	private Button m_BackButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thanks);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(ThanksActivity.this, SetActivity.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				ThanksActivity.this.finish();
			}
		});
	}
}
