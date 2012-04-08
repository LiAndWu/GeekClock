package edu.crabium.android.geekclock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingProvider {
	private static final String MISC_TABLE_NAME = "misc";
	private static final String SERVER_TABLE_NAME = "server";
	
	private SettingProvider(){
		File database = new File("/data/data/edu.crabium.android.geekclock/geekclock.sqlite3");
		if(!database.exists()){
			//TODO move default database from assets/ to geek clock folder
		}
	}
	
	private static final SettingProvider INSTANCE = new SettingProvider();
	public SettingProvider getInstance(){
		return INSTANCE;
	}

	private SQLiteDatabase openDatabase(){
		final String DATABASE_NAME = "/data/data/edu.crabium.android.geekclock/geekclock.sqlite3";
		return SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
	}
	
	public String getSetting(String key){
		String MISC_GET_VALUE = "SELECT key, value FROM " + MISC_TABLE_NAME + " WHERE key =";
		
		SQLiteDatabase DB = openDatabase();
		Cursor cursor = DB.rawQuery(MISC_GET_VALUE + "\"" + key + "\"", null);
		
		String result = " ";
		if(cursor.getCount() > 0){
			cursor.moveToNext();
			result = cursor.getString(cursor.getColumnIndex("value"));
		}
		cursor.close();
		DB.close();
		return result;
	}
	
	public void addSetting(String key, String value){
		String MISC_INSERT_VALUE = "INSERT INTO " + MISC_TABLE_NAME + " (key, value) VALUES ";
		
		deleteSetting(key);
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(MISC_INSERT_VALUE +"(\"" + key + "\", \"" + value + "\")");
		DB.close();
	}
	
	private void deleteSetting(String key){
		String sql_text = "DELETE FROM " + MISC_TABLE_NAME + " WHERE key=\"" + key + "\"";
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(sql_text);
		DB.close();
	}
	
	public Map<String, String> getServers(){
		SQLiteDatabase DB = openDatabase();

		Map<String, String> map = new HashMap<String, String>();
		Cursor cursor = DB.rawQuery("SELECT server_name, server_address FROM " + SERVER_TABLE_NAME , null);
		while(cursor.moveToNext()){
			map.put(cursor.getString(0), cursor.getString(1));
		}
		cursor.close();
		DB.close();
		return map;
	}
	
	public void addServer(String serverName, String serverAddress){
		String sql_text = "INSERT INTO " + SERVER_TABLE_NAME + " (server_name, server_address) VALUES ";
		
		deleteServer(serverName);
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(sql_text +"(\"" + serverName + "\", \"" + serverAddress + "\")");
		DB.close();
	}
	
	private void deleteServer(String serverName){
		String sql_text = "DELETE FROM " + SERVER_TABLE_NAME + " WHERE server_name=\"" + serverName + "\"";
		SQLiteDatabase DB = openDatabase();
		DB.execSQL(sql_text);
		DB.close();
	}
}
