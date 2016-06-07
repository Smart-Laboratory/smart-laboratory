package com.smart.dao.hibernate.lis;

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
}
