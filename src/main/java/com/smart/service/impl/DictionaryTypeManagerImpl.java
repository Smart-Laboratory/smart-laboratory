package com.smart.service.impl;

import com.smart.dao.DictionaryTypeDao;
import com.smart.model.DictionaryType;
import com.smart.service.UserExistsException;
import com.smart.service.DictionaryTypeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: DictionaryTypeManagerImpl
 * Description:
 *
 * @Author:zhou
 * @Date:2016/5/17 14:35
 * @Version:
 */
@Service(" dictionaryTypeManager")
public class DictionaryTypeManagerImpl extends GenericManagerImpl<DictionaryType, Long> implements DictionaryTypeManager  {
    private DictionaryTypeDao  dictionaryTypeDao;

    @Autowired
    public void setDictionaryDao(DictionaryTypeDao dictionaryTypeDao){
        this.dao = dictionaryTypeDao;
        this.dictionaryTypeDao = dictionaryTypeDao;
    }
}
