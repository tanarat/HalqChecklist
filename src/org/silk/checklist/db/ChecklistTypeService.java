package org.silk.checklist.db;

import java.util.List;

import org.silk.checklist.model.ChecklistType;

public class ChecklistTypeService {

	Dao dao;
	
	public ChecklistTypeService(Dao dao){
		this.dao = dao;
	}
	public List<ChecklistType> getAllChecklistTypes(){
		return dao.getAllChecklistTypes();
	}

}
