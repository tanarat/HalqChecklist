package org.silk.checklist;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.silk.checklist.db.ChecklistTypeService;
import org.silk.checklist.db.EntrepreneurService;
import org.silk.checklist.db.SheetService;
import org.silk.checklist.model.Auditor;
import org.silk.checklist.model.ChecklistType;
import org.silk.checklist.model.Entrepreneur;
import org.silk.checklist.model.Sheet;



import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SheetDetailActivity extends Activity {
	private static final int DIALOG_SELECT_ENTREPRENEUR = 1;
	private static final int DIALOG_SELECT_DATE = 2;
	private static final int DIALOG_SELECT_TIME = 3;
	private static final int DIALOG_SELECT_CHECKLIST = 4;
	private static final int DIALOG_CONFIRM_SAVE = 5;
	private static final int DIALOG_CONFIRM_SAVE_FINISH = 6;
	private static final int DIALOG_SELECT_AUDIT_TYPE = 7;
	
	private static CharSequence[] companyNames;

	private static CharSequence[] checklistNames;
	
	
	List<Entrepreneur> entrepreneurList;
	List<ChecklistType> checklistTypeList;
	
	private Date evalDate;


    private int selectedChecklist = 0;
    private int selectedTime = 0;
    private int selectedCompany = 0;
    private int selectedAuditType = 0;
    
    private EditText mDateDisplay,etEvalTime, etCompanyName, etAddress, etChecklist, etAuditType;
    
    private SheetService sheetService;
    private EntrepreneurService entrepreneurService;
    private ChecklistTypeService checklistTypeService;
    //model
    private Sheet sheet; 
    
    //MyApp
    MyApp myapp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheet_detail);
		myapp = (MyApp)getApplication();
		sheetService = new SheetService(myapp.getDao());
		entrepreneurService = new EntrepreneurService(myapp.getDao());
		checklistTypeService = new ChecklistTypeService(myapp.getDao());
		
		entrepreneurList = entrepreneurService.getAllEntrepreneurs();
		companyNames = new String[entrepreneurList.size()];
		for (int i = 0; i < entrepreneurList.size(); i++) {
			companyNames[i] = entrepreneurList.get(i).getCompanyName();
		}
		
		checklistTypeList = checklistTypeService.getAllChecklistTypes();
		checklistNames = new String[checklistTypeList.size()];
		for (int i = 0; i < checklistTypeList.size(); i++) {
			checklistNames[i] = checklistTypeList.get(i).getName();
		}
		
		etAuditType = (EditText)findViewById(R.id.auditType);
		etAuditType.setText(Sheet.AUDIT_TYPES[selectedAuditType]);
		
		mDateDisplay = (EditText)findViewById(R.id.evalDate);
		//set default date to now
		evalDate = new Date();
		setEvalDate(evalDate);
		etEvalTime = (EditText)findViewById(R.id.evalTime);
		//set default time slot
		etEvalTime.setText(Sheet.TIME_SLOTS[selectedTime]);
		
		etCompanyName = (EditText)findViewById(R.id.etCompanyName);
		etAddress = (EditText)findViewById(R.id.etAddress);
		etChecklist = (EditText)findViewById(R.id.checklist);
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sheet_detail, menu);
		return true;
	}
	public void selectChecklist(View view){
		showDialog(DIALOG_SELECT_CHECKLIST);
	}
	public void selectEntrepreneur(View view){
		showDialog(DIALOG_SELECT_ENTREPRENEUR);
	}
	public void selectDate(View view){
		showDialog(DIALOG_SELECT_DATE);
	}
	public void selectTime(View view){
		showDialog(DIALOG_SELECT_TIME);
	}
	public void selectEvaluationType(View view){
		showDialog(DIALOG_SELECT_AUDIT_TYPE);
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CONFIRM_SAVE_FINISH:
            return new AlertDialog.Builder(SheetDetailActivity.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_confirm_save_finish)
                .setPositiveButton(R.string.alert_dialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
//                    	startEvaluate();
                    	Intent intent = new Intent(SheetDetailActivity.this, ChecklistActivity.class);
                    	startActivity(intent);
                    	finish();
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    	finish();
                    }
                })
                .create();
		case DIALOG_CONFIRM_SAVE:
            return new AlertDialog.Builder(SheetDetailActivity.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle(R.string.alert_dialog_confirm_save)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    	saveSheet();
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create();
		
        case DIALOG_SELECT_ENTREPRENEUR:
        	
        	return new AlertDialog.Builder(SheetDetailActivity.this)
//            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.alert_dialog_entrepreneur_list)
//            .setSingleChoiceItems(new EntrepreneurAdapter(this), 0, new DialogInterface.OnClickListener() {
            .setSingleChoiceItems(companyNames, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int index) {
                    /* User clicked on a radio button do some stuff */
                	selectedCompany = index;
                }
            })
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    /* User clicked Yes so do some stuff */
                	etCompanyName.setText(companyNames[selectedCompany]);
                	etAddress.setText(entrepreneurList.get(selectedCompany).getAddress());
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            })
           .create();
        
        case DIALOG_SELECT_AUDIT_TYPE:
        	return new AlertDialog.Builder(SheetDetailActivity.this)
//            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.alert_dialog_evaluation_type_list)
//            .setSingleChoiceItems(new EntrepreneurAdapter(this), 0, new DialogInterface.OnClickListener() {
            .setSingleChoiceItems(Sheet.AUDIT_TYPES, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int index) {
                    /* User clicked on a radio button do some stuff */
                	selectedAuditType = index;
                }
            })
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    /* User clicked Yes so do some stuff */
                	etAuditType.setText(Sheet.AUDIT_TYPES[selectedAuditType]);
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            })
           .create();	
        	
        case DIALOG_SELECT_DATE:
        	Calendar calendar = new GregorianCalendar();
        	calendar.setTime(evalDate);
        	int mYear = calendar.get(Calendar.YEAR);
        	int mMonth = calendar.get(Calendar.MONTH);
        	int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        	DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    mDateSetListener,
                    mYear, mMonth, mDay);
        	
            return datePickerDialog;
        
        case DIALOG_SELECT_CHECKLIST:
        	return new AlertDialog.Builder(SheetDetailActivity.this)
//            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.alert_dialog_time)
            .setSingleChoiceItems(checklistNames, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int index) {

                    /* User clicked on a radio button do some stuff */
//                	Toast.makeText(getApplicationContext(), "you selected " + whichButton, Toast.LENGTH_SHORT).show();
                	selectedChecklist = index;
                }
            })
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Yes so do some stuff */
                	etChecklist.setText(checklistNames[selectedChecklist]);
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            })
           .create();    
        
        case DIALOG_SELECT_TIME:
        	return new AlertDialog.Builder(SheetDetailActivity.this)
//            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle(R.string.alert_dialog_time)
            .setSingleChoiceItems(Sheet.TIME_SLOTS, 0, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int index) {

                    /* User clicked on a radio button do some stuff */
//                	Toast.makeText(getApplicationContext(), "you selected " + whichButton, Toast.LENGTH_SHORT).show();
                	selectedTime = index;
                }
            })
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Yes so do some stuff */
                	etEvalTime.setText(Sheet.TIME_SLOTS[selectedTime]);
                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            })
           .create();  
        	
        default:
        	return null;
		}
		
	}

	private void setEvalDate(Date date){
		evalDate = date;
		String datePattern = "dd-MM-yyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern,Locale.US);
		mDateDisplay.setText(dateFormat.format(date));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                        int dayOfMonth) {
                	Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                	setEvalDate(calendar.getTime());

                }
            };
            

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int selectedItem = item.getItemId();
    	switch(selectedItem){
    	case R.id.action_save:
    		if(validSheet()){
    			showDialog(DIALOG_CONFIRM_SAVE);
    		}
    	}

        return true;
    }
    private boolean validSheet(){
//    	the Entrepreneur must be selected
    	if(etCompanyName.getText() == null || etCompanyName.getText().length() == 0){
    		Toast.makeText(this, R.string.alert_company_required, Toast.LENGTH_LONG).show();
    		return false;
    	}
    	return true;
    }
    private void saveSheet(){
    	if(sheet == null){
    		sheet = new Sheet();
    	}
    	Auditor auditor = new Auditor(); auditor.setId(1);
    	sheet.setAuditor(auditor);
    	sheet.setAuditType(selectedAuditType);
    	sheet.setEntrepreneur(entrepreneurList.get(selectedCompany));
    	sheet.setChecklistType(checklistTypeList.get(selectedChecklist));
    	sheet.setTimeSlot(selectedTime);
    	sheet.setDate(evalDate);
    	
    	
    	if(sheetService.save(sheet)){
//    		Toast.makeText(getApplicationContext(), R.string.alert_save_finish, Toast.LENGTH_SHORT).show();
    		showDialog(DIALOG_CONFIRM_SAVE_FINISH);
    		
    	}else{
    		Toast.makeText(getApplicationContext(), R.string.alert_save_fail, Toast.LENGTH_SHORT).show();
    	}
    }
    
}
