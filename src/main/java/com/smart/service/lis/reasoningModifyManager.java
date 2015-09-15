package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.ReasoningModify;;

public interface reasoningModifyManager {

	/**
	 *  获取某样本的解释列表
	 * @param sampleId
	 * @return
	 */
	List<ReasoningModify> getBySampleId(String docNo);

	int getAddNumber();

	int getDragNumber();
}
