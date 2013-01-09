package org.silk.checklist;

import java.util.List;

import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Item;
import org.silk.widget.CheckboxGroup;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemActivity extends Activity {
	Checklist checklist;
	Item item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);
		long itemId = getIntent().getLongExtra("itemId", -1);
		checklist = ((MyApp)getApplication()).getChecklist();
		item = checklist.getItem(itemId);
		TextView tvItemTitle = (TextView)findViewById(R.id.tvItemTitle);
		tvItemTitle.setText(item.getTitle());
		
		List<String> noReasonList = item.getNoReasons();
		
		CheckboxGroup chkbGroup = new CheckboxGroup(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(     
                RelativeLayout.LayoutParams.MATCH_PARENT, 
                RelativeLayout.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.BELOW,R.id.radioChoices);
		params.leftMargin = 100;
		chkbGroup.setLayoutParams(params);
		
		for (int i = 0; i < noReasonList.size(); i++) {
			chkbGroup.addCheckbox(noReasonList.get(i));
		}
		
		
		RelativeLayout contentView = (RelativeLayout) findViewById(R.id.activity_item_layout);
		contentView.addView(chkbGroup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_item, menu);
		return true;
	}

}
