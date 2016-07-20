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
	 * 分页指标列表
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
     * @param sord
     * @return
     */
	List<Index> getIndexs(String query,String departmentid, boolean isAdmin,int start, int end, String sidx, String sord);

	/**
	 * 分页记录数
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	int getIndexsCount(String query, String departmentid,boolean isAdmin,int start, int end, String sidx, String sord);
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
	 * 根据部门和id获取字典信息
	 * @param indexId
	 * @param labDepartment
	 * @return
	 */
	List getIndexsByIdandLab(String indexId ,String labDepartment);
	
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

	/**
	 * 按id集合获取指标
	 * @param ids
	 * @return
     */
	List<Index> getIndexsByQueryIds(String ids);

	/**
	 * 获取细菌列表
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	List<Index> getBacteriaList(String query,int start, int end, String sidx, String sord);

	/**
	 * 获取细菌列表记录数
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	int getBacteriaListCount(String query,int start, int end, String sidx, String sord);

	/**
	 * 细菌
	 * @param id
	 * @return
     */
	Index getBacteriaById(String id);

	/**
	 * 抗生素列表
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	List<Index> getAntibioticList(String query, int start, int end, String sidx, String sord) ;

	/**
	 * 抗生素记录数
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	int getAntibioticListCount(String query, int start, int end, String sidx, String sord);

	/**
	 * 抗生素
	 * @param id
	 * @return
     */
	Index getAntibioticById(String id);

}
