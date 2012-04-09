package edu.crabium.android.geekclock;

import java.util.Map;

import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeServerListActivity extends Activity {
	private Button backButton;
	private TextView timeServerTextView;
	private Spinner	timeServerSpinner;
	private ArrayAdapter<String>	adapter;
	
	private String[] serverNameList = null;
	private String[] serverAddressList = null;
	private int serverSelectedTimes = 0;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_server_list);
		
		SettingProvider sp = SettingProvider.getInstance();
		Map<String, String> map = sp.getServers();
		serverNameList = new String[map.keySet().size()];
		serverAddressList = new String[map.keySet().size()];
		int i = 0;
		for(String key : map.keySet()){
			serverNameList[i] = key;
			serverAddressList[i] = map.get(key);
			i++;
		}
        
		backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TimeServerListActivity.this, PreferencesActivity.class);
				startActivity(intent);
				TimeServerListActivity.this.finish();
			}
		});
		
		
		timeServerTextView = (TextView) findViewById(R.id.timeServerTextView);
		timeServerSpinner = (Spinner) findViewById(R.id.timeServerSpinner);

		timeServerTextView.setText("现在的时间服务器是：\n" + sp.getSetting(SettingProvider.CHOSEN_SERVER_NAME));
		//将可选内容与ArrayAdapter连接
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serverNameList);

		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//将adapter添加到m_Spinner中
		timeServerSpinner.setAdapter(adapter);

		//添加Spinner事件监听
		timeServerSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				serverSelectedTimes++;
				if(serverSelectedTimes > 1){
					SettingProvider sp = SettingProvider.getInstance();
					sp.addSetting(SettingProvider.CHOSEN_SERVER_NAME, serverNameList[arg2]);
					sp.addSetting(SettingProvider.CHOSEN_SREVER_ADDRESS, serverAddressList[arg2]);
					timeServerTextView.setText("现在的时间服务器是：\n" + serverNameList[arg2]);
					//设置显示当前选择的项
					arg0.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
