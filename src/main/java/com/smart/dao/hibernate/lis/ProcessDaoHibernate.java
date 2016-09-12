package com.smart.dao.hibernate.lis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProcessDao;
import com.smart.model.lis.Process;
import com.smart.model.lis.Sample;

@Repository("processDao")
public class ProcessDaoHibernate extends GenericDaoHibernate<Process, Long> implements ProcessDao {
	
	
	public ProcessDaoHibernate(){
		super(Process.class);
	}

	public void removeBySampleId(long id) {
		getSession().createSQLQuery("delete from l_process where sample_id=" + id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public Process getBySampleId(Long sampleid) {
		List<Process> p = getSession().createQuery("from Process where sampleid=" + sampleid).list();
		if(p!=null && p.size()>0)
			return p.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Process> getHisProcess(String sampleids) {
		return getSession().createQuery("from Process where sampleid in (" + sampleids + ")").list();
	}

	@SuppressWarnings("unchecked")
	public List<Process> getBySampleCondition(String text, String lab) {
		return getSession().createQuery("select p from Sample s, Process p where s.id=p.sampleid and s.sectionId = '" + lab + "' and s.sampleNo like '" + text + "%'").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Process> getOutList(String sender, Date starttime){
		String hql = "from Process p where p.sender='"+sender+"' and p.sendtime between "
				+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') order by p.sendtime desc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Process> getSendList(String receiver, Date starttime, Date endtime,int start,int end){
		String hql = "";
		if(endtime == null){
			hql = "from Process p where p.sender='"+receiver+"' and p.sendtime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "from Process p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		hql += " order by p.sendtime desc";
		
		Query q = getSession().createQuery(hql);
		if(start!=0 && end !=0 && start<=end){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Process> getReceiveList(String receiver, Date starttime, Date endtime,int start,int end){
		String hql = "";
		if(endtime == null){
			hql = "from Process p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "from Process p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		hql += " order by p.ksreceivetime desc";
		
		Query q = getSession().createQuery(hql);
		if(start!=0 && end !=0 && start<=end){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.list();
	}
	
	public int getReceiveListCount(String receiver, Date starttime, Date endtime){
		String hql = "";
		if(endtime == null){
			hql = "select count(*) from l_process p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "select count(*) from l_process p where p.ksreceiver='"+receiver+"' and p.ksreceivetime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		hql += " order by p.ksreceivetime desc";
		
		JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		return jdbcTemplate.queryForObject(hql, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getReceiveListBySection(String section, Date starttime, Date endtime,int sampleState){
		String hql = "";
		if(endtime == null){
			hql = "select s.*, p.* from L_SAMPLE s, L_PROCESS p where  s.id=p.SAMPLE_ID and s.SECTION_ID='"+section+"' and p.receivetime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') order by p.receivetime desc";
		}else{
			hql = "select s.*,p.* from L_SAMPLE s, L_PROCESS p where  s.id=p.SAMPLE_ID and s.SECTION_ID='"+section+"' and p.receivetime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		switch (sampleState) {//1:全部;2:已采集;3:已送出;4:科室接收;5:组内接受;6:已审核
		case 1:
			
			break;
		case 2:
			hql += " and p.executetime is not null and p.sendtime is null and p.ksreceivetime is null and p.receivetime is null";
			break;
		case 3:
			hql += " and p.executetime is not null and p.sendtime is not null and p.ksreceivetime is null and p.receivetime is null";
			break;
		case 4:
			hql += " and p.executetime is not null  and p.ksreceivetime is not null and p.receivetime is null";
			break;
		case 5:
			hql += " and p.executetime is not null  and p.receivetime is not null ";
			break;
		case 6:
			hql += " and  p.checktime is not null ";
			break;
		default:
			break;
		}
		
		hql += " order by p.receivetime desc";
		System.out.println(hql);
		
		return getSession().createSQLQuery(hql).addEntity("s", Sample.class).addEntity("p", Process.class).list();
	}
	
	public int getSendListCount(String sender, Date starttime, Date endtime){
		String hql = "";
		if(endtime == null){
			hql = "select count(*) from l_process p where p.sender='"+sender+"' and p.sendtime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			hql = "select count(*) from l_process p where p.sender='"+sender+"' and p.sendtime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
		}
		
		hql += " order by p.sendtime desc";
		
		JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
		return jdbcTemplate.queryForObject(hql, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSendListBySection(String section, Date starttime, Date endtime,int sampleState){
		String hql = "";
		if(endtime == null){
			hql = "select s.*, p.* from L_SAMPLE s, L_PROCESS p where  s.id=p.SAMPLE_ID and s.SECTION_ID='"+section+"' and p.sendtime between "
					+ "to_date('"+Constants.SDF.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') order by p.sendtime desc";
		}else{
			hql = "select s.*,p.* from L_SAMPLE s, L_PROCESS p where  s.id=p.SAMPLE_ID and s.SECTION_ID='"+section+"' and p.sendtime between "
					+ "to_date('"+Constants.DF2.format(starttime)+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and to_date('"+Constants.DF2.format(endtime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		switch (sampleState) {//1:全部;2:已采集;3:已送出;4:科室接收;5:组内接受;6:已审核
		case 1:
			
			break;
		case 2:
			hql += " and p.executetime is not null and p.sendtime is null and p.ksreceivetime is null and p.receivetime is null";
			break;
		case 3:
			hql += " and p.executetime is not null and p.sendtime is not null and p.ksreceivetime is null and p.receivetime is null";
			break;
		case 4:
			hql += " and p.executetime is not null  and p.ksreceivetime is not null and p.receivetime is null";
			break;
		case 5:
			hql += " and p.executetime is not null  and p.receivetime is not null ";
			break;
		case 6:
			hql += " and  p.checktime is not null ";
			break;
		default:
			break;
		}
		
		hql += " order by p.sendtime desc";
		System.out.println(hql);
		
		return getSession().createSQLQuery(hql).addEntity("s", Sample.class).addEntity("p", Process.class).list();
	}

	public List<Process> saveAll(List<Process> list) {
		Session s = getSessionFactory().openSession();
		List<Process> returnList = new ArrayList<Process>();
		for(Process process : list) {
			returnList.add((Process) s.merge(process));
		}
		s.flush();
		s.close();
        return returnList;
    }

	public void removeAll(List<Process> list) {
		Session s = getSessionFactory().openSession();
		for(Process process : list) {
			s.delete(process);
		}
		s.flush();
		s.close();
	}

}