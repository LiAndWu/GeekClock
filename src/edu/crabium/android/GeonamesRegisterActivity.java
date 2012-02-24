package edu.crabium.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import android.app.Activity;
import android.view.KeyEvent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GeonamesRegisterActivity extends Activity {
	
	private Button m_BackButton, m_ConfirmButton;
	private EditText m_UsernameExitText;
	private TextView m_ShowUsernameTextView, m_HintTextView;
	private ImageView m_RegisterImageView;
	private String m_InputRecord;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geonames_register);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(GeonamesRegisterActivity.this, SetActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				GeonamesRegisterActivity.this.finish();
			}
		});
		
		m_ShowUsernameTextView = (TextView)findViewById(R.id.showUsernameTextView);
		if(TimeProvider.GeoNamesUserName == "tikiet") {
			m_ShowUsernameTextView.setText("\t����ʹ�õ��˻��ǹ����˻�, ��ѯ��������, ����ע��˽���˺�.");
		} else {
			m_ShowUsernameTextView.setText("����ʹ�õ��˻���:\n" + TimeProvider.GeoNamesUserName);
		}
		
		
		m_UsernameExitText = (EditText)findViewById(R.id.usernameEditText);
		m_UsernameExitText.setHint("�����û���");
		m_UsernameExitText.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				m_InputRecord = m_UsernameExitText.getText().toString();
				if(m_InputRecord.length() >= 25) {
					m_HintTextView.setText("�û��������������");
				} else {
					m_HintTextView.setText(m_InputRecord);
				}
				return false;
			}
		});
		
		m_HintTextView = (TextView) findViewById(R.id.hintTextView);
		m_HintTextView.setTextColor(Color.GRAY);
		
		m_ConfirmButton = (Button)findViewById(R.id.confirmButton);
		m_ConfirmButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (m_InputRecord == null) {
					m_HintTextView.setText("ɧ�꣬����ȷ���룡��");
				} else {			
					TimeProvider.GeoNamesUserName = m_InputRecord;
					m_HintTextView.setText("����ɹ�����");
					m_ShowUsernameTextView.setText("����ʹ�õ��˻���:��\n" + TimeProvider.GeoNamesUserName);
					m_UsernameExitText.setFocusable(false);
					m_UsernameExitText.setFocusableInTouchMode(false);
					
					
					//д������
			        SAXBuilder saxBuilder = new SAXBuilder();
			        try 
			        {
			        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
						Document document = saxBuilder.build(fileInputStream);
						fileInputStream.close();
						
						Element root = document.getRootElement();
						Element GeoNamesConfig = root.getChild("GeoNames");
						Element GeoNamesUserName = GeoNamesConfig.getChild("UserName");
						GeoNamesUserName.setText(TimeProvider.GeoNamesUserName);
						XMLOutputter out = new XMLOutputter();
						FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
						out.output(document,fileOutputStream);  
						
						fileOutputStream.flush();
						fileOutputStream.close();
						
					} catch(IOException e){
					} catch (JDOMException e){
						e.printStackTrace();
					}
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
}
