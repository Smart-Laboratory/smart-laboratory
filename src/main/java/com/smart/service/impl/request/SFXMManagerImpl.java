package com.smart.service.impl.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.request.SFXMDao;
import com.smart.model.request.SFXM;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.request.SFXMManager;

@Service("sfxmManager")
public class SFXMManagerImpl extends GenericManagerImpl<SFXM, Long> implements SFXMManager {

	private SFXMDao sfxmDao;

	@Autowired
	public void setSfxmDao(SFXMDao sfxmDao) {
		this.dao = sfxmDao;
		this.sfxmDao = sfxmDao;
	}

	public int getSFXMCount(String search, String hospitalId) {
		return sfxmDao.getSFXMCount(search, hospitalId);
	}

	public List<SFXM> getPageList(String search, String hospitalId, int start, int end) {
		return sfxmDao.getPageLIst(search, hospitalId, start, end);
	}

	public List<SFXM> searchSFXM(String query, Long hospitalid) {
		return sfxmDao.searchSFXM(query, hospitalid);
	}
}
