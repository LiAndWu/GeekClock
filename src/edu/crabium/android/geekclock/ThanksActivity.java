package edu.crabium.android.geekclock;

import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ThanksActivity extends Activity {
	
	private Button backButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thanks);
		
		backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ThanksActivity.this, PreferencesActivity.class);
				startActivity(intent);
				ThanksActivity.this.finish();
			}
		});
	}
}
