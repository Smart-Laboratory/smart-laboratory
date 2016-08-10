package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.lis.ReasoningModify;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ReasoningModifyManager;
import com.smart.dao.lis.ReasoningModifyDao;

@Service("reasoningModifyManager")
public class ReasoningModifyManagerImpl extends GenericManagerImpl<ReasoningModify, Long> implements ReasoningModifyManager {

	private ReasoningModifyDao reasoningModifyDao;

	@Autowired
	public void setReasoningModifyDao(ReasoningModifyDao reasoningModifyDao) {
		this.reasoningModifyDao = reasoningModifyDao;
		this.dao = reasoningModifyDao;
	}

	public List<ReasoningModify> getBySampleId(String sampleId) {
		return reasoningModifyDao.getBySampleId(sampleId);
	}

	public int getAddNumber() {
		return reasoningModifyDao.getAddNumber();
	}

	public int getDragNumber() {
		return reasoningModifyDao.getDragNumber();
	}
}
