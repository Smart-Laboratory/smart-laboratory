package com.smart.dao.micro;

import com.smart.dao.GenericDao;

import com.smart.model.micro.DrugGroup;

import java.util.List;

/**
 * An interface that provides a data management interface to the DrugGroup table.
 */
public interface DrugGroupDao extends GenericDao<DrugGroup, Long> {
    int getDrugGroupsCount(String query, int start, int end, String sidx, String sord);
    List<DrugGroup> getDrugGroups(String query, int start, int end, String sidx, String sord);
}