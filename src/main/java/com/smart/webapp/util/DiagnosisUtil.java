package com.smart.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.Diagnosis;
import com.smart.service.lis.DiagnosisManager;

public class DiagnosisUtil {

	private Map<String, String> map = new HashMap<String,String>();
	
	private static DiagnosisUtil instance = new DiagnosisUtil();
	
	public DiagnosisUtil() {}

    public DiagnosisUtil getInstance() {
		if(instance.map == null){
			synchronized (instance) {
				instance.initMap();
			}
		}
		return instance;
	}
	
	public void initMap(){
		List<Diagnosis> dList = diagnosisManager.getAll();
		for(Diagnosis diagnosis : dList){
			map.put(diagnosis.getDiagnosisName(), diagnosis.getKnowledgeName());
		}
		
	}
	
	@Autowired
	private DiagnosisManager diagnosisManager;
}
