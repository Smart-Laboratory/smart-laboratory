package com.smart.webapp.controller.quality;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.smart.model.lis.InvalidSample;
import com.smart.model.lis.Sample;
import com.smart.service.lis.InvalidSampleManager;
import com.smart.service.lis.SampleManager;
import com.smart.util.PageList;


@Controller
@RequestMapping("/quality*")
public class InvalidSamplesController {

	@Autowired
	private InvalidSampleManager invalidSampleManager;
	/**
	 *  不合格标本列表、查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/invalidSamples*")
	public ModelAndView InvalidSamplesList(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		int pageNumber = 1;
		int totalNum = 0;
		
		String searchText = request.getParameter("text");
		
		PageList<InvalidSample> invalidSampleList = null;
		List<InvalidSample> invalidSamples = null;
		if(!StringUtils.isEmpty(searchText)){
			invalidSamples = invalidSampleManager.getByEzh(Long.parseLong(searchText));
		}
		else{
			invalidSamples = invalidSampleManager.getAll();
		}
		totalNum = invalidSamples.size();
		invalidSampleList = new PageList<InvalidSample>(pageNumber,totalNum);
		invalidSampleList.setList(invalidSamples);
		return new ModelAndView("quality/invalidSamples","invalidSamples",invalidSampleList);
	}
	
	/**
	 *  不合格标本查看页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET,value="/invalidSampleView*")
	public ModelAndView viewInvalidSample(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		String id = request.getParameter("id");
		
		InvalidSample invalidSample = new InvalidSample();
		Sample sample = new Sample();
		if(id!=null){
			invalidSample = invalidSampleManager.get(Long.parseLong(id));
			long sampleId = invalidSample.getSampleId();
			if(sampleId!=0)
				sample = sampleManager.get(sampleId);
		}
		
		return new ModelAndView("quality/invalidSampleView","invalidSample",invalidSample).addObject("sample", sample);
	}
	
	/**
	 *  删除不合格标本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET,value="/delete*")
	public ModelAndView deleteInvalidSample(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String id = request.getParameter("id");
		
		if(id!=null){
			invalidSampleManager.remove(Long.parseLong(id));
		}
		return new ModelAndView("/quality/invalidSamples");
	}
	
	@Autowired
	private SampleManager sampleManager;
}
