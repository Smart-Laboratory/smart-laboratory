package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.TaskDao;
import com.smart.model.lis.Task;

@Repository("taskDao")
public class TaskDaoHibernate extends GenericDaoHibernate<Task, Long> implements TaskDao {

	public TaskDaoHibernate() {
		super(Task.class);
	}

}
