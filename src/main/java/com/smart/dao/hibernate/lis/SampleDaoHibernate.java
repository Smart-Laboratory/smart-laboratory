package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.Sample;

@Repository("sampleDao")
public class SampleDaoHibernate extends GenericDaoHibernate<Sample, Long> implements SampleDao {

	public SampleDaoHibernate() {
        super(Sample.class);
    }

	public List<Sample> getSampleList(String date, String lab, String code, int mark, int status) {
		if (StringUtils.isEmpty(lab)) {
			return null;
		}
		
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("from Sample as s left join s.audit as a");
		
		builder.append(" where s.section_id='");
		if(code.isEmpty()) {
			
		}
		
		String[] cds = code.split(",");
		
		builder.append("from Sample as s left join s.audit as a where s.section_id='");
		builder.append(lab);
		builder.append("'");
		if (status == -3) {
			// all
		} else if (status == -2) {
			builder.append(" and ");
			builder.append("p.auditStatus>-1");
		} else if(status == 3){
			builder.append(" and ");
			builder.append("p.modifyFlag=1");
		} else if(status == 4){
			builder.append(" and ");
			builder.append("p.resultStatus<5");
		} else if(status == 5){
			builder = new StringBuilder();
			builder.append("from Sample where p.hasimages=1 order by p.sampleNo");
			return null;
		} else {
			builder.append(" and ");
			builder.append("p.auditStatus=");
			builder.append(status);
		}
		if (mark != 0) {
			builder.append(" and p.auditMark=");
			builder.append(mark);
		}
		builder.append(" and (");
		for (int i=0; i<cds.length; i++) {
			builder.append("p.sampleNo like '");
			builder.append(date);
			builder.append(cds[i]);
			builder.append("%'");
			if (cds.length != i+1) {
				builder.append(" or ");
			}
		}
		builder.append(") order by p.sampleNo");
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getListBySampleNo(String sampleno) {
		return  getSession().createQuery("from Sample where sampleNo='" + sampleno + "' order by upper(c.id)").list();
	}
	
}
