package com.smart.dao.request;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.request.SFXM;

public interface SFXMDao extends GenericDao<SFXM, Long> {

	int getSFXMCount(String search, String hospitalId);

	List<SFXM> getPageLIst(String search, String hospitalId, int start, int end);

	List<SFXM> searchSFXM(String query, Long hospitalid);

}
