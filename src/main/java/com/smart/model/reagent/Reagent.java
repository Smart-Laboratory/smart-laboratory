package com.smart.model.reagent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String testname;			// 检验项目的ID
	private String fridge;				// 存储的冰箱号
	private String storageCondition;			// 存储条件
	private int margin;					// 库存界值
	private String temperature;			// 存储位置的温度
	private int isselfmade;				// 是否为自制试剂
	
	private String lab;
	
	private boolean isStockout;
	private String totalNum;

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

	@Column(length=50)
	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	@Column(length=20)
	public String getFridge() {
		return fridge;
	}

	public void setFridge(String fridge) {
		this.fridge = fridge;
	}

	/**
	 * 存储条件
	 */
	@Column(length=20)
	public String getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
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
	
	@Column
	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	/**
	 * 是否缺货
	 */
	@Transient
	public boolean isStockout() {
		return isStockout;
	}

	public void setStockout(boolean isStockout) {
		this.isStockout = isStockout;
	}

	/**
	 * 库存总量
	 */
	@Transient
	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
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
	public String getNameAndSpecification() {
		return this.name + "[" + this.specification + "]";
	}
	
	@Transient
	public String getBaozhuang() {
		return this.unit + "[" + this.subtnum + this.subunit + "]";
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
