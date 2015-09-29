package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.text.ParseException;
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

@Repository("sampleDao")
public class SampleDaoHibernate extends GenericDaoHibernate<Sample, Long> implements SampleDao {

	public SampleDaoHibernate() {
        super(Sample.class);
    }

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleList(String date, String lab, String code, int mark, int status) {
		if (StringUtils.isEmpty(lab)) {
			return null;
		}
		
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("from Sample s where section.code=?");
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
			builder.append("from Sample s where hasimages=1 order by sampleNo");
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
		Query query = getSession().createQuery(builder.toString()).setString(0, lab);
		if(!code.isEmpty()) {
			query = query.setString(1, date + code + "%");
		} else {
			query = query.setString(1, date + "%");
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

	public List<Sample> getHistorySample(String patientId, String blh) {
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
	                        + to + "' order by s.sampleNo desc").list();
	        return infos;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Sample getBySampleNo(String sampleNo){
		return (Sample)getSession().createQuery("from Sample s where s.sampleNo='"+sampleNo+"'").uniqueResult();
	}
}
