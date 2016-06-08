package com.smart.model.lis;

import java.util.Date;

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
 * 样本流转信息日志表
 */
@Entity
@Table(name = "l_process_log")
public class ProcessLog extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -158140239103104718L;
	
	private long id;
	private long sampleLogId;
	
	private long processid;
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
	
	private String logger;
	private Date logtime;
	private String logip;
	private String logoperate;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PROCESS_LOG")
	@SequenceGenerator(name = "SEQ_PROCESS_LOG", sequenceName = "process_log_sequence", allocationSize=1)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "sample_log_id")
	public long getSampleLogId() {
		return sampleLogId;
	}

	public void setSampleLogId(long sampleLogId) {
		this.sampleLogId = sampleLogId;
	}

	@Column(name = "process_id")
	public long getProcessid() {
		return processid;
	}
	
	public void setProcessid(long processid) {
		this.processid = processid;
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
	
	@Column(name = "sample_id")
	public long getSampleid() {
		return sampleid;
	}
	
	public void setSampleid(long sampleid) {
		this.sampleid = sampleid;
	}
	
	@Column
	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	@Column
	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	@Column
	public String getLogip() {
		return logip;
	}

	public void setLogip(String logip) {
		this.logip = logip;
	}
	
	@Column
	public String getLogoperate() {
		return logoperate;
	}

	public void setLogoperate(String logoperate) {
		this.logoperate = logoperate;
	}

	@Transient
	public Process getProcessEntity() {
		Process p = new Process();
		p.setCheckoperator(this.checkoperator);
		p.setChecktime(this.checktime);
		p.setExecutetime(this.executetime);
		p.setExecutor(this.executor);
		p.setId(this.processid);
		p.setIsprint(this.isprint);
		p.setKsreceiver(this.ksreceiver);
		p.setKsreceivetime(this.ksreceivetime);
		p.setPrinttime(this.printtime);
		p.setReceiver(this.receiver);
		p.setReceivetime(this.receivetime);
		p.setRequester(this.requester);
		p.setRequesttime(this.requesttime);
		p.setSampleid(this.sampleid);
		p.setSender(this.sender);
		p.setSendtime(this.sendtime);
		return p;
	}
	
	public void setProcessEntity(Process p) {
		this.checkoperator = p.getCheckoperator();
		this.checktime = p.getChecktime();
		this.executetime = p.getExecutetime();
		this.executor = p.getExecutor();
		this.processid = p.getId();
		this.isprint = p.getIsprint();
		this.ksreceiver = p.getKsreceiver();
		this.ksreceivetime = p.getKsreceivetime();
		this.printtime = p.getPrinttime();
		this.receiver = p.getReceiver();
		this.receivetime = p.getReceivetime();
		this.requester = p.getRequester();
		this.requesttime = p.getRequesttime();
		this.sampleid = p.getSampleid();
		this.sender = p.getSender();
		this.sendtime = p.getSendtime();
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
