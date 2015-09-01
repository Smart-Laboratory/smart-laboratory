package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.Process;
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
			builder.append("audit.auditStatus>-1");
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
			builder.append("audit.auditStatus=");
			builder.append(status);
		}
		if (mark != 0) {
			builder.append(" and audit.auditMark=");
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
		return getSession().createQuery("from Sample  where sampleNo='" + sampleno + "' order by id").list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getNeedAudit(String day) {
		Query q =  getSession().createQuery("from Sample  where sampleNo like '" + day + "%' and (auditStatus=0 or auditMark=4) order by id");
		q.setFirstResult(0);
		q.setMaxResults(100);
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
	public List<Sample> getDiffCheck(Sample info) {
		Date toDate = null;
		for(Process process : info.getProcess()) {
			if(process.getOperation().equals(Constants.PROCESS_RECEIVE)) {
				toDate = process.getTime();
			}
		}
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(toDate); 
        calendar.add(Calendar.DATE,-180); 
        Date fromDate = calendar.getTime();
        
		List<Sample> infos = getSession().createQuery(
                "from PatientInfo p where (p.patientId='" + info.getPatientId() + "' or p.patientId='" + info.getPatient().getBlh() + "') and p.receivetime between to_date('" + Constants.SDF.format(fromDate) + "','"
                        + Constants.DATEFORMAT + "') and to_date('" + Constants.SDF.format(toDate) + "','" + Constants.DATEFORMAT
                        + "') order by p.receivetime desc").list();
        return infos;
	}
	
}
