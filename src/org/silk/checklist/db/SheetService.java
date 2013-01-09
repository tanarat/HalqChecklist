package org.silk.checklist.db;

import java.util.List;

import org.silk.checklist.model.Sheet;

public class SheetService {

	Dao dao;
	
	
	public SheetService(Dao dao){
		this.dao = dao;
	}

	public List<Sheet> listAllSheets() {
		// TODO Auto-generated method stub
		return dao.getAllSheets();
	}
	
	public boolean save(Sheet sheet){
		if(sheet.getSheetId() != 0)
			dao.update(sheet);
		else
			dao.insert(sheet);
		return true;
	}



}
