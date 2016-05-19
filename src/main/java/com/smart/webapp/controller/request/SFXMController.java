package com.smart.webapp.controller.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.request.SFXM;
import com.smart.service.UserManager;
import com.smart.service.request.SFXMManager;
import com.smart.webapp.util.DataResponse;

@Controller
@RequestMapping("/request/sfxm*")
public class SFXMController {

	@Autowired
	private UserManager userManager = null;
	
	@Autowired
	private SFXMManager sfxmManager = null;
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {
		return new ModelAndView();
    }
	
	@RequestMapping(value = "/data*",method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData( HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String hospitalId = request.getParameter("hospitalid");
		hospitalId = "1";
		String search = request.getParameter("search");
		int page = Integer.parseInt(pages);
		
		System.out.println("page===>"+page);
		
		int row = Integer.parseInt(rows);
		
		System.out.println("row===>"+row);
		
		int start = row * (page - 1);
		int end = row * page;

		DataResponse dataResponse = new DataResponse();

		List<SFXM> list = new ArrayList<SFXM>();

		int size = sfxmManager.getSFXMCount(search,hospitalId);

		System.out.println("size===>"+size);

		list = sfxmManager.getPageList(search,hospitalId,start,end);
		
		System.out.println("size===>"+list.size());

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		for(SFXM info :list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", info.getId());
			map.put("name", info.getName());
			map.put("english", info.getEnglish());
			map.put("pinyin", info.getPinyin());
			map.put("wubi", info.getWubi());
			map.put("price", info.getPrice());
			map.put("section", info.getSection());
			map.put("unit", info.getUnit());
			map.put("mzpb", info.getMzpb());
			map.put("zypb", info.getZypb());
			map.put("tjpb", info.getTjpb());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
}
