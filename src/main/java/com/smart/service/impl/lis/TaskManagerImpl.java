package com.smart.service.impl.lis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.service.lis.TaskManager;
import com.smart.dao.lis.TaskDao;
import com.smart.model.lis.Task;
import com.smart.service.impl.GenericManagerImpl;

@Service("taskManager")
public class TaskManagerImpl extends GenericManagerImpl<Task, Long> implements TaskManager {

	@SuppressWarnings("unused")
	private TaskDao taskDao;

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.dao = taskDao;
		this.taskDao = taskDao;
	}
	
	
}
