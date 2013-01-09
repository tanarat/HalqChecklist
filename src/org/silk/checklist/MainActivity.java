package org.silk.checklist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	User user;
	MyApp myapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myapp = (MyApp)getApplication();
		
		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		user = myapp.getUser();
//		if(user==null){
//			finish();
//			Intent intent = new Intent(this, LoginActivity.class);
//			startActivity(intent);
//		}else{


			setContentView(R.layout.activity_main);
//			String userName = getResources().getString(R.string.user_name);
//			TextView tvUserName = (TextView)findViewById(R.id.tvUserName);
//			tvUserName.setText(userName + " : " + myapp.getUser().getUserName());
			
//			myapp.getChecklist();
			
//		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.menu_logout:
	        	myapp.logout();
	        	finish();
	        	Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
	        	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent.putExtra("EXIT", true);
	        	startActivity(intent);
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void menuSheetList(View view){
		Intent intent = new Intent(getApplicationContext(), SheetListActivity.class);
		startActivity(intent);
	}
	public void menuNewSheet(View view){
		Intent intent = new Intent(getApplicationContext(), SheetDetailActivity.class);
		startActivity(intent);
	}
}
