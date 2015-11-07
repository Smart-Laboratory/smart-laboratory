package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.Sample;

import net.sf.ehcache.search.expression.And;

import com.smart.model.lis.Process;

@Repository("sampleDao")
public class SampleDaoHibernate extends GenericDaoHibernate<Sample, Long> implements SampleDao {

	public SampleDaoHibernate() {
        super(Sample.class);
    }

	private static String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
	
	@SuppressWarnings("unchecked")
	public List<Sample> getSampleList(String date, String lab, String code, int mark, int status) {
		if (StringUtils.isEmpty(lab)) {
			return null;
		}
		
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		StringBuilder builder = new StringBuilder();
		if(lab.contains(",")) {
			builder.append("from Sample where section.code in (" + lab + ")");
		} else {
			builder.append("from Sample where section.code=" + lab);
		}
		if (status == -3) {
			// all
		} else if (status == -2) {
			builder.append(" and ");
			builder.append("auditStatus>-1");
		} else if(status == 3){
			builder.append(" and ");
			builder.append("modifyFlag=1");
		} else if(status == 4){
			builder.append(" and ");
			builder.append("sampleStatus<5");
		} else if(status == 5){
			builder = new StringBuilder();
			builder.append("from Sample where hasimages=1 order by sampleNo");
			return getSession().createQuery(builder.toString()).list();
		} else {
			builder.append(" and ");
			builder.append("auditStatus=");
			builder.append(status);
		}
		if (mark != 0) {
			builder.append(" and auditMark=");
			builder.append(mark);
		}
		builder.append(" and ");
		builder.append("sampleNo like ?");
		builder.append(" order by sampleNo");
		Query query = getSession().createQuery(builder.toString());
		if(!code.isEmpty()) {
			query = query.setString(0, date + code + "%");
		} else {
			query = query.setString(0, date + "%");
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getListBySampleNo(String sampleno) {
		return getSession().createQuery("from Sample where sampleNo='" + sampleno + "' order by id").list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getNeedAudit(String day) {
		Query q =  getSession().createQuery("from Sample where sampleNo like '" + day + "%' and (auditStatus=0 or auditMark=4) order by id");
		q.setFirstResult(0);
		q.setMaxResults(500);             
		return q.list();
	}

	public void saveAll(List<Sample> updateSample) {
		Session s = getSession();
		for(Sample sample : updateSample) {
			s.saveOrUpdate(sample);
		}
		s.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getHistorySample(String patientId, String blh) {
		
		if(blh != null){
			return getSession().createQuery("from Sample s where s.patient.blh ='"+blh+"'  order by s.id desc").list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getDiffCheck(String patientid, String blh, String sampleno) {
		try {
			String to = sampleno.substring(0, 8);
			Date todate = Constants.DF3.parse(to);
			Calendar calendar = Calendar.getInstance(); 
	        calendar.setTime(todate); 
	        calendar.add(Calendar.DATE,-180); 
	        Date fromdate = calendar.getTime();
	        String from = Constants.DF3.format(fromdate);
	        List<Sample> infos = getSession().createQuery(
	                "from Sample s where (s.patientId='" + patientid + "' or s.patientId='" + blh + "') and s.sampleNo>='" + from + "' and s.sampleNo<='"
	                        + sampleno + "' order by s.sampleNo desc").list();
	        return infos;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Sample getBySampleNo(String sampleNo){
		return (Sample)getSession().createQuery("from Sample s where s.sampleNo='"+sampleNo+"'").uniqueResult();
	}
	
public List<Integer> getAuditInfo(String date, String department,String user) {
		
		if (StringUtils.isEmpty(department) ) {
			return null;
		}
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("select count(p) from Sample p where p.section.code");
		if (department.contains(",")) {
			builder.append(" in (");
			builder.append(department);
			builder.append(")");
		} else {
			builder.append("=");
			builder.append(department);
		}
		
		
		
		int unaudit = ((Number)getSession().createQuery(builder.toString() + " and p.auditStatus=0").uniqueResult()).intValue();
		int unpass = ((Number)getSession().createQuery(builder.toString() + " and p.auditStatus=2").uniqueResult()).intValue(); 
		int danger = ((Number)getSession().createQuery(builder.toString() + " and p.auditMark=6 and p.criticalRecord.criticalDealFlag=0").uniqueResult()).intValue(); 
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(unaudit);
		list.add(unpass);
		list.add(danger);
		
		StringBuilder builder2 = new StringBuilder();
		builder2.append("select count(s) from Sample s, Process p where s.section.code");
		if (department.contains(",")) {
			builder2.append(" in (");
			builder2.append(department);
			builder2.append(")");
		} else {
			builder2.append("=");
			builder2.append(department);
		}
		
		if (!date.equals("________")) {
			int needwriteBack = ((Number)getSession().createQuery(builder2.toString() + " and s.id = p.sample.id and p.operation='报告' and p.operator='" + user + "' and s.iswriteback!=0").uniqueResult()).intValue(); 
			list.add(needwriteBack);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleByPatientName(String from, String to,String pName) {
//		String hql = "select p from Sample p,Process s where s.sample=p and p.patient.patientName='" + pName + "' and s.operation='接收' and s.time between to_date('" + from + " 00:00:00','"
//                    + DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + DATEFORMAT
//                    + "') order by s.time desc";
		String hql = "select p from Sample p,Process s where s.sample=p and p.patient.patientName='" + pName + "' and s.operation='接收' ";
		if(!StringUtils.isEmpty(from) && from!=null && to!=null &&!StringUtils.isEmpty(to)){
			hql+="and s.time between to_date('" + from + " 00:00:00','"
                    + DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + DATEFORMAT
                    + "') order by s.time desc";
		}
		return getSession().createQuery(hql).list();
	}
}
