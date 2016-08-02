package com.smart.service.micro;

import com.smart.model.micro.DrugGroupDetails;
import com.smart.service.GenericManager;
import com.smart.service.impl.GenericManagerImpl;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/8 16:59
 * @Version:
 */
public interface DrugGroupDetailsManager extends GenericManager<DrugGroupDetails,Long> {
    void  saveDetails(List<DrugGroupDetails> drugGroupDetailsList);
    int getDrugDetailsCount(String groupId,int start, int end, String sidx, String sord);
    List<Object[]>  getDrugDetails(String groupId,int start, int end, String sidx, String sord);
    List<DrugGroupDetails> getByGroupId(String groupId);
    void removeById(String groupId,String drugId);
    void removeByGroupId(String groupId);
}
