package com.smart.service.impl.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.request.SFXMDao;
import com.smart.model.request.SFXM;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.request.SFXMManager;

@Service("sfxmManager")
public class SFXMManagerImpl extends GenericManagerImpl<SFXM, Long> implements SFXMManager {

	@SuppressWarnings("unused")
	private SFXMDao sfxmDao;

	@Autowired
	public void setSfxmDao(SFXMDao sfxmDao) {
		this.dao = sfxmDao;
		this.sfxmDao = sfxmDao;
	}
	
	
}
