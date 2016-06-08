package com.smart.dao.hibernate.lis;

import java.util.List;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ProfileTestDao;
import com.smart.model.lis.ProfileTest;
import org.springframework.stereotype.Repository;

/**
 * Title: ProfileTestDaoHibernate
 * Description:套餐组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:27
 * @Version:
 */
@Repository("profileTestDao")
public class ProfileTestDaoHibernate extends GenericDaoHibernate<ProfileTest, Long> implements ProfileTestDao {
    public ProfileTestDaoHibernate() {
        super(ProfileTest.class);
    }
    @SuppressWarnings("unchecked")
    public List<ProfileTest> getBySection(String lab){
    	List s = null;
    	try {
			s =  getSession().createQuery(
					"from ProfileTest where section='" + lab + "' ").list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return s;
    }
    @SuppressWarnings("unchecked")
	public List <ProfileTest> getByProfileName(String profileName){
    	return getSession().createQuery("from ProfileTest where profileName='" + profileName + "' ").list();
	}
}
