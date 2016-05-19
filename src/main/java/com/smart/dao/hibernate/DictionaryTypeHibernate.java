package com.smart.dao.hibernate;

import com.smart.dao.DictionaryTypeDao;
import com.smart.model.DictionaryType;
import org.springframework.stereotype.Repository;

/**
 * Title: DictionaryTypeHibernate
 * Description: 字典类别
 *
 * @Author:zhou
 * @Date:2016/5/17 11:09
 * @Version:
 */
@Repository("DictionaryTypeDao")
public class DictionaryTypeHibernate extends GenericDaoHibernate<DictionaryType, Long> implements DictionaryTypeDao {
    public DictionaryTypeHibernate(){
        super(DictionaryType.class);
    }

}
