package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.smart.model.lis.ReasoningModify;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.reasoningModifyManager;
import com.smart.dao.lis.ReasoningModifyDao;;

public class ReasoningModifyManagerImpl extends GenericManagerImpl<ReasoningModify, Long> implements reasoningModifyManager{

	private ReasoningModifyDao reasoningModifyDao;

	@Autowired
	public void setReasoningModifyDao(ReasoningModifyDao reasoningModifyDao) {
		this.reasoningModifyDao = reasoningModifyDao;
		this.dao = reasoningModifyDao;
	}

	public List<ReasoningModify> getBySampleId(String docNo) {
		return reasoningModifyDao.getBySampleId(docNo);
	}

	public int getAddNumber() {
		return reasoningModifyDao.getAddNumber();
	}

	public int getDragNumber() {
		return reasoningModifyDao.getDragNumber();
	}
}
