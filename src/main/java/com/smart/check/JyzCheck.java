package com.smart.check;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.zju.api.service.RMIService;
import com.smart.drools.R;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.webapp.util.FillFieldUtil;

public class JyzCheck implements Check {

	private final String LACK_CHECKER_ERROR = "缺少检验者！";
	
	public JyzCheck() {
	}
	
	public boolean doCheck(Sample info, List<TestResult> list) {
		
		boolean result = false;
		if(info.getChkoper2() != null && !info.getChkoper2().isEmpty()) {
			result = true;
		}
		/*String profileName = info.getSampleNo().substring(8, 11);
		String deviceId = null;
		
		for (TestResult tr : list) {
			deviceId = tr.getDeviceId();
			break;
		}
		
		String key = profileName + deviceId;
		if (profileJYZ.containsKey(key)) {
			info.setChkoper2(profileJYZ.get(key));
			result = true;
		} else {
			String jyz = FillFieldUtil.getJYZ(rmiService, profileName, deviceId);
			if (!StringUtils.isEmpty(jyz)) {
				info.setChkoper2(jyz);
				profileJYZ.put(key, jyz);
				result = true;
			}
		}*/
		
		if (!result) {
			// -----------------------------------
			info.setAuditStatus(UNPASS);
			info.setAuditMark(LACK_MARK);
			info.setNotes(LACK_CHECKER_ERROR);
			// -----------------------------------
		}
		
		return result;
	}

	public boolean doCheck(Sample info, R r, List<TestResult> list) {
		return false;
	}

}
