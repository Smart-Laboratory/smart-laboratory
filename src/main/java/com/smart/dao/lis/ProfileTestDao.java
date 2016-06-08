package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.ProfileTest;

/**
 * Title: ProfileTestDao
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:26
 * @Version:
 */
public interface ProfileTestDao extends GenericDao<ProfileTest, Long> {
	
	List<ProfileTest> getBySection(String lab);
	
	List <ProfileTest> getByProfileName(String profileName);
}
