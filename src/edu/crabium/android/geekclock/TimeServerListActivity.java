package edu.crabium.android.geekclock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeServerListActivity extends Activity {
	private Button m_BackButton;
	private TextView				m_TimeServerTextView;
	private Spinner					m_TimeServerSpinner;
	private ArrayAdapter<String>	adapter;
	
	String []ServerNameList = null;
	String []ServerAddressList = null;
	
	private int ServerSelectedTimes = 0;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_server_list);
		
		SettingProvider sp = SettingProvider.getInstance();
		Map<String, String> map = sp.getServers();
		ServerNameList = new String[map.keySet().size()];
		ServerAddressList = new String[map.keySet().size()];
		int i = 0;
		for(String key : map.keySet()){
			ServerNameList[i] = key;
			ServerAddressList[i] = map.get(key);
			i++;
		}
        
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TimeServerListActivity.this, SetActivity.class);
				startActivity(intent);
				TimeServerListActivity.this.finish();
			}
		});
		
		
		m_TimeServerTextView = (TextView) findViewById(R.id.timeServerTextView);
		m_TimeServerSpinner = (Spinner) findViewById(R.id.timeServerSpinner);

		m_TimeServerTextView.setText("现在的时间服务器是：\n" + TimeProvider.GetServerName());
		//将可选内容与ArrayAdapter连接
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ServerNameList);

		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将adapter添加到m_Spinner中
		m_TimeServerSpinner.setAdapter(adapter);

		//添加Spinner事件监听
		m_TimeServerSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ServerSelectedTimes++;
				if(ServerSelectedTimes > 1){
					SettingProvider sp = SettingProvider.getInstance();
					sp.addSetting(SettingProvider.CHOSEN_SERVER_NAME, ServerNameList[arg2]);
					sp.addSetting(SettingProvider.CHOSEN_SREVER_ADDRESS, ServerAddressList[arg2]);
					TimeProvider.SetServerName(ServerNameList[arg2]);
					TimeProvider.SetServerAddress(ServerAddressList[arg2]);
						
					m_TimeServerTextView.setText("现在的时间服务器是：\n" + TimeProvider.GetServerName());
					//设置显示当前选择的项
					arg0.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
}
