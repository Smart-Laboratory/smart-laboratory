package com.smart.service.dsf;

import com.smart.model.dsf.DSF_ControlTestItems;
import com.smart.model.dsf.DSF_TestItems;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zjn on 2016/8/20.
 */
public interface DSF_ControlTestItemsManager extends GenericManager<DSF_ControlTestItems, Long> {

    int getSizeByCustomerid(String customerid, String sidx);

    List<DSF_TestItems> getYlxhByCustomerid(String query, String customerid, int start, int end, String sidx, String sord);

    void saveAll(List<DSF_ControlTestItems> ctiList );

}
