package com.smart.webapp.controller.lis.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/audit*")
public class SampleController {
	
	@Autowired
	private SampleManager sampleManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private SectionManager sectionManager = null;
	
	/**
	 * 根据条件查询该检验人员的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String text = request.getParameter("text");
		String sample = request.getParameter("sample");
		String lab = request.getParameter("lab");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
		int mark = 0;
		int status = -3;
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		if (!StringUtils.isEmpty(request.getParameter("mark"))) {
			mark = Integer.parseInt(request.getParameter("mark"));
		}

		if (!StringUtils.isEmpty(request.getParameter("status"))) {
			status = Integer.parseInt(request.getParameter("status"));
		}

		if (!StringUtils.isEmpty(sample)) {
			text = sample;
		} else {
			text = Constants.DF3.format(new Date());
		}

		DataResponse dataResponse = new DataResponse();
		
		List<Sample> list = new ArrayList<Sample>();
		if (status < 1)
			mark = 0;

		text = text.toUpperCase();
		String labCode = sectionManager.getByCode(lab).getSegment();
		int size = sampleManager.getSampleCount(text, lab, mark, status, labCode);
		list = sampleManager.getSampleList(text, lab, mark, status, labCode, start, end);
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		for(Sample info :list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", info.getId());
			map.put("mark", info.getAuditMarkValue());
			map.put("sample", info.getSampleNo());
			map.put("status", info.getAuditStatusValue());
			map.put("flag", info.getModifyFlag());
			map.put("size", 0);
			if (info.getSampleStatus()>=5) {
				if (info.getIswriteback() == 1) {
					map.put("lisPass", "<font color='red'>" + Constants.TRUE + "</font>");
				} else {
					map.put("lisPass", Constants.TRUE);
				}
			} else {
				map.put("lisPass", "");
			}
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
}
