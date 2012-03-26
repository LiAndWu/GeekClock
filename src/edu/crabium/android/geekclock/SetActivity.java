package edu.crabium.android.geekclock;


import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class SetActivity extends Activity{
	private Button m_HomepageButton;
	private LinearLayout  m_ReadFrequencyButton, m_TimeServerButton, m_RegisterButton, 
		m_HelpButton, m_ThanksButton, m_AboutButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		m_HomepageButton = (Button)findViewById(R.id.homepageButton);
		m_HomepageButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, MainActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});	
		
		m_ReadFrequencyButton = (LinearLayout)findViewById(R.id.readFrequencyButton);
		m_ReadFrequencyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, ReadFrequencyActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});

		m_TimeServerButton = (LinearLayout)findViewById(R.id.timeServerButton);
		m_TimeServerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, TimeServerListActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});
		
		m_RegisterButton = (LinearLayout)findViewById(R.id.registerButton);
		m_RegisterButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, GeonamesRegisterActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});
		
			
		m_HelpButton = (LinearLayout)findViewById(R.id.helpButton);
		m_HelpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, HelpActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});
		
		m_ThanksButton = (LinearLayout)findViewById(R.id.thanksButton);
		m_ThanksButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, ThanksActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});	

		m_AboutButton = (LinearLayout)findViewById(R.id.aboutButton);
		m_AboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetActivity.this, AboutActivity.class);
				startActivity(intent);
				SetActivity.this.finish();
			}
		});	
	}
}
		
