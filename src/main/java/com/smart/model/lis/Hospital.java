package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.search.annotations.Indexed;

import com.smart.model.BaseObject;

@Entity
@Table(name = "LAB_HOSPITAL")
@Indexed
public class Hospital extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7195317090926126664L;

	private Long id;//医院id。主键
	
	private String name;//医院名称
	private String address;//地址
	private String phone;//电话
	private String postalCard;//邮政编码
	private String idCard;//组织代码
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HOSPITAL")
	@SequenceGenerator(name="SEQ_HOSPITAL", sequenceName = "hospital_sequence", allocationSize = 1)
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	@Column(name = "name", length=100)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "address")
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	@Column(name = "phone", length=30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "postalCard", length=10)
	public String getPostalCard(){
		return postalCard;
	}
	
	public void setPostalCard(String postalCard){
		this.postalCard = postalCard;
	}
	
	@Column(name = "idCard", length=10)
	public String getIdCard(){
		return idCard;
	}
	
	public void setIdCard(String idCard){
		this.idCard = idCard;
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
