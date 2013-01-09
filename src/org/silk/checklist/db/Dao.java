package org.silk.checklist.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.silk.checklist.ChecklistActivity;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.ChecklistType;
import org.silk.checklist.model.Entrepreneur;
import org.silk.checklist.model.Sheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Columns;
import android.util.Log;

public class Dao {
	String tag = this.getClass().getName();
	SQLiteDatabase db;
	DBHelper dbHelper;

	public Dao(Context context) {
		this.dbHelper = new DBHelper(context);
	}

	public void insert(ChecklistType checklistType) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_CHECKLIST_TYPE_ID, checklistType.getId());
		values.put(DBHelper.COLUMN_CHECKLIST_TYPE_NAME, checklistType.getName());
		long id = db.insert(DBHelper.TABLE_CHECKLIST_TYPE, null, values);
		Log.i(tag, "insert into " + DBHelper.TABLE_CHECKLIST_TYPE + " [" +values.toString() + "]");
		db.close();
	}
	public void insert(Auditor auditor) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_AUDITOR_ID, auditor.getId());
		values.put(DBHelper.COLUMN_AUDITOR_NAME, auditor.getName());
		long id = db.insert(DBHelper.TABLE_AUDITOR, null, values);
		Log.i(tag, "insert into " + DBHelper.TABLE_AUDITOR + " [" +values.toString() + "]");
		db.close();
	}
	public void insert(Entrepreneur entrepreneur) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_ENTREPRENEUR_ID, entrepreneur.getId());
		values.put(DBHelper.COLUMN_COMPANY_NAME, entrepreneur.getCompanyName());
		values.put(DBHelper.COLUMN_ADDRESS, entrepreneur.getAddress());
		long id = db.insert(DBHelper.TABLE_ENTREPRENEUR, null, values);
		Log.i(tag, "insert into " + DBHelper.TABLE_ENTREPRENEUR + " [" +values.toString() + "]");
		db.close();
	}
	public void insert(Sheet sheet) {
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		/* sheetId */
//		 values.put(DBHelper.COLUMN_ENTREPRENEUR_ID, sheet.getSheetId());
		/* evaluation date */
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		values.put(DBHelper.COLUMN_DATE, dateFormat.format(sheet.getDate()));
		/* time slot */
		values.put(DBHelper.COLUMN_TIME_SLOT, sheet.getTimeSlot());
		/* audit type */
		values.put(DBHelper.COLUMN_AUDIT_TYPE, sheet.getAuditType());
		/* auditor id */
		values.put(DBHelper.COLUMN_AUDITOR_ID, sheet.getAuditor().getId());
		/* checklist type */
		values.put(DBHelper.COLUMN_CHECKLIST_TYPE_ID, sheet.getChecklistType().getId());
		/* entrepreneur */
		values.put(DBHelper.COLUMN_ENTREPRENEUR_ID, sheet.getEntrepreneur().getId());
		
		long id = db.insert(DBHelper.TABLE_SHEET, null, values);

		
		sheet.setSheetId(id);
		Log.i(tag, "insert into " + DBHelper.TABLE_SHEET + " [" +values.toString() + "]");
		db.close();
	}
	public List<ChecklistType> getAllChecklistTypes(){
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(DBHelper.TABLE_CHECKLIST_TYPE, DBHelper.COLUMNS_OF_CHECKLIST_TYPE, null, null, null, null, null);
		List<ChecklistType> typeList = new ArrayList<ChecklistType>(); 
		while(c.moveToNext()){
			ChecklistType ch = new ChecklistType();
			ch.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_ID)));
			ch.setName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_NAME)));
			typeList.add(ch);
		}
		db.close();
		return typeList;
	}
	public List<Entrepreneur> getAllEntrepreneurs(){
		db = dbHelper.getReadableDatabase();
		Cursor c = db.query(DBHelper.TABLE_ENTREPRENEUR, DBHelper.COLUMNS_OF_ENTREPRENEUR, null, null, null, null, null);
		List<Entrepreneur> entrepreneurList = new ArrayList<Entrepreneur>(); 
		while(c.moveToNext()){
			Entrepreneur en = new Entrepreneur();
			en.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_ENTREPRENEUR_ID)));
			en.setCompanyName(c.getString(c.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME)));
			en.setAddress(c.getString(c.getColumnIndex(DBHelper.COLUMN_ADDRESS)));
			entrepreneurList.add(en);
		}
		db.close();
		return entrepreneurList;
	}
	
	public List<Sheet> getAllSheets() {
		db = dbHelper.getReadableDatabase();
		
		Cursor c = db.query(DBHelper.TABLE_SHEET, DBHelper.COLUMNS_OF_SHEET, null, null, null, null, DBHelper.COLUMN_SHEET_ID + " DESC");
		List<Sheet> sheetList = new ArrayList<Sheet>(); 
		while(c.moveToNext()){
			Sheet sheet = new Sheet();
//			sheet id
			sheet.setSheetId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_SHEET_ID)));
//			entrepreneur
			long entrepreneurId = c.getLong(c.getColumnIndex(DBHelper.COLUMN_ENTREPRENEUR_ID));
			sheet.setEntrepreneur(getEntrepreneurById(entrepreneurId));
//			evaluation date
			String dateStr = c.getString(c.getColumnIndex(DBHelper.COLUMN_DATE));
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date;
			try {
				date = format.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				date = null;
			}
			sheet.setDate(date);
//			time slot
			sheet.setTimeSlot(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TIME_SLOT)));
//			audit type
			sheet.setAuditType(c.getInt(c.getColumnIndex(DBHelper.COLUMN_AUDIT_TYPE)));
//			auditor
			long auditorId = c.getLong(c.getColumnIndex(DBHelper.COLUMN_AUDITOR_ID));
			sheet.setAuditor(getAuditorById(auditorId));
//			checklist type
			long checklistTypeId = c.getInt(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_ID));
			sheet.setChecklistType(getChecklistTypeById(checklistTypeId));
			sheetList.add(sheet);
		}
		db.close();
		return sheetList;
	}
	
	public void update(Sheet sheet) {
		// TODO Auto-generated method stub
		
	}
	public void loadSampleData() {
		System.out.println("=====================================");
		Entrepreneur e1 = new Entrepreneur(1, "บริษัท ทีซี ฟาร์มาซูติคอล อุตสาหกรรม จำกัด", "39 หมู่ 13 ถนนบางขนาก-บ้านสร้าง ตำบลบางแตน อำเภอบ้านสร้าง จังหวัดปราจีนบุรี 25150");
		Entrepreneur e2 = new Entrepreneur(2, "บริษัท ยูนิ-เพรสซิเดนท์ (ประเทศไทย) จำกัด", "502 หมุ่ 3 ถนนพระประโทน-บ้านแพ้ว ตำบลดอนยายหอม อำเภอเมือง จังหวัดนครปฐม 73000");
		insert(e1);
		insert(e2);
		
		Auditor a1 = new Auditor();
		a1.setId(1); a1.setName("ธนะรัตน์ ฐานิสสะ");
		Auditor a2 = new Auditor();
		a2.setId(2); a2.setName("ธรรมนูญ ดีสวัสดิ์");
		insert(a1);
		insert(a2);
		
		ChecklistType chType1 = new ChecklistType(1, "แบบประเมินระบบ HAL-Q มาตรฐาน");
		insert(chType1);
		
		Sheet s1 = new Sheet();
		s1.setSheetId(1);
		s1.setAuditor(a1);
		s1.setEntrepreneur(e1);
		s1.setChecklistType(chType1);
		s1.setAuditType(Sheet.AUDIT_TYPE_0);
		s1.setTimeSlot(Sheet.TIME_SLOT_0);
		s1.setDate(new Date());
		insert(s1);
		
		System.out.println("=====================================");

	}
	private Sheet cursorToSheet(Cursor c){
		Sheet sheet = null;
		if( c.moveToFirst() ){
			sheet = new Sheet();
//			sheet id
			sheet.setSheetId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_SHEET_ID)));
//			entrepreneur
			long entrepreneurId = c.getLong(c.getColumnIndex(DBHelper.COLUMN_ENTREPRENEUR_ID));
			sheet.setEntrepreneur(getEntrepreneurById(entrepreneurId));
//			evaluation date
			String dateStr = c.getString(c.getColumnIndex(DBHelper.COLUMN_DATE));
			Date date = new Date();
			sheet.setDate(date);
//			time slot
			sheet.setTimeSlot(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TIME_SLOT)));
//			auditor
			long auditorId = c.getLong(c.getColumnIndex(DBHelper.COLUMN_AUDITOR_ID));
			sheet.setAuditor(getAuditorById(auditorId));
//			checklist type
			long checklistTypeId = c.getInt(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_ID));
			sheet.setChecklistType(getChecklistTypeById(checklistTypeId));
		}
		return sheet;
	}
	public Entrepreneur getEntrepreneurById(long entrepreneurId){
		db = dbHelper.getReadableDatabase();
		String selection = DBHelper.COLUMN_ENTREPRENEUR_ID + " = ?";
		String[] selectionArgs = new String[]{String.valueOf(entrepreneurId)};
		Cursor c = db.query(DBHelper.TABLE_ENTREPRENEUR, DBHelper.COLUMNS_OF_ENTREPRENEUR, selection, selectionArgs, null, null, null);
		Entrepreneur entrepreneur = cursorToEntrepreneur(c);
		db.close();
		return entrepreneur;
	}
	public ChecklistType getChecklistTypeById(long checklistTypeId){
		db = dbHelper.getReadableDatabase();
		String selection = DBHelper.COLUMN_CHECKLIST_TYPE_ID + " = ?";
		String[] selectionArgs = new String[]{String.valueOf(checklistTypeId)};
		Cursor c = db.query(DBHelper.TABLE_CHECKLIST_TYPE, DBHelper.COLUMNS_OF_CHECKLIST_TYPE, selection, selectionArgs, null, null, null);
		ChecklistType checklistType = cursorToChecklistType(c);
		db.close();
		return checklistType;
	}
	public Sheet getSheetById(long sheetId){
		db = dbHelper.getReadableDatabase();
		String selection = DBHelper.COLUMN_SHEET_ID + " = ?";
		String[] selectionArgs = new String[]{String.valueOf(sheetId)};
		Cursor c = db.query(DBHelper.TABLE_SHEET, DBHelper.COLUMNS_OF_SHEET, selection, selectionArgs, null, null, null);
		Sheet sheet = cursorToSheet(c);
		db.close();
		return sheet;
	}
	public Auditor getAuditorById(long auditorId){
		db = dbHelper.getReadableDatabase();
		String selection = DBHelper.COLUMN_AUDITOR_ID + " = ?";
		String[] selectionArgs = new String[]{String.valueOf(auditorId)};
		Cursor c = db.query(DBHelper.TABLE_AUDITOR, DBHelper.COLUMNS_OF_AUDITOR, selection, selectionArgs, null, null, null);
		Auditor auditor = cursorToAuditor(c);
		db.close();
		return auditor;
	}
	private ChecklistType cursorToChecklistType(Cursor c){
		ChecklistType checklistType = null;
		if(c.moveToFirst()){
			checklistType = new ChecklistType();
			checklistType.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_ID)));
			checklistType.setName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CHECKLIST_TYPE_NAME)));
		}
		return checklistType;
	}
	private Entrepreneur cursorToEntrepreneur(Cursor c){
		Entrepreneur entrepreneur = null;
		if(c.moveToFirst()){
			entrepreneur = new Entrepreneur();
			entrepreneur.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_ENTREPRENEUR_ID)));
			entrepreneur.setCompanyName(c.getString(c.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME)));
			entrepreneur.setAddress(c.getString(c.getColumnIndex(DBHelper.COLUMN_ADDRESS)));
		}
		return entrepreneur;
	}
	private Auditor cursorToAuditor(Cursor c){
		Auditor auditor = null;
		if(c.moveToFirst()){
			auditor = new Auditor();
			auditor.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_AUDITOR_ID)));
			auditor.setName(c.getString(c.getColumnIndex(DBHelper.COLUMN_AUDITOR_NAME)));
		}
		return auditor;
	}
}
