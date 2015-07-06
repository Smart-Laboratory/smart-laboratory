package com.smart.service.impl.reagent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.reagent.ComboDao;
import com.smart.model.reagent.Combo;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.reagent.ComboManager;

@Service("comboManager")
public class ComboManagerImpl extends GenericManagerImpl<Combo, Long> implements ComboManager {
	
	private ComboDao comboDao;
	
    @Autowired
    public ComboManagerImpl(ComboDao comboDao) {
        this.dao = comboDao;
        this.comboDao = comboDao;
    }
}
