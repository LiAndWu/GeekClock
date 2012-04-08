package edu.crabium.android.geekclock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

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
	
	private Button m_BackButton, m_ConfirmButton;
	private EditText m_UsernameExitText;
	private TextView m_ShowUsernameTextView;
	private ImageView m_RegisterImageView;
	private String m_InputRecord;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geonames_register);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GeonamesRegisterActivity.this, SetActivity.class);
				startActivity(intent);
				GeonamesRegisterActivity.this.finish();
			}
		});
		
		m_ShowUsernameTextView = (TextView)findViewById(R.id.showUsernameTextView);
		SettingProvider sp = SettingProvider.getInstance();
		String geoNamesUserName = sp.getSetting(SettingProvider.GEONAMES_USER_NAME);
		if(geoNamesUserName == "tikiet") {
			m_ShowUsernameTextView.setText("\t现在使用的账户是公用账户, 查询次数有限, 建议注册私人账号.");
		} else {
			m_ShowUsernameTextView.setText("正在使用的账户是:\n" + geoNamesUserName);
		}
		
		
		m_UsernameExitText = (EditText)findViewById(R.id.usernameEditText);
		m_UsernameExitText.setHint("输入用户名：");
		m_UsernameExitText.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				m_InputRecord = m_UsernameExitText.getText().toString();
				DisplayToast( m_InputRecord.length() >= 25 ? "用户名输入过长！！" : "用户名是:\n" + m_InputRecord);
				return false;
			}
		});
		
		
		m_ConfirmButton = (Button)findViewById(R.id.confirmButton);
		m_ConfirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_InputRecord = m_UsernameExitText.getText().toString();
				if (m_InputRecord == null || m_InputRecord.trim().equals("")) {
					DisplayToast("骚年，请正确输入！！");
				} else {			
					DisplayToast("输入成功！！");
					m_ShowUsernameTextView.setText("正在使用的账户是:：\n" + m_InputRecord);
					m_UsernameExitText.setFocusable(false);
					m_UsernameExitText.setFocusableInTouchMode(false);

					SettingProvider sp = SettingProvider.getInstance();
					sp.addSetting(SettingProvider.GEONAMES_USER_NAME, m_InputRecord);
				}
			}
		});
		
		m_RegisterImageView = (ImageView)findViewById(R.id.registerImageView);
		m_RegisterImageView.setOnClickListener(new View.OnClickListener(){
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
	
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
