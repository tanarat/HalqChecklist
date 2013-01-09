package org.silk.checklist;

import java.util.List;
import java.util.Map;

import org.silk.checklist.model.Checklist;
import org.silk.checklist.model.Entrepreneur;
import org.silk.checklist.model.Group;
import org.silk.checklist.model.Item;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntrepreneurAdapter extends BaseAdapter{
	private Activity activity;
	private List<Entrepreneur> entrepreneurList;
	
	
	public EntrepreneurAdapter(Activity activity){
		this.activity = activity;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entrepreneurList.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return entrepreneurList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		
		return ((Entrepreneur)getItem(position)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
//		LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
//		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View rowView = inflater.inflate(R.layout.item_row, parent, false);
//		TextView tvItemTitle = (TextView)rowView.findViewById(R.id.tvItemTitle);
//		TextView tvItemKey = (TextView)rowView.findViewById(R.id.tvItemKey);
//		TextView tvSubGroup = (TextView)rowView.findViewById(R.id.tvItemGroup);
//		
//
//		Item item = (Item)getItem(position);
//		tvItemTitle.setText(item.getTitle());
//		tvItemKey.setText(item.getKey());
//		tvSubGroup.setText(item.getSubGroupName());
		return null;
	}
	

}
