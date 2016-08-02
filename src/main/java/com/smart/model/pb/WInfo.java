package com.smart.model.pb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "WORKINFO")
public class WInfo {

	private Long id;
	
	private String workid;
	private String name;
	private int sex;
	private Date worktime;
	private int type;
	private String section;
	private String phone;
	private String shift;
	private int ord1=0;
	private int ord2=0;
	private int ord3=0;
	private int ord4=0;
	private int ord5=0;
	private int ord6=0;
	private int isActive;
	
	private double holiday;//年休
	private String defeHoliday;//积休
	private double defeholidayhis;//历年积休
	private double lxsy;//历休使用
	private String school;
	private String usercomment; //备注
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WINFO")
	@SequenceGenerator(name = "SEQ_WINFO", sequenceName = "winfo_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getWorkid() {
		return workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	@Column
	public Date getWorktime() {
		return worktime;
	}

	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}

	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	@Column
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	@Column(name="ord1", length = 10)
	public int getOrd1() {
		return ord1;
	}

	public void setOrd1(int ord1) {
		this.ord1 = ord1;
	}

	@Column
	public int getOrd2() {
		return ord2;
	}

	public void setOrd2(int ord2) {
		this.ord2 = ord2;
	}

	@Column
	public int getOrd3() {
		return ord3;
	}

	public void setOrd3(int ord3) {
		this.ord3 = ord3;
	}
	
	@Column
	public int getOrd4() {
		return ord4;
	}

	public void setOrd4(int ord4) {
		this.ord4 = ord4;
	}
	
	


	@Transient
	public String getSexString() {
		return this.sex==0?"男":"女";
	}
	
	@Column
	public int getOrd5() {
		return ord5;
	}

	public void setOrd5(int ord5) {
		this.ord5 = ord5;
	}

	@Column
	public int getOrd6() {
		return ord6;
	}

	public void setOrd6(int ord6) {
		this.ord6 = ord6;
	}

	@Column
	public double getHoliday() {
		return holiday;
		
	}

	public void setHoliday(double holiday) {
		this.holiday = holiday;
	}

	@Column
	public String getDefeHoliday() {
		return defeHoliday;
	}

	public void setDefeHoliday(String defeHoliday) {
		this.defeHoliday = defeHoliday;
	}

	@Column
	public double getDefeholidayhis() {
		return defeholidayhis;
	}

	public void setDefeholidayhis(double defeholidayhis) {
		this.defeholidayhis = defeholidayhis;
	}

	@Column(name="isactive")
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Column
	public double getLxsy() {
		return lxsy;
	}

	public void setLxsy(double lxsy) {
		this.lxsy = lxsy;
	}
	
	@Column(name="school",length=50)
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	@Column
	public String getUsercomment() {
		return usercomment;
	}
	public void setUsercomment(String usercomment) {
		this.usercomment = usercomment;
	}

	@Transient
	public String getTypeString() {
		String value = "";
		switch (getType()) {
		case 0:
			value = "在职";
			break;
		case 1:
			value = "进修";
			break;
		case 2:
			value = "实习";
			break;
		case 3:
			value = "工人";
			break;
		case 4:
			value = "住培";
			break;
		}
		return value;
	}
	
	@Transient
	public List<String> getShifts() {
		List<String> list = new ArrayList<String>();
		for(String s : this.shift.split(",")) {
			if(!s.isEmpty()) {
				list.add(s);
			}
		}
		return list;
	}
	
	@Transient
	public String getShift2() {
		String str ="";
		for(String s : this.shift.split(",")) {
			if(!s.isEmpty()) {
				str += "'" + s + "',";
			}
		}
		return str.substring(0, str.length()-1);
	}
	
	
	@Transient
	public double getHolidayNum(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.getWorktime());
		int year = calendar.get(Calendar.YEAR);
		calendar.setTime(new Date());
		int yearNow = calendar.get(Calendar.YEAR);
		int gl = yearNow - year;
		if(gl == 0){
			return 2.5;
		}
		else if(gl<10){
			return 5;
		} else if(gl<20){
			return 10;
		} else if(gl>20){
			return 15;
		}
		return 0;
	}
	
	@Transient
	public double getDefeHolidayNum(){
		double num =0;
		if(defeHoliday!=null && defeHoliday != ""){
			for(String MonthHoliday : this.defeHoliday.split(";")){
				if(MonthHoliday !=null){
					if(MonthHoliday.split(":").length>=2)
						num += Double.parseDouble( MonthHoliday.split(":")[1] );
				}
			}
		}
		return num+defeholidayhis;
	}
	
	@Transient
	public double getDefeHolidayhisNum(){
		return defeholidayhis-lxsy;
	}
}
