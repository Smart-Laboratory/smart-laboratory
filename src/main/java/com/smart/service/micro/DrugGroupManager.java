package com.smart.service.micro;

import com.smart.service.GenericManager;
import com.smart.model.micro.DrugGroup;

import java.util.List;

public interface DrugGroupManager extends GenericManager<DrugGroup, Long> {
    int getDrugGroupsCount(String query, int start, int end, String sidx, String sord);
    List<DrugGroup> getDrugGroups(String query, int start, int end, String sidx, String sord);
}