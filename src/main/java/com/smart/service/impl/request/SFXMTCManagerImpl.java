package com.smart.service.impl.request;

import org.springframework.stereotype.Service;

import com.smart.dao.request.SFXMTCDao;
import com.smart.model.request.SFXMTC;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.request.SFXMTCManager;

@Service("sfxmtcManager")
public class SFXMTCManagerImpl extends GenericManagerImpl<SFXMTC, Long> implements SFXMTCManager {

	@SuppressWarnings("unused")
	private SFXMTCDao sfxmtcDao;

	public void setSfxmtcDao(SFXMTCDao sfxmtcDao) {
		this.dao = sfxmtcDao;
		this.sfxmtcDao = sfxmtcDao;
	}
	
	
}
