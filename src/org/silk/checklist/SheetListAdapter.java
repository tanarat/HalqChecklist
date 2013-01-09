package org.silk.checklist;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.silk.checklist.db.SheetService;
import org.silk.checklist.model.ChecklistType;
import org.silk.checklist.model.Entrepreneur;
import org.silk.checklist.model.Sheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SheetListAdapter extends BaseAdapter{
	List<Sheet> sheetList;
	SheetService sheetDbService;// = new SheetDbService();
	LayoutInflater inflater;
	MyApp myapp;
	
	public SheetListAdapter(Context context){
		myapp = (MyApp)context.getApplicationContext();
		sheetDbService = new SheetService(myapp.getDao());
		sheetList = sheetDbService.listAllSheets();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sheetList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sheetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return ((Sheet)sheetList.get(position)).getSheetId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Sheet sheet = (Sheet)getItem(position);
		int auditType = sheet.getAuditType();
		ChecklistType checklistType = sheet.getChecklistType();
		String auditTypeStr = Sheet.AUDIT_TYPES[auditType];
		Entrepreneur entrepreneur = sheet.getEntrepreneur();
		
		View rowView = inflater.inflate(R.layout.sheet_row, parent, false);
		TextView tvChecklistName = (TextView)rowView.findViewById(R.id.tvChecklistName);
		tvChecklistName.setText(checklistType.getName() +" [ " + sheet.getAuditor().getName()+ " ]");
		
		
		TextView tvSheetName = (TextView)rowView.findViewById(R.id.tvSheetName);
		tvSheetName.setText(entrepreneur.getCompanyName() + "  [ " + auditTypeStr + " ]");
		
		String format = "dd-MM-yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		TextView tvEvalDate =(TextView)rowView.findViewById(R.id.tvEvalDate);
		tvEvalDate.setText(dateFormat.format(sheet.getDate()) + " [ " + Sheet.TIME_SLOTS[sheet.getTimeSlot()] + " ]");
		return rowView;
	}

	

}
