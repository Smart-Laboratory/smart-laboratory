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
 * 规则条目，一个规则可以包含多个条目。条目中记录index（检验项目）对应的参考范围等
 */
@Entity
@Table(name = "lab_item")
public class Item extends BaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// Primary Key
	
	private Long id;

	private String indexId;
	private String value; // 数值为表达式，字符和枚举使用字符串
	private String unit;	//单位
	private int isStr;
	private Set<Rule> rules = new HashSet<Rule>();
	private String createUser;
	private Date createTime;
	
	public Item() {}

	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM")
	@SequenceGenerator(name = "SEQ_ITEM", sequenceName = "item_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 是否为字符串
	 */
	@Column(name = "is_str")
	public int getIsStr() {
		return isStr;
	}

	public void setIsStr(int isStr) {
		this.isStr = isStr;
	}

	@Column(name = "indexid")
	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	
	/**
	 * 条目的单位
	 */
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * 使用该条目的规则，关系多对多
	 */
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_rule_item", 
			joinColumns = { @JoinColumn(name = "item_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

	/**
	 * 条目的值
	 */
	@Column(length = 50)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 条目的创建者
	 */
	@Column(name = "create_user", nullable = true)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 条目的创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
