package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.Sample;
import com.smart.model.util.NeedWriteCount;


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
		builder.append("sampleNo like '");
		if(!code.isEmpty()) {
			builder.append(date + code);
		} else {
			builder.append(date);
		}
		builder.append("%' order by sampleNo");
		Query query = getSession().createQuery(builder.toString());
		List<Sample> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getListBySampleNo(String sampleno) {
		return getSession().createQuery("from Sample where sampleNo='" + sampleno + "' order by id").list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getNeedAudit(String day) {
		Session session = getSession();
		Query q =  session.createQuery("from Sample where sampleNo like '" + day + "%' and (auditStatus=0 or auditMark=4) order by auditMark");
		q.setFirstResult(0);
		q.setMaxResults(500);  
		List<Sample> list = q.list();
		for(Sample s : list) {
			s.getPatient();
			s.getResults().size();
			s.getProcess();
		}
		session.flush();
		return list;
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
	        for(Sample s : infos) {
	        	s.getResults().size();
	        }
	        return infos;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Sample getBySampleNo(String sampleNo){
		return (Sample)getSession().createQuery("from Sample s where s.sampleNo='"+sampleNo+"'").uniqueResult();
	}
	
public List<Integer> getAuditInfo(String date, String department, String code, String user) {
		
		if (StringUtils.isEmpty(department) ) {
			return null;
		}
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		String[] cds = code.split(",");
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
		
		builder.append(" and ");
		StringBuilder bld = new StringBuilder();
		bld.append("(");
		for (int i=0; i<cds.length; i++) {
			bld.append("p.sampleNo like '");
			bld.append(date);
			bld.append(cds[i]);
			bld.append("%'");
			if (cds.length != i+1) {
				bld.append(" or ");
			}
		}
		bld.append(")");
		builder.append(bld.toString());
		
		
		int unaudit = ((Number)getSession().createQuery(builder.toString() + " and p.auditStatus=0").uniqueResult()).intValue();
		int unpass = ((Number)getSession().createQuery(builder.toString() + " and p.auditStatus=2").uniqueResult()).intValue(); 
		int danger = ((Number)getSession().createQuery(builder.toString() + " and p.auditMark=6 and p.criticalRecord.criticalDealFlag=0").uniqueResult()).intValue(); 
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(unaudit);
		list.add(unpass);
		list.add(danger);
		
		StringBuilder builder2 = new StringBuilder();
		builder2.append("select count(s) from Sample s where s.section.code");
		if (department.contains(",")) {
			builder2.append(" in (");
			builder2.append(department);
			builder2.append(")");
		} else {
			builder2.append("=");
			builder2.append(department);
		}
		
		if (!date.equals("________")) {
			int needwriteBack = ((Number)getSession().createQuery(builder2.toString() + " and s.writeback!=0").uniqueResult()).intValue(); 
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

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end) {
		String sql = "from Sample s where s.section.code in (" + lab + ") ";
		String[] cds = code.split(",");
		switch (text.length()) {
		case 8:
			if (StringUtils.isNumeric(text)) {
				sql += "and (";
				for (int i=0; i<cds.length; i++) {
					sql += "s.sampleNo like '" + text + cds[i] + "%'";
					if (cds.length != i+1) {
						sql += " or ";
					}
				}
				sql += ")";
			}
			break;
		case 11:
			if (StringUtils.isNumeric(text.substring(0, 8)) && code.indexOf(text.substring(8)) != -1) {
				sql += "and s.sampleNo like '" + text + "%'";
			}
			break;
		case 14:
			if (StringUtils.isNumeric(text.substring(0, 8)) && StringUtils.isNumeric(text.substring(11)) && 
				code.indexOf(text.substring(8, 11)) != -1) {
				sql += "and s.sampleNo='" + text + "'";
			}
			break;
		case 18:
			if (text.indexOf('-') != 0 && StringUtils.isNumeric(text.substring(0, 8))
				&& StringUtils.isNumeric(text.substring(11, 14))
				&& StringUtils.isNumeric(text.substring(15, 18))
				&& code.indexOf(text.substring(8, 11)) != -1) {
				sql += "and s.sampleNo>='" + text.substring(0, 14) 
					+ "' and s.sampleNo<='" + text.substring(0, 11) + text.substring(15, 18) + "'";
			}
			break;
		}
		
		switch (status) {
		case -3:
			break;
		case -2:
			sql += " and s.auditStatus>-1";
			break;
		case 3:
			sql += " and s.modifyFlag=1";
			break;
		case 4:
			sql += " and s.sampleStatus<5";
			break;
		case 5:
			sql += " and s.hasimages=1";
			break;
		default:
			sql += " and s.auditStatus=" + status;
			break;
		}
		if (mark != 0) {
			sql += " and s.auditMark=" + mark;
		}
		sql += " order by s.sampleNo";
		Query q =  getSession().createQuery(sql);
		q.setFirstResult(start);
		q.setMaxResults(end); 
		return q.list();
	}

	public int getSampleCount(String text, String lab, int mark, int status, String code) {
		String sql = "select count(s.id) from Sample s where s.section.code in (" + lab + ") ";
		String[] cds = code.split(",");
		switch (text.length()) {
		case 8:
			if (StringUtils.isNumeric(text)) {
				sql += "and (";
				for (int i=0; i<cds.length; i++) {
					sql += "s.sampleNo like '" + text + cds[i] + "%'";
					if (cds.length != i+1) {
						sql += " or ";
					}
				}
				sql += ")";
			}
			break;
		case 11:
			if (StringUtils.isNumeric(text.substring(0, 8)) && code.indexOf(text.substring(8)) != -1) {
				sql += "and s.sampleNo like '" + text + "%'";
			}
			break;
		case 14:
			if (StringUtils.isNumeric(text.substring(0, 8)) && StringUtils.isNumeric(text.substring(11)) && 
				code.indexOf(text.substring(8, 11)) != -1) {
				sql += "and s.sampleNo='" + text + "'";
			}
			break;
		case 18:
			if (text.indexOf('-') != 0 && StringUtils.isNumeric(text.substring(0, 8))
				&& StringUtils.isNumeric(text.substring(11, 14))
				&& StringUtils.isNumeric(text.substring(15, 18))
				&& code.indexOf(text.substring(8, 11)) != -1) {
				sql += "and s.sampleNo>='" + text.substring(0, 14) 
					+ "' and s.sampleNo<='" + text.substring(0, 11) + text.substring(15, 18) + "'";
			}
			break;
		}
		
		switch (status) {
		case -3:
			break;
		case -2:
			sql += " and s.auditStatus>-1";
			break;
		case 3:
			sql += " and s.modifyFlag=1";
			break;
		case 4:
			sql += " and s.sampleStatus<5";
			break;
		case 5:
			sql += " and s.hasimages=1";
			break;
		default:
			sql += " and s.auditStatus=" + status;
			break;
		}
		if (mark != 0) {
			sql += " and s.auditMark=" + mark;
		}
		sql += " order by s.sampleNo";

		Query q =  getSession().createQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	public List<NeedWriteCount> getAllWriteBack(String date) {
		JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		String sql = "select sampleno from l_sample where sampleno like '" + date + "%' and writeback=1";
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		Map<String, Integer> cMap = new HashMap<String, Integer>();
		Map<String, String> sMap = new HashMap<String, String>();
		for(String s : list) {
			if(cMap.containsKey(s.substring(8, 11))) {
				cMap.put(s.substring(8, 11), cMap.get(s.substring(8, 11)) + 1);
			} else {
				cMap.put(s.substring(8, 11), 1);
			}
			if(sMap.containsKey(s.substring(8, 11))) {
				sMap.put(s.substring(8, 11), sMap.get(s.substring(8, 11)) + s + "<br/>");
			} else {
				sMap.put(s.substring(8, 11), s + "<br/>");
			}
		}
		List<NeedWriteCount> nwcList = new ArrayList<NeedWriteCount>();
		for(String code : cMap.keySet()) {
			NeedWriteCount nwc = new NeedWriteCount();
			nwc.setCode(code);
			nwc.setCount(cMap.get(code));
			nwc.setList(sMap.get(code));
			nwcList.add(nwc);
		}
		return nwcList;
	}
}
