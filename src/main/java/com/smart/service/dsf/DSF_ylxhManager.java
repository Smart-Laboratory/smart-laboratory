package com.smart.service.dsf;

import com.smart.model.dsf.DSF_ylxh;
import com.smart.service.GenericManager;

import java.util.List;

/**
 * Created by zjn on 2016/8/15.
 */
public interface DSF_ylxhManager  extends GenericManager<DSF_ylxh, Long> {
    List<DSF_ylxh> getYlxh();

    List<DSF_ylxh> getTest(String lab);

    List<DSF_ylxh> getSearchData(String text);

    String getRelativeTest(String ylxh);

    List<DSF_ylxh> getLabofYlmcBylike(String lab ,String ylmc);

    int getSizeByLab(String lab, String sidx);

    List<DSF_ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord,String customerid);
}
