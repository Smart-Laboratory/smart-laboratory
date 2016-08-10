package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.AuditTrace;
import com.smart.model.lis.ProfileTest;
import com.smart.service.GenericManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: ProfileTestManager
 * Description: 试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:34
 * @Version:
 */
public interface ProfileTestManager extends GenericManager<ProfileTest,Long> {

	/**
	 * 根据部门编号获取ProfileTest List
	 * @param lab
	 * @return
	 */
	List<ProfileTest> getBySection(String lab);
	
	List <ProfileTest> getByProfileName(String profileName);

    List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord);
    int getProfileTestSize(String query);

}
