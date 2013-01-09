package org.silk.checklist;

import java.io.IOException;
import java.io.InputStream;

import org.silk.checklist.db.DBHelper;
import org.silk.checklist.db.Dao;
import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.ChecklistUtil;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class MyApp extends Application{

	private Checklist checklist;
	private User user;
	private Dao dao;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		setDao(new Dao(getApplicationContext()));

//		dao.loadSampleData();
		
		
		//load checklist from csv file
		InputStream in;
		try {
			in = getResources().getAssets().open("Checklist.csv");
			checklist = ChecklistUtil.createFromCSV(in);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}


	


	public Checklist getChecklist() {
		return checklist;
	}


	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}


	public Boolean login(String email, String password) {
		// TODO Auto-generated method stub
		try {
			// Simulate network access.
			Thread.sleep(2000);
			if(email.equals("a@a")){
				if(password.equals("123")){
					user = new User();
					user.setUserName("ธนะรัตน์ ฐานิสสะ");
					return true;
				}
			}
		} catch (InterruptedException e) {

		}
		
		return false;
	}
	public User getUser(){
		return user;
	}


	public void logout() {
		// TODO Auto-generated method stub
		user=null;
	}





	public Dao getDao() {
		return dao;
	}





	public void setDao(Dao dao) {
		this.dao = dao;
	}
	

	
}
