package com.smart.model.lis;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 智能审核结果表
 */
@Entity
@Table(name = "l_audit")
public class Audit {
	
	private long id;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_AUDIT")
	@SequenceGenerator(name = "SEQ_AUDIT", sequenceName = "audit_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
