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
		if(!query.equals("")) querySql += " and lab_index.name like '%"+query +"%' ";
		if(!departmentid.equals("") && !departmentid.equals("other")){
			departmentid+=",";
			querySql += "  and  lab_index.labdepartment like '%"+departmentid+"%'";
		}else {
			if(isAdmin){
				querySql += "  and  (lab_index.labdepartment is null or lab_index.labdepartment='')";
			}
		}
		return indexDao.getIndexs(querySql,start,end,sidx,sord);
	}

	public int getIndexsCount(String query, String departmentid,boolean isAdmin,int start, int end, String sidx, String sord) {
		String querySql = " ";
		if(!query.equals("")) querySql += " and lab_index.name like '%"+query +"%' ";
		if(!departmentid.equals("") && !departmentid.equals("other")){
			departmentid+=",";
			querySql += "  and  lab_index.labdepartment like '%"+departmentid+"%'";
		}else {
			if(isAdmin){
				querySql += "  and  (lab_index.labdepartment is null or lab_index.labdepartment='')";
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

	public List<Index> getIndexsByQuery(String query) {
		return indexDao.getIndexsByQuery(query);
	}

	public List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexsByName(name, pageNum, field, isAsc);
	}

	public int getIndexsByNameCount(String name) {
		return indexDao.getIndexsByNameCount(name);
	}
}
