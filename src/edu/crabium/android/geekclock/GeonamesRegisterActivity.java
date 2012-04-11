package edu.crabium.android.geekclock;

import edu.crabium.android.geekclock.R;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.Window;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GeonamesRegisterActivity extends Activity {
	private EditText usernameExitText;
	private TextView showUsernameTextView;
	private String inputRecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geonames_register);
		
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				GeonamesRegisterActivity.this.finish();
			}
		});
		
		showUsernameTextView = (TextView)findViewById(R.id.showUsernameTextView);
		SettingProvider sp = SettingProvider.getInstance();
		String geoNamesUserName = sp.getSetting(SettingProvider.GEONAMES_USER_NAME);
		if(geoNamesUserName == "tikiet") {
			showUsernameTextView.setText("\t现在使用的账户是公用账户, 查询次数有限, 建议注册私人账号.");
		}
		else {
			showUsernameTextView.setText("正在使用的账户是:\n" + geoNamesUserName);
		}
		
		
		usernameExitText = (EditText)findViewById(R.id.usernameEditText);
		usernameExitText.setHint("输入用户名：");
		usernameExitText.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				inputRecord = usernameExitText.getText().toString();
				DisplayToast( inputRecord.length() >= 25 ? "用户名输入过长！！" : "用户名是:\n" + inputRecord);
				return false;
			}
		});
		
		
		Button confirmButton = (Button)findViewById(R.id.confirmButton);
		confirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				inputRecord = usernameExitText.getText().toString();
				if (inputRecord == null || inputRecord.trim().equals("")) {
					DisplayToast("骚年，请正确输入！！");
				}
				else {			
					DisplayToast("输入成功！！");
					showUsernameTextView.setText("正在使用的账户是:：\n" + inputRecord);
					usernameExitText.setFocusable(false);
					usernameExitText.setFocusableInTouchMode(false);

					SettingProvider sp = SettingProvider.getInstance();
					sp.addSetting(SettingProvider.GEONAMES_USER_NAME, inputRecord);
				}
			}
		});
		
		ImageView registerImageView = (ImageView)findViewById(R.id.registerImageView);
		registerImageView.setOnClickListener(new View.OnClickListener(){
	           @Override
			public void onClick(View v){
	               Intent intent = new Intent();
	               intent.setAction(Intent.ACTION_VIEW);
	               intent.addCategory(Intent.CATEGORY_BROWSABLE);
	               intent.setData(Uri.parse("http://www.geonames.org/login"));
	               startActivity(intent);
	           }
	       });  
	}
	
	private void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
