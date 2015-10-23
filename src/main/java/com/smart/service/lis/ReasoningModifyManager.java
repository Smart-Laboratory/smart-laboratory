package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.ReasoningModify;
import com.smart.service.GenericManager;;

public interface ReasoningModifyManager extends GenericManager<ReasoningModify, Long> {

	/**
	 *  获取某样本的解释列表
	 * @param sampleId
	 * @return
	 */
	List<ReasoningModify> getBySampleId(String sampleId);

	int getAddNumber();

	int getDragNumber();
}
