package com.smart.dao.micro;

import com.smart.dao.GenericDao;
import com.smart.model.micro.DrugGroupDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/8 16:56
 * @Version:
 */
public interface DrugGroupDetailsDao  extends GenericDao<DrugGroupDetails, Long> {

    void saveDetails(List<DrugGroupDetails> drugGroupDetailses);

    @Transactional
    int getDrugDetailsCount(String groupId,int start, int end, String sidx, String sord);

    @Transactional
    List<Object[]>  getDrugDetails(String groupId,int start, int end, String sidx, String sord);

    @Transactional
    void removeById(String groupId,String drugId);

    @Transactional
    void removeByGroupId(String groupId);

    @Transactional
    List<DrugGroupDetails> getByGroupId(String groupId);
}
