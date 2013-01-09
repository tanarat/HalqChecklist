package org.silk.checklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SheetListActivity extends Activity {

	SheetListAdapter sheetListAdapter;
	ListView lvSheetList;
//	Option menu
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet_list);
		
		lvSheetList = (ListView)findViewById(R.id.listView1);

		
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sheetListAdapter = new SheetListAdapter(getApplicationContext());
		lvSheetList.setAdapter(sheetListAdapter);
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sheet_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int selectedMenuItem = item.getItemId();
		switch(selectedMenuItem){
			case R.id.action_new:
				Intent intent = new Intent(SheetListActivity.this, SheetDetailActivity.class);
				startActivity(intent);
		}
		return true;
	}

}
