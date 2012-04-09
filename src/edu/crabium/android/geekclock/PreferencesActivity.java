package edu.crabium.android.geekclock;


import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class PreferencesActivity extends Activity{
	private Button homepageButton;
	private LinearLayout  readFrequencyButton, timeServerButton, registerButton, 
		helpButton, thanksButton, aboutButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		
		homepageButton = (Button)findViewById(R.id.homepageButton);
		homepageButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});	
		
		readFrequencyButton = (LinearLayout)findViewById(R.id.readFrequencyButton);
		readFrequencyButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, RefreshFrequencyActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});

		timeServerButton = (LinearLayout)findViewById(R.id.timeServerButton);
		timeServerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, TimeServerListActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});
		
		registerButton = (LinearLayout)findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, GeonamesRegisterActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});
		
			
		helpButton = (LinearLayout)findViewById(R.id.helpButton);
		helpButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, HelpActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});
		
		thanksButton = (LinearLayout)findViewById(R.id.thanksButton);
		thanksButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, ThanksActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});	

		aboutButton = (LinearLayout)findViewById(R.id.aboutButton);
		aboutButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PreferencesActivity.this, AboutActivity.class);
				startActivity(intent);
				PreferencesActivity.this.finish();
			}
		});	
	}
}
		
