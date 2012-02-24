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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ReadFrequencyActivity extends Activity{
	private Button m_BackButton;
	private Button m_FiveMinutesButton, m_TenMinutesButton,
	m_HalfHourButton, m_OneHourButton, m_CustomButton;
	private static TextView m_ReadFrequencyTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection_frequency);
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_FiveMinutesButton = (Button)findViewById(R.id.fiveMinutesButton);
		m_TenMinutesButton = (Button)findViewById(R.id.tenMinutesButton);
		m_HalfHourButton = (Button)findViewById(R.id.halfHourButton);
		m_OneHourButton = (Button)findViewById(R.id.oneHourButton);
		m_CustomButton = (Button)findViewById(R.id.customButton);
		m_ReadFrequencyTextView = (TextView)findViewById(R.id.readFrequencytextView);
		
		m_ReadFrequencyTextView.setTextSize(18);
		if (MoreActivity.ReadFrequencyHour == 0) {
			m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ" + MoreActivity.ReadFrequencyMinute + "����һ��");
		} else if (MoreActivity.ReadFrequencyMinute == 0) {
			m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ" + MoreActivity.ReadFrequencyHour + "Сʱһ��");
		} else {
			m_ReadFrequencyTextView.setTextSize(17);
			m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ" + MoreActivity.ReadFrequencyHour + 
					"Сʱ" + MoreActivity.ReadFrequencyMinute + "����һ��");
		}
		
		
		m_BackButton = (Button)findViewById(R.id.backButton);
		m_BackButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(ReadFrequencyActivity.this, SetActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				ReadFrequencyActivity.this.finish();
			}
		});
		
		m_FiveMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 5;
				m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ5����һ��");	
				
				//д������
		        SAXBuilder saxBuilder = new SAXBuilder();
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					Document document = saxBuilder.build(fileInputStream);
					fileInputStream.close();
					
					Element root = document.getRootElement();
					Element GPSConfig = root.getChild("GPSConfig");
					Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
					RefreshFrequency.getChild("Hour").setText(String.valueOf(MoreActivity.ReadFrequencyHour));
					RefreshFrequency.getChild("Minute").setText(String.valueOf(MoreActivity.ReadFrequencyMinute));
					XMLOutputter out = new XMLOutputter();
					FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					out.output(document, fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch(IOException e){
				} catch (JDOMException e){
					e.printStackTrace();
				}
			}
		});
		
		m_TenMinutesButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 10;
				m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ10����һ��");	
				
				//д������
		        SAXBuilder saxBuilder = new SAXBuilder();
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					Document document = saxBuilder.build(fileInputStream);
					fileInputStream.close();
					
					Element root = document.getRootElement();
					Element GPSConfig = root.getChild("GPSConfig");
					Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
					RefreshFrequency.getChild("Hour").setText(String.valueOf(MoreActivity.ReadFrequencyHour));
					RefreshFrequency.getChild("Minute").setText(String.valueOf(MoreActivity.ReadFrequencyMinute));
					XMLOutputter out = new XMLOutputter();
					FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					out.output(document, fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch(IOException e){
				} catch (JDOMException e){
					e.printStackTrace();
				}
			}
		});
		
		m_HalfHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 0;
				MoreActivity.ReadFrequencyMinute = 30;
				m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ30����һ��");	
				
				//д������
		        SAXBuilder saxBuilder = new SAXBuilder();
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					Document document = saxBuilder.build(fileInputStream);
					fileInputStream.close();
					
					Element root = document.getRootElement();
					Element GPSConfig = root.getChild("GPSConfig");
					Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
					RefreshFrequency.getChild("Hour").setText(String.valueOf(MoreActivity.ReadFrequencyHour));
					RefreshFrequency.getChild("Minute").setText(String.valueOf(MoreActivity.ReadFrequencyMinute));
					XMLOutputter out = new XMLOutputter();
					FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					out.output(document, fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch(IOException e){
				} catch (JDOMException e){
					e.printStackTrace();
				}
			}
		});
		
		m_OneHourButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				MoreActivity.ReadFrequencyHour = 1;
				MoreActivity.ReadFrequencyMinute = 0;
				m_ReadFrequencyTextView.setText("���ڵ�ˢ��Ƶ���ǣ�\nÿ1Сʱһ��");	
				
				//д������
		        SAXBuilder saxBuilder = new SAXBuilder();
		        try 
		        {
		        	FileInputStream fileInputStream = new FileInputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					Document document = saxBuilder.build(fileInputStream);
					fileInputStream.close();
					
					Element root = document.getRootElement();
					Element GPSConfig = root.getChild("GPSConfig");
					Element RefreshFrequency = GPSConfig.getChild("RefreshFrequency");
					RefreshFrequency.getChild("Hour").setText(String.valueOf(MoreActivity.ReadFrequencyHour));
					RefreshFrequency.getChild("Minute").setText(String.valueOf(MoreActivity.ReadFrequencyMinute));
					XMLOutputter out = new XMLOutputter();
					FileOutputStream fileOutputStream = new FileOutputStream("/data/data/edu.crabium.android/files/geekclock.xml");
					out.output(document, fileOutputStream);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch(IOException e){
				} catch (JDOMException e){
					e.printStackTrace();
				}
			}
		});
		
		m_CustomButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(ReadFrequencyActivity.this, TurntableActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				ReadFrequencyActivity.this.finish();
			}
		});
		


	}
}
		