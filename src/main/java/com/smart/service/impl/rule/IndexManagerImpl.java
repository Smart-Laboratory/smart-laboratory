package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.IndexDao;
import com.smart.model.rule.Index;
import com.smart.service.rule.IndexManager;
import com.smart.service.impl.GenericManagerImpl;

@Service("indexManager")
public class IndexManagerImpl extends GenericManagerImpl<Index, Long> implements IndexManager {

	private IndexDao indexDao;
	
	@Autowired
	public void setIndexDao(IndexDao indexDao) {
		this.dao = indexDao;
		this.indexDao = indexDao;
	}

	public List<Index> getIndexs(String indexName) {
		return indexDao.getIndexs(indexName);
	}

	public List<Index> getIndexs(int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexs(pageNum, field, isAsc);
	}

	public List<Index> getIndexs(String query,String departmentid,boolean isAdmin,int start, int end, String sidx, String sord) {
		String querySql = " ";
		if(!query.equals("")) querySql += " and name like '%"+query +"%' ";
		if(!departmentid.equals("") && !departmentid.equals("other")){
			departmentid+=",";
			querySql += " and labdepartment like '%"+departmentid+"%'";
		}else {
			if(isAdmin){
				querySql += " and (labdepartment is null or labdepartment='')";
			}else {
				querySql += " and (labdepartment is not null or labdepartment != '')";
			}
		}
		return indexDao.getIndexs(querySql,start,end,sidx,sord);
	}

	public int getIndexsCount(String query, String departmentid,boolean isAdmin,int start, int end, String sidx, String sord) {
		String querySql = " ";
		if(!query.equals("")) querySql += " and name like '%"+query +"%' ";
		if(!departmentid.equals("") && !departmentid.equals("other")){
			departmentid+=",";
			querySql += "  and  labdepartment like '%"+departmentid+"%'";
		}else {
			if(isAdmin){
				querySql += "  and  (labdepartment is null or labdepartment='')";
			}else {
				querySql += "  and  (labdepartment is not null or labdepartment != '')";
			}
		}
		return indexDao.getIndexsCount(querySql,start,end,sidx,sord);
	}

	public List<Index> getIndexs(String sample, int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexsByCategory(sample, pageNum, field, isAsc);
	}

	public int getIndexsCount() {
		return indexDao.getIndexsCount();
	}
	
	public List<Index> getIndexsByIdandLab(String indexId ,String labDepartment){
		return indexDao.getIndexsByIdandLab(indexId,labDepartment);
	}

	public int getIndexsCount(String sample) {
		return indexDao.getIndexsCount(sample);
	}

	public Index getIndex(String indexId) {
		return indexDao.getIndex(indexId);
	}


	@Override
	/**
	 * 根据ID获取项目列表
	 */
	public List<Index> getIndexsByQueryIds(String ids) {
		String query = " and indexId in(" + ids + ")";
		return indexDao.getIndexsByQuery(query);
	}

	/**
	 * 微生物列表
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	public List<Index> getBacteriaList(String query, int start, int end, String sidx, String sord) {
		return indexDao.getBacteriaList(query,start,end,sidx,sord);
	}

	/**
	 * 微生物记录数
	 * @param query
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	public int getBacteriaListCount(String query, int start, int end, String sidx, String sord) {
		return indexDao.getBacteriaListCount(query,start,end,sidx,sord);
	}

	public List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexsByName(name, pageNum, field, isAsc);
	}

	public int getIndexsByNameCount(String name) {
		return indexDao.getIndexsByNameCount(name);
	}

	public Index getBacteriaById(String id){
		return indexDao.getBacteriaById(id);
	}

	public List<Index> getAntibioticList(String query, int start, int end, String sidx, String sord) {
		return indexDao.getAntibioticList(query,start,end,sidx,sord);
	}

	public int getAntibioticListCount(String query, int start, int end, String sidx, String sord) {
		return indexDao.getAntibioticCount(query,start,end,sidx,sord);
	}

	public Index getAntibioticById(String id){
		return indexDao.getAntibioticById(id);
	}
}
