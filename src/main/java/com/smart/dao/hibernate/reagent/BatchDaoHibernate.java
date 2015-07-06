package com.smart.dao.hibernate.reagent;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.BatchDao;
import com.smart.model.reagent.Batch;

@Repository("batchDao")
public class BatchDaoHibernate extends GenericDaoHibernate<Batch, Long> implements BatchDao {

	public BatchDaoHibernate() {
		super(Batch.class);
	}

}
