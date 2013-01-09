package org.silk.checklist.db;

import java.util.List;

import org.silk.checklist.model.Entrepreneur;

public class EntrepreneurService {

	Dao dao;

	public EntrepreneurService(Dao dao) {
		super();
		this.dao = dao;
	}
	
	public List<Entrepreneur> getAllEntrepreneurs(){
		return dao.getAllEntrepreneurs();
	}
}
