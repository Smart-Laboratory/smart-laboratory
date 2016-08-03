package com.smart.webapp.controller.set;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.webapp.util.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.Section;
import com.smart.model.lis.SectionCode;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionCodeManager;
import com.smart.service.lis.SectionManager;
import com.smart.util.ConvertUtil;

@Controller
@RequestMapping("/set/section*")
public class SectionController {
	
	@Autowired
	private SectionManager sectionManager = null;
	
	@Autowired
	private SectionCodeManager sectionCodeManager = null;
	
	@Autowired
	private UserManager userManager = null;
	
	public SectionController(){
		
	}

	@RequestMapping(value = "/data*",method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getDataList( HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String hospitalId = request.getParameter("hospitalid");
		String query = request.getParameter("query");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;

		DataResponse dataResponse = new DataResponse();
		List<Section> list = new ArrayList<Section>();
		int size = sectionManager.getSectionCount(query,hospitalId);
		list =sectionManager.getSectionList(query,hospitalId,start,end,sidx,sord);

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		int x = size % (row == 0 ? size : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (size + x) / (row == 0 ? size : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		for(Section info :list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", info.getId());
			map.put("code", info.getCode());
			map.put("name", info.getName());
			map.put("segment", info.getSegment());
			map.put("hospitalId", info.getHospitalId());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	/**
     * 获取已选项目
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping( value = "/getCodeList*" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getIndexList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page  = ConvertUtil.getIntValue(request.getParameter("page"));
        int row = ConvertUtil.getIntValue(request.getParameter("rows"));
        String code = request.getParameter("code");

        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        if(code.isEmpty()) {
        	return null;
        }
        String codeId = spilt(code);
        List<SectionCode> list = sectionCodeManager.getCode(codeId, start, end);
        int size = list.size();

        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        for(SectionCode sc :list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", sc.getId());
            map.put("code", sc.getCode());
            map.put("describe", sc.getDescribe());
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query) throws Exception {
        List<Section> list = sectionManager.search(query);
        return new ModelAndView().addObject("list", list.size() > 0 ? list : null);
    }

	@RequestMapping(method = RequestMethod.POST,value="/edit")
	public void editSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Section section = new Section();
		if(id ==null || id.equals("0")) {
			User user = userManager.getUserByUsername(request.getRemoteUser());
			section.setHospitalId(user.getHospitalId());
		} else {
			section = sectionManager.get(Long.parseLong(id));
		}
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String segment = request.getParameter("segment");
		section.setCode(code);
		section.setName(name);
		section.setSegment(segment);
		sectionManager.save(section);
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/addCode")
	public void addSectionCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SectionCode sectionCode = new SectionCode();
		String code = request.getParameter("code");
		String describe = request.getParameter("describe");
		sectionCode.setCode(code);
		sectionCode.setDescribe(describe);
		sectionCodeManager.save(sectionCode);
	}
    
    @RequestMapping(method = RequestMethod.POST,value="/delete")
    public ModelAndView deleteSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	sectionManager.remove(Long.parseLong(request.getParameter("id")));
    	return new ModelAndView("redirect:/set/section");
    }

	/**
	 * 删除
	 * @param id
	 * @param request
	 * @param response
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/remove")
	public void Remove(HttpServletRequest request, HttpServletResponse response)throws Exception{
		try{
			Long id = ConvertUtil.getLongValue(request.getParameter("id"));
			sectionManager.remove(id);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
     * 分割字符加引号，用于SQL查询
     * @param str
     * @return
     */
    private String spilt(String str) {
        StringBuffer sb = new StringBuffer();
        String[] temp = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            if (!"".equals(temp[i]) && temp[i] != null)
                sb.append("'" + temp[i] + "',");
        }
        String result = sb.toString();
        String tp = result.substring(result.length() - 1, result.length());
        if (",".equals(tp))
            return result.substring(0, result.length() - 1);
        else
            return result;
    }
}
