package com.smart.dao.request;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.request.SFXM;

public interface SFXMDao extends GenericDao<SFXM, Long> {

	@Transactional
	int getSFXMCount(String search, String hospitalId);

	@Transactional
	List<SFXM> getPageLIst(String search, String hospitalId, int start, int end);

	@Transactional
	List<SFXM> searchSFXM(String query, Long hospitalid);

}
