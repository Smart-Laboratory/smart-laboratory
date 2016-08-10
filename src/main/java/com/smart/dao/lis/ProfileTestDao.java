package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	List<ProfileTest> getBySection(String lab);
	
	@Transactional
	List <ProfileTest> getByProfileName(String profileName);
    
	@Transactional
    List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord);
    
	@Transactional
    int getProfileTestSize(String query);
}
