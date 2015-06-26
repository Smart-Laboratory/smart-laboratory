package com.smart.dao.rule;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Index;

public interface IndexDao extends GenericDao<Index, Long> {

	/**
	 *  搜索一页的指标
	 * @param pageNum 页号
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Index> getIndexs(int pageNum, String field, boolean isAsc);
	
	/**
	 *  指标数
	 * @return
	 */
	int getIndexsCount();
	
	/**
	 *  
	 * @param sample
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Index> getIndexsByCategory(String sample, int pageNum, String field, boolean isAsc);
	
	/**
	 *  该样本来源下的指标数
	 * @param sample
	 * @return
	 */
	int getIndexsCount(String sample);
	
	/**
	 *   根据指标的部分名称，模糊搜索匹配的指标
	 * @param index
	 * @return
	 */
	List<Index> getIndexs(String indexName);
	
	/**
	 *  通过指标编号获得指标
	 * @param indexId
	 * @return
	 */
	Index getIndex(String indexId);
	
	/**
	 *  根据名称模糊搜索
	 * @param name
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	int getIndexsByNameCount(String name);
}
