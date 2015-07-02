package com.smart.service.rule;

import java.util.List;

import com.smart.model.rule.Index;
import com.smart.service.GenericManager;

/**
 *  指标Index的操作
 * @author Winstar
 *
 */
public interface IndexManager extends GenericManager<Index, Long> {

	/**
	 *  根据指标的部分名称获取指标列表
	 * @param indexName 指标名称
	 * @return
	 */
	List<Index> getIndexs(String indexName);
	
	/**
	 *  分页查找指标
	 * @param pageNum	第几页
	 * @param field	排序字段
	 * @param isAsc	排序方式
	 * @return
	 */
	List<Index> getIndexs(int pageNum, String field, boolean isAsc);
	
	/**
	 *  指标总数
	 * @return
	 */
	int getIndexsCount();
	
	/**
	 * 分页查找指标
	 * @param sample 样本来源
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Index> getIndexs(String sample, int pageNum, String field, boolean isAsc);
	
	/**
	 *  某一样本来源的指标总数
	 * @param sample
	 * @return
	 */
	int getIndexsCount(String sample);
	
	/**
	 *  根据名称模糊查找指标
	 * @param name
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc);
	
	/**
	 *  名称中包含name的指标数
	 * @param name
	 * @return
	 */
	int getIndexsByNameCount(String name);
	
	/**
	 *  获取指标
	 * @param indexId	4位的指标ID
	 * @return
	 */
	Index getIndex(String indexId);
}
