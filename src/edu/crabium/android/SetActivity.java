package edu.crabium.android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SetActivity extends Activity{
	private Button m_HomepageButton;
	private Button  m_ReadFrequencyButton, m_TimeServerButton, m_RegisterButton, 
		m_HelpButton, m_ThanksButton, m_AboutButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		m_HomepageButton = (Button)findViewById(R.id.homepageButton);
		m_HomepageButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, MainActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});	
		
		m_ReadFrequencyButton = (Button)findViewById(R.id.readFrequencyButton);
		m_ReadFrequencyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, ReadFrequencyActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});

		m_TimeServerButton = (Button)findViewById(R.id.timeServerButton);
		m_TimeServerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, TimeServerListActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});
		
		m_RegisterButton = (Button)findViewById(R.id.registerButton);
		m_RegisterButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, GeonamesRegisterActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});
		
			
		m_HelpButton = (Button)findViewById(R.id.helpButton);
		m_HelpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, HelpActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});
		
		m_ThanksButton = (Button)findViewById(R.id.thanksButton);
		m_ThanksButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, ThanksActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});	

		m_AboutButton = (Button)findViewById(R.id.aboutButton);
		m_AboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(SetActivity.this, AboutActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				SetActivity.this.finish();
			}
		});	
	}
}
		