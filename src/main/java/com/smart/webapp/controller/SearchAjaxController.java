package com.smart.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.model.Dictionary;
import com.smart.model.lis.*;
import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.model.request.SFXM;
import com.smart.model.rule.Bag;
import com.smart.model.rule.DesBag;
import com.smart.model.rule.Index;
import com.smart.webapp.controller.lis.audit.BaseAuditController;
import com.zju.api.model.Ksdm;

/**
 * Title: SearchController
 * Description:所有search AJAX
 *
 * @Author:yu
 * @Date:2016/6/21 10:07
 * @Version:
 */
@Controller
@RequestMapping("/ajax")
public class SearchAjaxController extends BaseAuditController {
    
    /**
     * 搜索仪器
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchDevice", method = { RequestMethod.GET })
    @ResponseBody
    public String searchDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        List<Device> deviceList =  deviceManager.getDeviceList(name);
        if(deviceList.size()>10)
            deviceList = deviceList.subList(0, 10);

        JSONArray array = new JSONArray();
        if (deviceList != null) {
            for (Device device : deviceList) {
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("id", device.getId());
                jsonObject.put("type", device.getType());
                jsonObject.put("name",device.getName());
                array.put(jsonObject);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }
    
    /**
     * 搜索检验段
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchCode", method = { RequestMethod.GET })
    @ResponseBody
    public String searchSectionCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        List<SectionCode> codeList =  sectionCodeManager.searchCode(name.toUpperCase());
        if(codeList.size()>5)
        	codeList = codeList.subList(0, 5);

        JSONArray array = new JSONArray();
        if (codeList != null) {
            for (SectionCode sc : codeList) {
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("id", sc.getId());
                jsonObject.put("code", sc.getCode());
                jsonObject.put("describe",sc.getDescribe());
                array.put(jsonObject);
            }
        }

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }

	/**
	 * 搜索样本类型
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchSampleType", method = { RequestMethod.GET })
	@ResponseBody
	public String searchSampleType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}

		List<Dictionary> codeList =  dictionaryManager.searchSampleType(name);
		if(codeList.size()>5)
			codeList = codeList.subList(0, 5);

		JSONArray array = new JSONArray();
		if (codeList != null) {
			for (Dictionary dictionary : codeList) {
				JSONObject  jsonObject = new JSONObject();
				jsonObject.put("id", dictionary.getId());
				jsonObject.put("sign", dictionary.getSign());
				jsonObject.put("value",dictionary.getValue());
				array.put(jsonObject);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

    /**
     * 搜索部门
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchLab", method = { RequestMethod.GET })
    @ResponseBody
    public String searchLab(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        List<Section> sectionList =  sectionManager.getSectionList(name);
        if(sectionList.size()>10)
            sectionList = sectionList.subList(0, 10);

        JSONArray array = new JSONArray();
        if (sectionList != null) {
            for (Section section : sectionList) {
                JSONObject  jsonObject = new JSONObject();
                jsonObject.put("id", section.getId());
                jsonObject.put("code",section.getCode());
                jsonObject.put("name",section.getName());
                array.put(jsonObject);
            }
        }
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(array.toString());
        return null;
    }
    
    @RequestMapping(value = "/searchBAMC", method = { RequestMethod.GET })
	@ResponseBody
	public String searchBAMC(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}

		List<CollectSample> csList = collectSampleManager.getCollectSampleByName(name);
		JSONArray array = new JSONArray();
		
		if (csList != null) {
			for (int i=0; i<csList.size(); i++) {
				CollectSample cs = csList.get(i);
				JSONObject o = new JSONObject();
				o.put("id", i);
				o.put("name", cs.getBamc());
				array.put(o);
			}
		}

		response.setContentType("charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
    
    @RequestMapping(value = "/searchSection*", method = { RequestMethod.GET })
	@ResponseBody
	public String searchSection(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Ksdm> list = rmiService.searchSection(name.toUpperCase());
		JSONArray array = new JSONArray();
		if (list != null) {
			for (Ksdm s : list) {
				JSONObject o = new JSONObject();
				o.put("id", s.getId());
				o.put("name", s.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
    
    @RequestMapping(value = "/searchContactInfo*", method = { RequestMethod.GET })
	@ResponseBody
	public String searchContactInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<ContactInfor> list = contactManager.searchContact(name.toUpperCase());
		JSONArray array = new JSONArray();
		if (list != null) {
			for (ContactInfor ci : list) {
				JSONObject o = new JSONObject();
				o.put("id", ci.getWORKID());
				o.put("name", ci.getNAME());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
    
    @RequestMapping(value = "/searchYlsf*", method = RequestMethod.GET)
	public String searchYlsf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String query = request.getParameter("query");
		long hospitalid = userManager.getUserByUsername(request.getRemoteUser()).getHospitalId();
		List<SFXM> sfxmList = sfxmManager.searchSFXM(query.toUpperCase(), hospitalid);
		JSONObject o = new JSONObject();
		List<String> list= new ArrayList<String>();
		for(SFXM sfxm : sfxmList) {
			list.add(sfxm.getId() + " " + sfxm.getName() + " " + sfxm.getYblx() +" " + sfxm.getPrice());
		}
		o.put("list", list);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
    
    @RequestMapping(value = "/searchDesBag", method = { RequestMethod.GET })
	@ResponseBody
	public String searchDesBag(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<DesBag> bags = new ArrayList<DesBag>();
		if(name.equals(" "))
			bags = desBagManager.getAll();
		else
			bags = desBagManager.getBag(name);
		JSONArray array = new JSONArray();
		if (bags != null) {
			for (DesBag b : bags) {
				JSONObject o = new JSONObject();
				o.put("id", b.getId());
				o.put("name", b.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
    
    @RequestMapping(value = "/searchTest", method = { RequestMethod.GET })
	@ResponseBody
	public String searchTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Index> desList =  indexManager.getIndexs(name);
		if(desList.size()>10)
			desList = desList.subList(0, 10);
		JSONArray array = new JSONArray();
		
		if (desList != null) {
			for (Index d : desList) {
				
				JSONObject o = new JSONObject();
				o.put("id", d.getIndexId());
				o.put("ab", d.getEnglish());
				o.put("name", d.getName());
				array.put(o);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	/**
	 * 抗生素
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/searchAntibiotic", method = { RequestMethod.GET })
	@ResponseBody
	public String searchAntibiotic(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Index> desList =  indexManager.getAntibioticList(name,0,0,"","");
		if(desList.size()>10)
			desList = desList.subList(0, 10);
		JSONArray array = new JSONArray();

		if (desList != null) {
			for (Index d : desList) {
				JSONObject o = new JSONObject();
				o.put("id", d.getId());
				o.put("indexid", d.getIndexId());
				o.put("ab", d.getEnglish());
				o.put("name", d.getName());
				array.put(o);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	/**
	 * 搜索微生物检验目的
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/searchMicroTest", method = { RequestMethod.GET })
	@ResponseBody
	public String searchMicroTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Ylxh> desList =  ylxhManager.getLabofYlmcBylike("1160703,3120401",name);
		if(desList.size()>10)
			desList = desList.subList(0, 10);
		JSONArray array = new JSONArray();

		if (desList != null) {
			for (Ylxh info : desList) {
				JSONObject o = new JSONObject();
				o.put("id",info.getYlxh());
				o.put("indexid", info.getYlmc());
				o.put("lab", info.getKsdm());
				array.put(o);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	@RequestMapping(value = "/searchBag", method = { RequestMethod.GET })
	@ResponseBody
	public String searchBag(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		List<Bag> bags = bagManager.getBagByName(name);
		JSONArray array = new JSONArray();
		if (bags != null) {
			for (Bag b : bags) {
				JSONObject o = new JSONObject();
				o.put("id", b.getId());
				o.put("name", b.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
}
