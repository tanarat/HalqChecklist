package org.silk.checklist;

import java.util.Map;

import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Group;
import org.silk.checklist.model.Item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChecklistAdapter extends BaseAdapter{
	private Activity activity;
	private Checklist checklist;
	private Group[] groups;
	private int groupIndex;
	private Map<String, Item> itemMap;
	
	public ChecklistAdapter(Activity activity, int groupIndex){
		this.activity = activity;
		this.groupIndex = groupIndex;
		checklist = ((MyApp)(activity.getApplication())).getChecklist();
		Map<String,Group> groupMap = checklist.getGroups();
		groups = groupMap.values().toArray(new Group[groupMap.size()]);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		itemMap = groups[groupIndex].getItems();
		return itemMap.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Item[] items = itemMap.values().toArray(new Item[itemMap.size()]);
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return ((Item)getItem(position)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_row, parent, false);
		TextView tvItemTitle = (TextView)rowView.findViewById(R.id.tvItemTitle);
		TextView tvItemKey = (TextView)rowView.findViewById(R.id.tvItemKey);
		TextView tvSubGroup = (TextView)rowView.findViewById(R.id.tvItemGroup);
		

		Item item = (Item)getItem(position);
		tvItemTitle.setText(item.getTitle());
		tvItemKey.setText(item.getKey());
		tvSubGroup.setText(item.getSubGroupName());
		return rowView;
	}
	public int getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

}
