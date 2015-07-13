package com.smart.model.reagent;

import java.io.Serializable;
import java.util.Date;
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

import com.smart.model.BaseObject;

/**
 * 卫生材料管理
 */
@Entity
@Table(name = "rg_manage")
public class Reagent extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3474312114197464946L;
	
	private Long id;					// 主键
	private String name;				// 卫生材料名称
	private String pinyin;				// 卫生材料名称拼音
	private String specification;		// 规格
	private String placeoforigin;		// 产地
	private String brand;				// 品牌/生产
	private String unit;				// 单位
	private String subunit;				// 子单位
	private int subtnum;				// 子数量
	private String price;				// 单价
	private String productcode;			// 商品码
	private int printord;				// 显示顺序
	private String creator;				// 创建者
	private Date createtime;			// 创建本记录的时间
	private String testname;			// 检验项目的ID
	private String fridge;				// 存储的冰箱号
	private int layer;					// 存储冰箱内的地方
	private String condition;			// 存储条件
	private int margin;					// 库存界值
	private String temperature;			// 存储位置的温度
	private int isselfmade;				// 是否为自制试剂
	
	private Set<Batch> batchs;			//按批号  分批储存试剂

	/**
	 * 主键
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HMM")
	@SequenceGenerator(name = "SEQ_HMM", sequenceName = "hmm_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 卫生材料名称
	 */
	@Column(length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 拼音
	 */
	@Column(length=50)
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * 规格
	 */
	@Column(length=50)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	/**
	 * 产地
	 */
	@Column(length=20)
	public String getPlaceoforigin() {
		return placeoforigin;
	}

	public void setPlaceoforigin(String placeoforigin) {
		this.placeoforigin = placeoforigin;
	}

	/**
	 * 品牌
	 */
	@Column(length=20)
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 单位
	 */
	@Column(length=10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 子单位
	 */
	@Column(length=10)
	public String getSubunit() {
		return subunit;
	}

	public void setSubunit(String subunit) {
		this.subunit = subunit;
	}
	
	/**
	 * 子数量
	 */
	@Column
	public int getSubtnum() {
		return subtnum;
	}

	public void setSubtnum(int subtnum) {
		this.subtnum = subtnum;
	}

	/**
	 * 价格
	 */
	@Column(length=10)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 商品吗
	 */
	@Column(length=20)
	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	/**
	 * 打印顺序
	 */
	@Column
	public int getPrintord() {
		return printord;
	}

	public void setPrintord(int printord) {
		this.printord = printord;
	}

	@Column(length=10)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(length=50)
	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	@Column(length=10)
	public String getFridge() {
		return fridge;
	}

	public void setFridge(String fridge) {
		this.fridge = fridge;
	}

	@Column
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * 存储条件
	 */
	@Column(length=20)
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 库存界值
	 */
	@Column
	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * 是否是自制试剂
	 */
	@Column
	public int getIsselfmade() {
		return isselfmade;
	}

	public void setIsselfmade(int isselfmade) {
		this.isselfmade = isselfmade;
	}
	

	/**
	 * 温度
	 */
	@Transient
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	@Transient
	public String getPosition() {
		return this.fridge + " 第" + this.layer + "层";
	}
	
	@Transient
	public String getNameAndSpecification() {
		return this.name + "[" + this.specification + "]";
	}
	
	@Transient
	public String getBaozhuang() {
		return this.unit + "[" + this.subtnum + this.subunit + "]";
	}
	

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Batch.class, mappedBy = "reagent")
	public Set<Batch> getBatchs() {
		return batchs;
	}

	public void setBatchs(Set<Batch> batchs) {
		this.batchs = batchs;
	}

	public String toString() {
		return null;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}
}
