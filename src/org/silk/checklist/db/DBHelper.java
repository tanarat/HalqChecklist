package org.silk.checklist.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.Entrepreneur;
import org.silk.checklist.model.Sheet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "SILKDB";
	static final int VERSION = 1;
	static final String tag = "DBHelper";
	
	/*
	 * Table: ChecklistType
	 */
	static final String TABLE_CHECKLIST_TYPE = "CHECKLIST_TYPE";
	static final String COLUMN_CHECKLIST_TYPE_ID = "CHECKLIST_TYPE_ID";
	static final String COLUMN_CHECKLIST_TYPE_NAME = "CHECKLIST_TYPE_NAME";
	static final String[] COLUMNS_OF_CHECKLIST_TYPE = {COLUMN_CHECKLIST_TYPE_ID, COLUMN_CHECKLIST_TYPE_NAME};
	
	static final String CREATE_TABLE_CHECKLIST_TYPE = "CREATE TABLE "
			+ TABLE_CHECKLIST_TYPE + " (" + COLUMN_CHECKLIST_TYPE_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_CHECKLIST_TYPE_NAME
			+ " TEXT NOT NULL );";
	
	/*
	 * Table: Entrepreneur
	 */
	static final String TABLE_ENTREPRENEUR = "ENTREPRENEUR";
	static final String COLUMN_ENTREPRENEUR_ID = "ENTREPRENEUR_ID";
	static final String COLUMN_COMPANY_NAME = "COMPANY_NAME";
	static final String COLUMN_ADDRESS = "ADDRESS";

	static final String CREATE_TABLE_ENTREPRENEUR = "CREATE TABLE "
			+ TABLE_ENTREPRENEUR + " (" + COLUMN_ENTREPRENEUR_ID
			+ " INTEGER PRIMARY KEY, " + COLUMN_COMPANY_NAME
			+ " TEXT NOT NULL, " + COLUMN_ADDRESS + " TEXT NOT NULL" + ");";

	static final String[] COLUMNS_OF_ENTREPRENEUR = {COLUMN_ENTREPRENEUR_ID, COLUMN_COMPANY_NAME, COLUMN_ADDRESS};
	/*
	 * Table: Auditor
	 */
	static final String TABLE_AUDITOR = "AUDITOR";
	static final String COLUMN_AUDITOR_ID = "AUDITOR_ID";
	static final String COLUMN_AUDITOR_NAME = "AUDIROT_NAME";

	static final String CREATE_TABLE_AUDITOR = "CREATE TABLE " + TABLE_AUDITOR
			+ " (" + COLUMN_AUDITOR_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_AUDITOR_NAME + " TEXT NOT NULL " + ");";

	static final String[] COLUMNS_OF_AUDITOR = {COLUMN_AUDITOR_ID, COLUMN_AUDITOR_NAME};
	/*
	 * Table: Sheet
	 */
	static final String TABLE_SHEET = "SHEET";
	static final String COLUMN_SHEET_ID = "SHEET_ID";
	// static final String COLUMN_ENTREPRENEUR = "ENTREPRENEUR_ID";
	static final String COLUMN_DATE = "EVALUATE_DATE";
	static final String COLUMN_TIME_SLOT = "TIMESLOT";
	static final String COLUMN_AUDIT_TYPE = "AUDIT_TYPE";
	// static final String COLUMN_AUDITOR = "AUDITOR_ID";
//	static final String COLUMN_CHECKLIST_TYPE_ID = "CHECKLIST_TYPE_ID";
	static final String[] COLUMNS_OF_SHEET = {COLUMN_SHEET_ID, COLUMN_ENTREPRENEUR_ID, COLUMN_DATE, COLUMN_TIME_SLOT, COLUMN_AUDIT_TYPE,COLUMN_AUDITOR_ID, COLUMN_CHECKLIST_TYPE_ID};

	static final String CREATE_TABLE_SHEET = "CREATE TABLE " + TABLE_SHEET
			+ " ("
			+ COLUMN_SHEET_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_ENTREPRENEUR_ID
			+ " INTEGER NOT NULL, "
			+
			// COLUMN_ENTREPRENEUR + " INTEGER NOT NULL, FOREIGN KEY (" +
			// COLUMN_ENTREPRENEUR_ID + ") " +
			// "REFERENCES " + TABLE_ENTREPRENEUR + " (" +
			// COLUMN_ENTREPRENEUR_ID + "), " +
			COLUMN_DATE + " TEXT NOT NULL, " + COLUMN_TIME_SLOT
			+ " INTEGER NOT NULL, " + COLUMN_AUDIT_TYPE + " INTEGER NOT NULL, "
			+ COLUMN_AUDITOR_ID + " INTEGER NOT NULL, " +
			// COLUMN_AUDITOR + " INTEGER NOT NULL, FOREIGN KEY (" +
			// COLUMN_AUDITOR_ID + ") " +
			// "REFERENCES " + TABLE_AUDITOR + " (" + COLUMN_AUDITOR_ID + "), "
			// +
			COLUMN_CHECKLIST_TYPE_ID + " INTEGER NOT NULL" + ");";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_CHECKLIST_TYPE);
		Log.i(tag, "CREATE TABLE : " + TABLE_CHECKLIST_TYPE);
//		db.execSQL("DROP TABLE " + TABLE_AUDITOR);
		db.execSQL(CREATE_TABLE_AUDITOR);
		Log.i(tag, "CREATE TABLE : " + TABLE_AUDITOR);
//		db.execSQL("DROP TABLE " + TABLE_ENTREPRENEUR);
		db.execSQL(CREATE_TABLE_ENTREPRENEUR);
		Log.i(tag, "CREATE TABLE : " + TABLE_ENTREPRENEUR);
//		db.execSQL("DROP TABLE " + TABLE_SHEET);
		db.execSQL(CREATE_TABLE_SHEET);
		Log.i(tag, "CREATE TABLE : " + TABLE_SHEET);
		System.out.println(CREATE_TABLE_SHEET);

	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
///*
//	public List<Sheet> getAllSheets() {
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor c = db.query(TABLE_SHEET, COLUMNS_OF_SHEET, null, null, null, null, null);
//		List<Sheet> sheetList = new ArrayList<Sheet>(); 
//		while(c.moveToNext()){
//			Sheet sheet = new Sheet();
//			sheet = cursorToSheet(c);
//			sheetList.add(sheet);
//		}
//		return sheetList;
//	}
//	
//	public Auditor getAuditorById(long auditorId){
//		SQLiteDatabase db = this.getReadableDatabase();
//		String selection = COLUMN_AUDITOR_ID + " = ?";
//		String[] selectionArgs = new String[]{String.valueOf(auditorId)};
//		Cursor c = db.query(TABLE_AUDITOR, COLUMNS_OF_AUDITOR, selection, selectionArgs, null, null, null);
//		Auditor auditor = cursorToAuditor(c);
//		db.close();
//		return auditor;
//	}
//	public Entrepreneur getEntrepreneurById(long entrepreneurId){
//		SQLiteDatabase db = this.getReadableDatabase();
//		String selection = COLUMN_ENTREPRENEUR_ID + " = ?";
//		String[] selectionArgs = new String[]{String.valueOf(entrepreneurId)};
//		Cursor c = db.query(TABLE_ENTREPRENEUR, COLUMNS_OF_ENTREPRENEUR, selection, selectionArgs, null, null, null);
//		Entrepreneur entrepreneur = cursorToEntrepreneur(c);
//		db.close();
//		return entrepreneur;
//	}
//	public Sheet getSheetById(long sheetId){
//		SQLiteDatabase db = this.getReadableDatabase();
//		String selection = COLUMN_SHEET_ID + " = ?";
//		String[] selectionArgs = new String[]{String.valueOf(sheetId)};
//		Cursor c = db.query(TABLE_SHEET, COLUMNS_OF_SHEET, selection, selectionArgs, null, null, null);
//		Sheet sheet = cursorToSheet(c);
//		db.close();
//		return sheet;
//	}
//	private Sheet cursorToSheet(Cursor c){
//		Sheet sheet = null;
//		if( c.moveToFirst() ){
//			sheet = new Sheet();
////			sheet id
//			sheet.setSheetId(c.getLong(c.getColumnIndex(COLUMN_SHEET_ID)));
////			entrepreneur
//			long entrepreneurId = c.getLong(c.getColumnIndex(COLUMN_ENTREPRENEUR_ID));
//			sheet.setEntrepreneur(getEntrepreneurById(entrepreneurId));
////			evaluation date
//			String dateStr = c.getString(c.getColumnIndex(COLUMN_DATE));
//			Date date = new Date();
//			sheet.setDate(date);
////			time slot
//			sheet.setTimeSlot(c.getInt(c.getColumnIndex(COLUMN_TIME_SLOT)));
////			auditor
//			long auditorId = c.getLong(c.getColumnIndex(COLUMN_AUDITOR_ID));
//			sheet.setAuditor(getAuditorById(auditorId));
////			checklist type
//			sheet.setChecklistType(c.getInt(c.getColumnIndex(COLUMN_CHECKLIST_TYPE_ID)));
//		}
//		return sheet;
//	}
//	private Entrepreneur cursorToEntrepreneur(Cursor c){
//		Entrepreneur entrepreneur = null;
//		if(c.moveToFirst()){
//			entrepreneur = new Entrepreneur();
//			entrepreneur.setId(c.getLong(c.getColumnIndex(COLUMN_ENTREPRENEUR_ID)));
//			entrepreneur.setCompanyName(c.getString(c.getColumnIndex(COLUMN_COMPANY_NAME)));
//			entrepreneur.setAddress(c.getString(c.getColumnIndex(COLUMN_ADDRESS)));
//		}
//		return entrepreneur;
//	}
//	private Auditor cursorToAuditor(Cursor c){
//		Auditor auditor = null;
//		if(c.moveToFirst()){
//			auditor = new Auditor();
//			auditor.setId(c.getLong(c.getColumnIndex(COLUMN_AUDITOR_ID)));
//			auditor.setName(c.getString(c.getColumnIndex(COLUMN_AUDITOR_NAME)));
//		}
//		return auditor;
//	}
//	public void insert(Entrepreneur entrepreneur){
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(COLUMN_ENTREPRENEUR_ID, entrepreneur.getId());
//		values.put(COLUMN_COMPANY_NAME, entrepreneur.getCompanyName());
//		values.put(COLUMN_ADDRESS, entrepreneur.getAddress());
//		long id = db.insert(TABLE_ENTREPRENEUR, null, values);
//		Log.i(tag, "insert into " + TABLE_ENTREPRENEUR + " with id " + id);
//		db.close();
//	}
//	public void insert(Sheet sheet) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		putValues(sheet, values);
//		long id = db.insert(TABLE_SHEET, null, values);
//		sheet.setSheetId(id);
//		db.close();
//	}
//
//	public void update(Sheet sheet) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		putValues(sheet, values);
//		String whereClause = COLUMN_SHEET_ID + " = ?";
//		String[] whereArgs = new String[] { String.valueOf(sheet.getSheetId()) };
//		db.update(TABLE_SHEET, values, whereClause, whereArgs);
//		db.close();
//	}
//
//	private void putValues(Sheet sheet, ContentValues values) {
////		/* sheetId */
//		// values.put(COLUMN_ENTREPRENEUR_ID, sheet.getEntrepreneur().getId());
////		/* evaluation date */
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		values.put(COLUMN_DATE, dateFormat.format(sheet.getDate()));
////		/* time slot */
//		values.put(COLUMN_TIME_SLOT, sheet.getTimeSlot());
////		/* audit type */
//		values.put(COLUMN_AUDIT_TYPE, sheet.getAuditType());
////		/* auditor id */
//		values.put(COLUMN_AUDITOR_ID, sheet.getAuditor().getId());
////		/* checklist type */
//		values.put(COLUMN_CHECKLIST_TYPE_ID, sheet.getChecklistType());
//	}

}
