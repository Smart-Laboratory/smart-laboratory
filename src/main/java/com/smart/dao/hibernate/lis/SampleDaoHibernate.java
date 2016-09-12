package com.smart.dao.hibernate.lis;

import com.smart.dao.lis.SampleDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
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

	private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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
			builder.append("from Sample where sectionId in (" + lab + ")");
		} else {
			builder.append("from Sample where sectionId=" + lab);
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
		System.out.println(builder.toString());
		Query query = getSession().createQuery(builder.toString());

		System.out.println(query.getQueryString());
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
		Query q =  session.createQuery("from Sample where sampleNo like '" + day + "%' and (auditStatus=0 or auditMark=4) and sampleStatus<5 order by auditMark");
		q.setFirstResult(0);
		q.setMaxResults(500);
		List<Sample> list = q.list();
		return list;
	}

	public List<Sample> saveAll(List<Sample> updateSample) {
		Session s = getSessionFactory().openSession();
		List<Sample> returnList  = new ArrayList<Sample>();
		for(Sample sample : updateSample) {
			returnList.add((Sample)s.merge(sample));
		}
		s.flush();
		s.close();
        return returnList;
    }

	@SuppressWarnings("unchecked")
	public List<Sample> getHistorySample(String patientId, String blh, String lab) {

		if(blh != null && !blh.equals("null")){
			if(lab.isEmpty()) {
				return getSession().createQuery("from Sample s where s.patientblh ='" + blh + "' order by s.id desc").list();
			} else {
				if(lab.contains(",")) {
					return getSession().createQuery("from Sample s where s.patientblh ='" + blh + "' and s.sectionId in (" + lab + ") order by s.id desc").list();
				} else {
					return getSession().createQuery("from Sample s where s.patientblh ='" + blh + "' and s.sectionId=" + lab + " order by s.id desc").list();
				}
			}
		}else if(patientId!=null && !patientId.isEmpty()){
			if(lab.isEmpty()) {
				return getSession().createQuery("from Sample s where s.patientId ='" + patientId + "' order by s.id desc").list();
			} else {
				if(lab.contains(",")) {
					return getSession().createQuery("from Sample s where s.patientId ='" + patientId + "' and s.sectionId in (" + lab + ") order by s.id desc").list();
				} else {
					return getSession().createQuery("from Sample s where s.patientId ='" + patientId + "' and s.sectionId=" + lab + " order by s.id desc").list();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getDiffCheck(String patientid, String blh, String sampleno, String lab) {
		try {
			Date todate = Constants.DF3.parse(sampleno.substring(0, 8));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(todate);
			calendar.add(Calendar.DATE,-180);
			Date fromdate = calendar.getTime();
			String from = Constants.DF3.format(fromdate);
			List<Sample> infos = getSession().createQuery(
					"from Sample s where (s.patientblh='" + patientid + "' or s.patientblh='" + blh + "') and s.sampleNo>='" + from + "' and s.sampleNo<='"
							+ sampleno + "' and s.sectionId in (" + lab + ") order by s.sampleNo desc").list();
			return infos;
		} catch (ParseException e) {
			//e.printStackTrace();
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

		if(code == null) {
			code = "";
		}
		String[] cds = code.split(",");
		StringBuilder builder = new StringBuilder();
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


		int unaudit = ((Number)getSession().createQuery("select count(p) from Sample p where p.sectionId" +
				builder.toString() + " and p.auditStatus=0").uniqueResult()).intValue();
		int unpass = ((Number)getSession().createQuery("select count(p) from Sample p where p.sectionId" +
				builder.toString() + " and p.auditStatus=2").uniqueResult()).intValue();
		int danger = ((Number)getSession().createQuery("select count(p) from Sample p, CriticalRecord c where c.sampleid = p.id and p.sectionId" +
				builder.toString() + " and p.auditMark=6 and c.criticalDealFlag=0").uniqueResult()).intValue();

		List<Integer> list = new ArrayList<Integer>();
		list.add(unaudit);
		list.add(unpass);
		list.add(danger);

		if (!date.equals("________")) {
			int needwriteBack = ((Number)getSession().createQuery("select count(p) from Sample p where p.sectionId" +
					builder.toString() + " and p.writeback!=0").uniqueResult()).intValue();
			list.add(needwriteBack);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleByPatientName(String from, String to,String pName) {
//		String hql = "select p from Sample p,Process s where s.sample=p and p.patient.patientName='" + pName + "' and s.operation='接收' and s.time between to_date('" + from + " 00:00:00','"
//                    + DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + DATEFORMAT
//                    + "') order by s.time desc";
		String hql = "select s from Sample s where s.patientname='" + pName + "' ";
		if(!StringUtils.isEmpty(from) && from!=null && to!=null &&!StringUtils.isEmpty(to)){
			hql+="and SUBSTR(s.sampleNo,1,8)>='" + from + "' and SUBSTR(s.sampleNo,1,8)<='" + to + "' order by s.sampleNo desc";
		}
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleBySearchType(String fromDate, String toDate, String searchType, String text){
		String hql = "select s from Sample s where s."+searchType+"='" + text + "' ";
		if(!StringUtils.isEmpty(fromDate) && fromDate!=null && toDate!=null &&!StringUtils.isEmpty(toDate)){
			hql+="and SUBSTR(s.sampleNo,1,8)>='" + fromDate + "' and SUBSTR(s.sampleNo,1,8)<='" + toDate + "' order by s.sampleNo desc";
		}
		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end) {
		String sql = "from Sample s where s.sectionId in (" + lab + ") ";
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
				} else {
					sql += "and s.sampleNo like '" + text.substring(0, 8) + "%'";
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
			default:
				String date = Constants.DF3.format(new Date());
				sql += "and (";
				for (int i=0; i<cds.length; i++) {
					sql += "s.sampleNo like '" + date + cds[i] + "%'";
					if (cds.length != i+1) {
						sql += " or ";
					}
				}
				sql += ")";
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

		System.out.print(q.getQueryString());
		if( end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.list();
	}

	public int getSampleCount(String text, String lab, int mark, int status, String code) {
		String sql = "select count(s.id) from Sample s where s.sectionId in (" + lab + ") ";
		String[] cds = new String[0];
		if(code != null) {
			cds = code.split(",");
		}
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
			default:
				String date = Constants.DF3.format(new Date());
				sql += "and (";
				for (int i=0; i<cds.length; i++) {
					sql += "s.sampleNo like '" + date + cds[i] + "%'";
					if (cds.length != i+1) {
						sql += " or ";
					}
				}
				sql += ")";
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

	@SuppressWarnings("unchecked")
	public List<NeedWriteCount> getAllWriteBack(String date) {
		String sql = "select sampleno from l_sample where sampleno like '" + date + "%' and writeback=1";
		List<String> list = getSession().createSQLQuery(sql).list();
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

	@SuppressWarnings("unchecked")
	public List<Sample> getByIds(String ids) {
		return getSession().createQuery("from Sample where id in (" + ids + ")").list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getBysampleNos(String ids) {
		try {
			return getSession().createQuery("from Sample where sampleNo in (" + ids + ")").list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getSampleByCode(String code) {
		return getSession().createQuery("from Sample where sampleNo like '"+ code +"%'").list();
	}

	public boolean existSampleNo(String sampleno){
		String sql = "select count(*) from Sample where sampleno = '"+sampleno+"'";
		Query q = getSession().createQuery(sql);
		return ((Number) (q.uniqueResult())).intValue() != 0;
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getByPatientId(String patientId,String lab){
		String hql = "from Sample where patientId = '"+patientId+"'";
		if(lab!=null && !lab.isEmpty()){
			hql += " and lab='"+lab+"' ";
		}
		hql += " and rownum <4";
		return getSession().createQuery(hql).list();
	}

	public String getReceiveSampleno(String name, String lab, String today) {
		String sql = "select s.sampleno from l_sample s, l_process p where s.section_id='" + lab + "' and p.receiver='" + lab + "' and s.sampleno like '" + today + "%' and rownum=1 order by s.sampleno desc";
		return (String)getSession().createSQLQuery(sql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getReceiveList(String text, String lab) {
		//String hql = "from Sample where sampleNo like '" + text + "%' and sectionId='" + lab + "' order by sampleNo desc";
		String sql = "select s from Sample s, Process p where s.id=p.sampleid and s.sectionId = '" + lab + "' and s.sampleNo like '" + text + "%' order by p.receivetime desc";
		return getSession().createQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Sample> getOutList(String sender,Date sendtime){
		String sql = "select s from Sample s, Process p where s.id=p.sampleid and p.sender='"+sender+"' and p.sendtime > "
				+ "to_date('"+ymd.format(sendtime)+"','yyyy-mm-dd hh24:mi:ss') order by p.sendtime desc";
		return getSession().createQuery(sql).list();
	}

	public Long getSampleId() {
        String sql = "select  sample_sequence.nextval nextvalue from dual";
        return (Long)(getSession().createSQLQuery(sql).addScalar("nextvalue", StandardBasicTypes.LONG) ).uniqueResult();
	}

	public void removeAll(List<Sample> list) {
		Session s = getSessionFactory().openSession();
		for(Sample sample : list) {
			s.delete(sample);
		}
		s.flush();
		s.close();
	}
}