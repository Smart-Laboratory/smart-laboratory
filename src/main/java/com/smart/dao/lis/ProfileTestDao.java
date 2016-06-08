package com.smart.dao.lis;

import java.util.List;

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
<<<<<<< HEAD
	
	List<ProfileTest> getBySection(String lab);
	
	List <ProfileTest> getByProfileName(String profileName);
=======
    public List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord);
    public int getProfileTestSize(String query);
>>>>>>> origin/master
}
