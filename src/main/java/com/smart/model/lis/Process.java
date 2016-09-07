package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 样本流转信息表
 */
@Entity
@Table(name = "l_process")
public class Process extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1430918242317268010L;
	
	private long id;

	private String requester;
	private Date requesttime;
	private String executor;
	private Date executetime;
	private String sender;
	
	private Date sendtime;
	private String receiver;
	private Date receivetime;
	private String ksreceiver;
	private Date ksreceivetime;
	private String checkoperator;
	private Date checktime;
	private int isprint;
	private Date printtime;
	private long sampleid;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PROCESS")
	@SequenceGenerator(name = "SEQ_PROCESS", sequenceName = "process_sequence", allocationSize=1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "sample_id")
	public long getSampleid() {
		return sampleid;
	}

	public void setSampleid(long sampleid) {
		this.sampleid = sampleid;
	}

	@Column
	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	@Column
	public Date getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}

	@Column
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	@Column
	public Date getExecutetime() {
		return executetime;
	}

	public void setExecutetime(Date executetime) {
		this.executetime = executetime;
	}

	@Column
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Column
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	@Column
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column
	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	@Column
	public String getKsreceiver() {
		return ksreceiver;
	}

	public void setKsreceiver(String ksreceiver) {
		this.ksreceiver = ksreceiver;
	}

	@Column
	public Date getKsreceivetime() {
		return ksreceivetime;
	}

	public void setKsreceivetime(Date ksreceivetime) {
		this.ksreceivetime = ksreceivetime;
	}

	@Column
	public String getCheckoperator() {
		return checkoperator;
	}

	public void setCheckoperator(String checkoperator) {
		this.checkoperator = checkoperator;
	}

	@Column
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	@Column
	public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}

	@Column
	public Date getPrinttime() {
		return printtime;
	}

	public void setPrinttime(Date printtime) {
		this.printtime = printtime;
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
