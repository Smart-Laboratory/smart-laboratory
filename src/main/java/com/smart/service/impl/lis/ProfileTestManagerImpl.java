package com.smart.service.impl.lis;

import java.util.List;

import com.smart.dao.lis.ProfileTestDao;
import com.smart.dao.lis.SampleDao;
import com.smart.dao.lis.ProfileTestDao;
import com.smart.model.lis.ProfileTest;
import com.smart.service.GenericManager;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProfileTestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: ProfileTestManagerImpl
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:36
 * @Version:
 */
@Service("profileTestManager")
public class ProfileTestManagerImpl extends GenericManagerImpl<ProfileTest,Long> implements ProfileTestManager{
	private ProfileTestDao profileTestDao ;
	@Autowired
    public void setProfileTestDao(ProfileTestDao profileTestDao) {
    	this.dao = profileTestDao;
		this.profileTestDao = profileTestDao;
	}
	
	public List<ProfileTest> getBySection(String lab){
		return profileTestDao.getBySection(lab);
	}
	public List <ProfileTest> getByProfileName(String profileName){
		return profileTestDao.getByProfileName(profileName);
	}

    /**
     * 组合试验列表
     * @param query
     * @param start
     * @param end
     * @param sidx
     * @param sord
     * @return
     */
    public List<ProfileTest> getProfileTestList(String query, int start, int end, String sidx, String sord){
        return profileTestDao.getProfileTestList(query,start,end,sidx,sord);
    }

    /**
     * 组合试验记录数
     * @param query
     * @return
     */
    public int getProfileTestSize(String query){
        return profileTestDao.getProfileTestSize(query);
    }
}
