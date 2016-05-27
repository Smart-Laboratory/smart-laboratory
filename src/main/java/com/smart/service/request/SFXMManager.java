package com.smart.service.request;

import java.util.List;

import com.smart.model.request.SFXM;
import com.smart.service.GenericManager;

public interface SFXMManager extends GenericManager<SFXM, Long> {

	int getSFXMCount(String search, String hospitalId);

	List<SFXM> getPageList(String search, String hospitalId, int start, int end);

	List<SFXM> searchSFXM(String query, Long hospitalid);

}
