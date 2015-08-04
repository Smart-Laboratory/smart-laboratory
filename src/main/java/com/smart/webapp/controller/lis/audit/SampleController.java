package com.smart.webapp.controller.lis.audit;

import java.text.SimpleDateFormat;
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
import com.smart.service.lis.SampleManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/audit*")
public class SampleController {
	
	@Autowired
	private SampleManager sampleManager = null;
	
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
		int mark = 0;
		int status = -3;
		
		if (!StringUtils.isEmpty(request.getParameter("mark"))) {
			mark = Integer.parseInt(request.getParameter("mark"));
		}

		if (!StringUtils.isEmpty(request.getParameter("status"))) {
			status = Integer.parseInt(request.getParameter("status"));
		}

		if (!StringUtils.isEmpty(sample)) {
			text = sample;
		} else {
			text = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}

		DataResponse dataResponse = new DataResponse();
		List<Sample> list = new ArrayList<Sample>();
		if (status < 1)
			mark = 0;

		if (!StringUtils.isEmpty(text)) {
			text = text.toUpperCase();
			switch (text.length()) {
			case 3:
				if ("ALL".equals(text)) {
					list = sampleManager.getSampleList("", lab, "", mark, status);
				}
				break;
			case 8:
				if (StringUtils.isNumeric(text)) {
					list = sampleManager.getSampleList(text, lab, "", mark, status);
				}
				break;
			case 11:
				if (StringUtils.isNumeric(text.substring(0, 8))) {
					list = sampleManager.getSampleList(text.substring(0, 8), lab, text.substring(8),
							mark, status);
				}
				break;
			case 14:
				if (StringUtils.isNumeric(text.substring(0, 8)) && StringUtils.isNumeric(text.substring(11))) {
					list = sampleManager.getListBySampleNo(text);
				}
				break;
			case 18:
				if (text.indexOf('-') != 0 && StringUtils.isNumeric(text.substring(0, 8))
						&& StringUtils.isNumeric(text.substring(11, 14))
						&& StringUtils.isNumeric(text.substring(15, 18))) {
					List<Sample> result = sampleManager.getSampleList(text.substring(0, 8), lab,
							text.substring(8, 11), mark, status);

					list = new ArrayList<Sample>();
					int start = Integer.parseInt(text.substring(11, 14));
					int end = Integer.parseInt(text.substring(15, 18));
					// 过滤
					for (Sample s : result) {
						int index = Integer.parseInt(s.getSampleNo().substring(11));
						if (index >= start && index <= end) {
							list.add(s);
						}
					}
				}
				break;
			}
		}
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			Sample info = list.get(start + index);
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
			index++;
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
}
