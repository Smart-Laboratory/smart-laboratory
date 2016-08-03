package com.smart.dao.hibernate.lis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProcessDao;
import com.smart.model.lis.Process;

@Repository("processDao")
public class ProcessDaoHibernate extends GenericDaoHibernate<Process, Long> implements ProcessDao {
	
	private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public ProcessDaoHibernate(){
		super(Process.class);
	}

	public void removeBySampleId(long id) {
		getSession().createSQLQuery("delete from l_process where sample_id=" + id).executeUpdate();
	}

	public Process getBySampleId(Long sampleid) {
		return (Process) getSession().createQuery("from Process where sampleid=" + sampleid).uniqueResult();
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
				+ "to_date('"+ymdh.format(starttime)+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+ymd.format(starttime)+" 23:59:59','yyyy-mm-dd hh24:mi:ss') order by p.sendtime desc";
		return getSession().createQuery(hql).list();
	}

}