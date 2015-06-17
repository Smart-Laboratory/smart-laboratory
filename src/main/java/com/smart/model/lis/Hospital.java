package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "Hospital")
@Indexed
@XmlRootElement
public class Hospital {

	
	private String id;//医院id。主键
	
	private String name;//医院名称
	private String address;//地址
	private String postalCard;//邮政编码
	private String idCard;//组织代码
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HOSPITAL")
	@SequenceGenerator(name="SEQ_HOSPITAL", sequenceName = "hospital_seq", allocationSize = 1)
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	@Column(name = "address")
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
	}
	
	@Column(name = "postalCard")
	public String getPostalCard(){
		return postalCard;
	}
	public void setPostalCard(String postalCard){
		this.postalCard = postalCard;
	}
	
	@Column(name = "idCard")
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
}
