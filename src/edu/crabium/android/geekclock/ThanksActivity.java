package edu.crabium.android.geekclock;

import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ThanksActivity extends Activity {
	
	private Button m_BackButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thanks);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ThanksActivity.this, SetActivity.class);
				startActivity(intent);
				ThanksActivity.this.finish();
			}
		});
	}
}
