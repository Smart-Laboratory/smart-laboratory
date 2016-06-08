package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ProfileTest;

import java.util.List;

/**
 * Title: ProfileTestDao
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:26
 * @Version:
 */
public interface ProfileTestDao extends GenericDao<ProfileTest, Long> {
    public List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord);
    public int getProfileTestSize(String query);
}
