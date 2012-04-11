package edu.crabium.android.geekclock;
import java.util.Map;
import edu.crabium.android.geekclock.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TimeServerListActivity extends Activity {
	private TextView timeServerTextView;
	private String[] serverNameList = null;
	private String[] serverAddressList = null;
	private int serverSelectedTimes = 0;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        
		Button backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TimeServerListActivity.this.finish();
			}
		});
		
		
		timeServerTextView = (TextView) findViewById(R.id.timeServerTextView);
		Spinner timeServerSpinner = (Spinner) findViewById(R.id.timeServerSpinner);

		timeServerTextView.setText("现在的时间服务器是：\n" + sp.getSetting(SettingProvider.CHOSEN_SERVER_NAME));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, serverNameList);

		//设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		timeServerSpinner.setAdapter(adapter);

		timeServerSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				serverSelectedTimes++;
				if(serverSelectedTimes > 1){
					SettingProvider sp = SettingProvider.getInstance();
					sp.addSetting(SettingProvider.CHOSEN_SERVER_NAME, serverNameList[arg2]);
					sp.addSetting(SettingProvider.CHOSEN_SREVER_ADDRESS, serverAddressList[arg2]);
					timeServerTextView.setText("现在的时间服务器是：\n" + serverNameList[arg2]);
					
					arg0.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
