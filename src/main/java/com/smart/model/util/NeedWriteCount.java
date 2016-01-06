package com.smart.model.util;

/**
 * 需要写回的统计对象，包括需要写回的代码段、写回数、写回详情
 */
public class NeedWriteCount {
	
	private String code;
	private int count;
	private String list;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getList() {
		return list;
	}
	
	public void setList(String list) {
		this.list = list;
	}
}
