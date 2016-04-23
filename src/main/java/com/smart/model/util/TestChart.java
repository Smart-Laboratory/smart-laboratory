package com.smart.model.util;

import java.util.ArrayList;
import java.util.List;

public class TestChart {
	
	private String title;
	private List<Double> loArr = new ArrayList<Double>(); 
	private List<Double> reArr = new ArrayList<Double>();
	private List<Double> hiArr = new ArrayList<Double>();
	private List<String> timeArr = new ArrayList<String>();
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Double> getLoArr() {
		return loArr;
	}
	
	public void setLoArr(List<Double> loArr) {
		this.loArr = loArr;
	}
	
	public List<Double> getReArr() {
		return reArr;
	}
	
	public void setReArr(List<Double> reArr) {
		this.reArr = reArr;
	}
	
	public List<Double> getHiArr() {
		return hiArr;
	}
	
	public void setHiArr(List<Double> hiArr) {
		this.hiArr = hiArr;
	}
	
	public List<String> getTimeArr() {
		return timeArr;
	}
	
	public void setTimeArr(List<String> timeArr) {
		this.timeArr = timeArr;
	}

}
