package com.smart.model.rule;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;
/**
 * 规则的结果
 */
@Entity
@Table(name = "lab_result")
public class Result extends BaseObject implements Serializable{

	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id;

	private String content; //内容
	private String level;
	private String reject;  //与该结果同属一个规则的 其他结果
	private String category;
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String percent;
	private String modifyContent;
	private Set<Rule> rules = new HashSet<Rule>(); // 使用该结果的规则
	
	public Result() {
	}

	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESULT")
	@SequenceGenerator(name = "SEQ_RESULT", sequenceName = "result_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 结果内容
	 */
	@Column(nullable = false, length = 50)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 结果的等级
	 */
	@Column(length = 20, name= "result_level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 结果的类别
	 */
	@Column(length = 20)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * 与该结果冲突的其他结果
	 */
	@Column(length = 50)
	public String getReject() {
		return reject;
	}

	public void setReject(String reject) {
		this.reject = reject;
	}

	/**
	 * 结果创建者
	 */
	@Column(name = "create_user", nullable = true)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 结果创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 结果修改者
	 */
	@Column(name = "modify_user")
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * 结果修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 结果修改内容
	 */
	@Column(name = "modify_content")
	public String getModifyContent() {
		return modifyContent;
	}

	public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}

	/**
	 * 包含该结果的规则
	 */
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_rule_result", 
			joinColumns = { @JoinColumn(name = "result_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * 推出该结果的可能性
	 */
	@Column(length = 50, name= "gailv")
	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
