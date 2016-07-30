package com.smart.model.lis;

import javax.persistence.*;

@Entity
@Table(name = "l_device")
public class Device {
	private String id;
	private String type;
	private String name;
	private String lab;
	private int status;
	private int comport;
	private String baudrate;
	private String parity;
	private String databit;
	private String stopbit;
	private int handshake;
	private String mainid;
	private String datawind;
	private int inuse;
	private int logtemp;
	private int temprec;
	
	@Id
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getLab() {
		return lab;
	}
	
	public void setLab(String lab) {
		this.lab = lab;
	}
	
	@Column
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column
	public int getComport() {
		return comport;
	}
	
	public void setComport(int comport) {
		this.comport = comport;
	}
	
	@Column
	public String getBaudrate() {
		return baudrate;
	}
	
	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}
	
	@Column
	public String getParity() {
		return parity;
	}
	
	public void setParity(String parity) {
		this.parity = parity;
	}
	
	@Column
	public String getDatabit() {
		return databit;
	}
	
	public void setDatabit(String databit) {
		this.databit = databit;
	}
	
	@Column
	public String getStopbit() {
		return stopbit;
	}
	
	public void setStopbit(String stopbit) {
		this.stopbit = stopbit;
	}
	
	@Column
	public int getHandshake() {
		return handshake;
	}
	
	public void setHandshake(int handshake) {
		this.handshake = handshake;
	}
	
	@Column
	public String getMainid() {
		return mainid;
	}
	
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	
	@Column
	public String getDatawind() {
		return datawind;
	}
	
	public void setDatawind(String datawind) {
		this.datawind = datawind;
	}
	
	@Column
	public int getInuse() {
		return inuse;
	}
	
	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
	
	@Column
	public int getLogtemp() {
		return logtemp;
	}
	
	public void setLogtemp(int logtemp) {
		this.logtemp = logtemp;
	}
	
	@Column
	public int getTemprec() {
		return temprec;
	}
	
	public void setTemprec(int temprec) {
		this.temprec = temprec;
	}
}
