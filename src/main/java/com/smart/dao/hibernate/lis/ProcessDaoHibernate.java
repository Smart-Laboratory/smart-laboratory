package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProcessDao;
import com.smart.model.lis.Process;

@Repository("processDao")
public class ProcessDaoHibernate extends GenericDaoHibernate<Process, Long> implements ProcessDao {
	
	public ProcessDaoHibernate(){
		super(Process.class);
	}

	public void removeBySampleId(long id) {
		getSession().createSQLQuery("delete from l_process where sample_id=" + id).executeUpdate();
	}

}