package edu.crabium.android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	
	private static boolean firstSet = true;
	private static int previousArg = 0;
	
	String []ServerNameList = null;
	String []ServerAddressList = null;
	
	private int ServerSelectedTimes = 0;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_server_list);
		
		AssetManager assets = getAssets();
        SAXBuilder saxBuilder = new SAXBuilder();
        try 
        {
        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
			Document document = saxBuilder.build(fileInputStream);
			fileInputStream.close();
			
			Element root = document.getRootElement();
			Element NTPServer = root.getChild("NTPServer");
			
			//���ѡ�з���������Ϣ 
		    List servers = NTPServer.getChildren("Server"); 
		    ServerNameList = new String[servers.size()];
		    ServerAddressList = new String[servers.size()];
		    Iterator<Element> iterator = servers.iterator();
		    int i = 0;
		    while(iterator.hasNext())
		    {
		    	Element server = iterator.next();

		      ServerNameList[i] = server.getChild("Name").getValue();
		        ServerAddressList[i] = server.getChild("Address").getValue();
		        i++;
		   }
		} 
        
        catch (IOException e1) 
		{
			e1.printStackTrace();
		} 
        catch (JDOMException e) 
		{
			e.printStackTrace();
		}
        
        
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// �½�һ��Intent���� 
				Intent intent = new Intent();
				// ָ��intentҪ�������� 
				intent.setClass(TimeServerListActivity.this, SetActivity.class);
				// ����һ���µ�Activity 
				startActivity(intent);
				// �رյ�ǰ��Activity 
				TimeServerListActivity.this.finish();
			}
		});
		
		
		m_TimeServerTextView = (TextView) findViewById(R.id.timeServerTextView);
		m_TimeServerSpinner = (Spinner) findViewById(R.id.timeServerSpinner);

		//����ѡ������ArrayAdapter����
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ServerNameList);

		//���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//��adapter��ӵ�m_Spinner��
		m_TimeServerSpinner.setAdapter(adapter);

		//���Spinner�¼�����
		m_TimeServerSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				ServerSelectedTimes++;
				
				if(ServerSelectedTimes > 1)
				{
					//д������
			        SAXBuilder saxBuilder = new SAXBuilder();
			        try 
			        {
			        	FileInputStream fileInputStream  = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
						Document document = saxBuilder.build(fileInputStream);
						fileInputStream.close();
						
						Element root = document.getRootElement();
						Element NTPServer = root.getChild("NTPServer");
						Element Chosen = NTPServer.getChild("Chosen");
						Chosen.getChild("Name").setText(ServerNameList[arg2]);
						Chosen.getChild("Address").setText(ServerAddressList[arg2]);
						
						TimeProvider.SetServerName(ServerNameList[arg2]);
						TimeProvider.SetServerAddress(ServerAddressList[arg2]);
						
						
						XMLOutputter out = new XMLOutputter();
						FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
						out.output(document,fileOutputStream);
						fileOutputStream.flush();
						fileOutputStream.close();
						
					} catch(IOException e)
					{
						
					} catch (JDOMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					
				}
				m_TimeServerTextView.setText("���ڵ�ʱ��������ǣ�\n" + TimeProvider.GetServerName());
				//������ʾ��ǰѡ�����
				arg0.setVisibility(View.VISIBLE);
				
				Log.d(String.valueOf(ServerSelectedTimes),"func");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}
}
