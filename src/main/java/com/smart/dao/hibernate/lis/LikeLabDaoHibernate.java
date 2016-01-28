package com.smart.dao.hibernate.lis;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.LikeLabDao;
import com.smart.model.lis.LikeLab;

@Repository("likeLabDao")
public class LikeLabDaoHibernate extends GenericDaoHibernate<LikeLab, Long> implements LikeLabDao {

	public LikeLabDaoHibernate() {
		super(LikeLab.class);
	}

}
