package com.smart.model.rule;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="lab_description")
public class Description {

	private Long id;
	
	private String name; 
	private String bagId;  //包含该贵的的包 id集合，用"，"隔开
	private String description;
	private int type;
	private boolean isActivate = false; // 是否激活
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	
	private int number;
	private int hospitalmode;	//规则使用的对象，包括门诊、病房

	public Description() {
	}

	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DES")
	@SequenceGenerator(name = "SEQ_DES", sequenceName = "description_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 规则名称
	 */
	@Column(nullable = false, length = 50, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	/**
	 * 规则描述
	 */
	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * 是否激活
	 */
	@Column(name = "is_activate")
	public boolean isActivate() {
		return isActivate;
	}

	public void setActivate(boolean isActivate) {
		this.isActivate = isActivate;
	}
	
	
	/**
	 * 规则类型
	 */
	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 规则创建者
	 */
	@Column(name="create_user")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 规则创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 规则修改者
	 */
	@Column(name="modify_user")
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * 规则修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 就医类型
	 */
	@Column(name = "hospital_mode")
	public int getHospitalmode() {
		return hospitalmode;
	}

	public void setHospitalmode(int hospitalmode) {
		this.hospitalmode = hospitalmode;
	}

	/**
	 * 隶属的规则包
	 */
	@Column(name = "bag_id")
	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	/**
	 * 使用次数
	 */
	@Column(name = "count")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
