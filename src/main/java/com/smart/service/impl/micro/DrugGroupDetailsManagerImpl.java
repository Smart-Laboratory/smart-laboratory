package com.smart.service.impl.micro;

import com.smart.dao.micro.DrugGroupDetailsDao;
import com.smart.model.micro.DrugGroupDetails;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.micro.DrugGroupDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/8 17:00
 * @Version:
 */
@Service("drugGroupDetailsManager")
public class DrugGroupDetailsManagerImpl extends GenericManagerImpl<DrugGroupDetails,Long> implements DrugGroupDetailsManager {
    private DrugGroupDetailsDao drugGroupDetailsDao = null;

    @Autowired
    public void setDrugGroupDetailsDao(DrugGroupDetailsDao drugGroupDetailsDao){
        this.dao = drugGroupDetailsDao;
        this.drugGroupDetailsDao = drugGroupDetailsDao;
    }

    public void saveDetails(List<DrugGroupDetails> drugGroupDetailsList) {
        drugGroupDetailsDao.saveDetails(drugGroupDetailsList);
    }

    public int getDrugDetailsCount(String groupId, int start, int end, String sidx, String sord) {
        return drugGroupDetailsDao.getDrugDetailsCount(groupId,start,end,sidx,sord);
    }

    public List<Object[]>  getDrugDetails(String groupId, int start, int end, String sidx, String sord) {
        return drugGroupDetailsDao.getDrugDetails(groupId,start,end,sidx,sord);
    }

    public List<DrugGroupDetails> getByGroupId(String groupId) {
        return drugGroupDetailsDao.getByGroupId(groupId);
    }

    public void removeById(String groupId,String drugId){
        drugGroupDetailsDao.removeById(groupId,drugId);
    }
    
    public void removeByGroupId(String groupId){
        drugGroupDetailsDao.removeByGroupId(groupId);
    }
}
