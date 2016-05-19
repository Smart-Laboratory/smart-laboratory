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
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.SectionManager;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("/set/section*")
public class SectionController {
	
	@Autowired
	private SectionManager sectionManager = null;
	
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
		if(query != null){
			query = new String(query.getBytes("ISO-8859-1"),"UTF-8");
		}
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int start = row * (page - 1);
		int end = row * page;
		System.out.println("query--->"+query);
		User user = userManager.getUserByUsername(request.getRemoteUser());

		DataResponse dataResponse = new DataResponse();

		List<Section> list = new ArrayList<Section>();

		int size = sectionManager.getSectionCount(query,hospitalId);

		System.out.println("size===>"+size);

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
			map.put("hospitalId", info.getHospitalId());
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
	public ModelAndView editSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		section.setCode(code);
		section.setName(name);
		sectionManager.save(section);
		return new ModelAndView("redirect:/set/section");
	}
    
    @RequestMapping(method = RequestMethod.POST,value="/delete")
    public ModelAndView deleteSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	sectionManager.remove(Long.parseLong(request.getParameter("id")));
    	return new ModelAndView("redirect:/set/section");
    }

	/**
	 * 批量删除
	 * @param ids
	 * @param request
	 * @param response
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/bathremove")
	public ModelAndView bathRemove(@RequestParam(value = "ids[]") long[] ids,HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map map=new HashMap();
		if(sectionManager.batchRemove(ids)){
			map.put("result", "success");
		}else{
			map.put("result", "error");
		}
		return new ModelAndView(new MappingJacksonJsonView(),map);
	}
}
