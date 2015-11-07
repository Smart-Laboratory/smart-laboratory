package com.smart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 映射
 */
@Entity
@Table(name = "lab_dictionary")
public class Dictionary extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -941189353315029258L;
	
	private Long id;
	private int type;
	private String sign;
	private String value;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DICT")
	@SequenceGenerator(name = "SEQ_DICT", sequenceName = "dictionary_sequence", allocationSize = 1)*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 映射类型
	 */
	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * 需要映射的符号
	 */
	@Column
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * 映射的值
	 */
	@Column
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
