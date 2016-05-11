package com.smart.model.rule;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.DocumentId;

import com.smart.model.BaseObject;

/**
 *  Index对象表示最基本的检验项目
 *
 *  add by zcw 2016-05-31
 *  	labdepartment：		关联部门
 *  	instrument			关联仪器
 */
@Entity
@Table(name = "lab_index")
public class Index extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Primary Key
	private Long id;
	private String indexId;
	private String english;
	private String name;
	private String sampleFrom;
	private String unit;
	private String type;
	private int diffAlgo;
	private String description;
	private String enumData;
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String importance;
	private String knowledgename;
	private int isprint;
	private String printord;
	private int needhistory;
	private String method;
<<<<<<< HEAD
	private String defaultvalue;
	
=======
>>>>>>> origin/master
	private String guide;
	private Set<Item> item = new HashSet<Item>(); // 该指标的知识点列表

	private String labdepartment;	//关联部门
	private String instrument;		//关联仪器



	public Index() {
	}

	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INDEX")
	@SequenceGenerator(name = "SEQ_INDEX", sequenceName = "index_sequence", allocationSize = 1)
    @DocumentId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 医院对应的项目id
	 */
	@Column(name = "index_id", nullable = false, length = 10, unique = true)
	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	
	/**
	 * 检验项目名称
	 */
	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 检验项目英文项目
	 */
	@Column(name = "english", length = 20)
	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	/**
	 * 检验项目样本来源
	 */
	@Column(name = "sample_from", length = 20)
	public String getSampleFrom() {
		return sampleFrom;
	}

	public void setSampleFrom(String sampleFrom) {
		this.sampleFrom = sampleFrom;
	}

	/**
	 * 检验项目单位
	 */
	@Column(length = 20)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 检验项目类型，0：枚举型；1：数字型；2：字符型；
	 */
	@Column(length = 2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 检验项目的差值算法。1：差值；2：差值百分率；3：差值变化；4：差值变化率
	 */
	@Column(name = "algorithm")
	public int getDiffAlgo() {
		return diffAlgo;
	}

	public void setDiffAlgo(int diffAlgo) {
		this.diffAlgo = diffAlgo;
	}
	
	/**
	 * 检验项目描述
	 */
	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 枚举数据
	 */
	@Column(name = "enum_data")
	public String getEnumData() {
		return enumData;
	}

	public void setEnumData(String enumData) {
		this.enumData = enumData;
	}

	/**
	 * 指标创建者
	 */
	@Column(name = "create_user", nullable = true)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 指标创建时间
	 */
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 指标修改者
	 */
	@Column(name = "modify_user")
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * 指标修改时间
	 */
	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 指标重要性
	 */
	@Column
	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}
	
	/**
	 * 知识库名称
	 */
	@Column
	public String getKnowledgename() {
		return knowledgename;
	}

	public void setKnowledgename(String knowledgename) {
		this.knowledgename = knowledgename;
	}
	
	/**
	 * 指标是否打印
	 */
	@Column
	public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}
	
	
	/**
	 * 指标打印顺序
	 */
	@Column
	public String getPrintord() {
		return printord;
	}

	public void setPrintord(String printord) {
		this.printord = printord;
	}

	@Column
	public int getNeedhistory() {
		return needhistory;
	}

	public void setNeedhistory(int needhistory) {
		this.needhistory = needhistory;
	}

	@Column
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Column
	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	/**
	 * 使用该指标的规则集合
	 */
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Item.class, mappedBy = "index")
	public Set<Item> getItem() {
		return item;
	}

	public void setItem(Set<Item> item) {
		this.item = item;
	}
	
	@Column
	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	@Column
	public String getLabdepartment() {
		return labdepartment;
	}

	public void setLabdepartment(String labdepartment) {
		this.labdepartment = labdepartment;
	}

	@Column
	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	@Transient
	public int getRuleCount() {
		HashMap<Long, Rule> map = new HashMap<Long, Rule>();
		for (Item item : this.getItem()) {
			for (Rule rule : item.getRules()) {
				if (!map.containsKey(rule.getId())) {
					map.put(rule.getId(), rule);
				}
			}
		}
		return map.size();
		
	}
	

	@Transient
	public int getAuditRuleCount() {
		HashMap<Long, Rule> map = new HashMap<Long, Rule>();
		for (Item item : this.getItem()) {
			for (Rule rule : item.getRules()) {
				if (!map.containsKey(rule.getId()) && rule.getType() != 0) {
					map.put(rule.getId(), rule);
				}
			}
		}
		return map.size();
	}
	
	@Transient
	public Map<String, String> getRules() {
		
		Map<String, String> map = new HashMap<String, String>();
		for (Item item : this.getItem()) {
			for (Rule rule : item.getRules()) {
				String id = rule.getId().toString();
				if (!map.containsKey(id)) {
					map.put(id, rule.getName());
				}
			}
		}
		return map;
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
	
	@Transient
	public String getAlgorithm() {

		String algorithm = "";

		switch (this.getDiffAlgo()) {
		case 1:
			algorithm = "差值";
			break;
		case 2:
			algorithm = "差值百分率";
			break;
		case 3:
			algorithm = "差值变化";
			break;
		case 4:
			algorithm = "差值变化率";
			break;
		default:
			algorithm = "差值百分率";
			break;
		}
		return algorithm;
	}
	
	@Transient
	public String getHistory() {
		return this.needhistory == 1 ? "是" : "否"; 
	}
}
