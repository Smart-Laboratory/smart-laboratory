package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

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
		/*builder.append(date);
		if(!code.isEmpty()) {
			builder.append(code);
		}*/
		builder.append(" order by sampleNo");
		System.out.println(builder.toString());
		Query query = getSession().createQuery(builder.toString()).setString(0, lab);
		if(!code.isEmpty()) {
			query = query.setString(1, date + code + "%");
		} else {
			query = query.setString(1, date + "%");
		}
		List<Sample> sample = new ArrayList<Sample>();
		try {
			sample = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sample.size());
		return sample;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getListBySampleNo(String sampleno) {
		return getSession().createQuery("from Sample where sampleNo='" + sampleno + "' order by upper(c.id)").list();
	}
	
}
