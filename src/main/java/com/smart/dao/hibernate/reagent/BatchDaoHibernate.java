package com.smart.dao.hibernate.reagent;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.BatchDao;
import com.smart.model.reagent.Batch;

@Repository("batchDao")
public class BatchDaoHibernate extends GenericDaoHibernate<Batch, Long> implements BatchDao {

	public BatchDaoHibernate() {
		super(Batch.class);
	}

	public void saveAll(List<Batch> needSaveBatch) {
		Session s = getSession();
		for(Batch b : needSaveBatch) {
			s.saveOrUpdate(b);
		}
		s.flush();
	}

}
