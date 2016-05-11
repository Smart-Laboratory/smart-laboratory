package com.smart.webapp.controller.quality;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.Constants;
import com.smart.model.lis.InvalidSample;
import com.smart.model.lis.Sample;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.InvalidSampleManager;
import com.smart.service.lis.SampleManager;

@Controller
@RequestMapping("/quality/invalidSampleForm*")
public class InvalidSampleFormController {

	@Autowired
	private InvalidSampleManager invalidSampleManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private SampleManager sampleManager;
	
	private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 *  不合格标本编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public InvalidSample getInvalidSample(HttpServletRequest request, HttpServletResponse response){
		
		String[] reasonList = Constants.INVALIDSAMPLE_REASON;
		Map<String, String> reasonMap = new LinkedHashMap<String,String>();
		for(int i=1;i< reasonList.length;i++){
			reasonMap.put(String.valueOf(i), reasonList[i]);
		}
		request.setAttribute("rejectReason", reasonMap);
		request.setAttribute("measureTaken", Constants.INVALIDSAMPLE_MEASURETAKEN);
		String msg = null;
		InvalidSample invalidSample = new InvalidSample();
		String id = request.getParameter("id");
		if(id!=null && !id.isEmpty()){
			invalidSample = invalidSampleManager.getByEzh(Long.parseLong(id));
			if(invalidSample == null){
				invalidSample = new InvalidSample();
				if(sampleManager.exists(Long.parseLong(id))){
					Sample sample = sampleManager.get(Long.parseLong(id));
					invalidSample.setSampleId(sample.getId());
					invalidSample.setPatientName(sample.getPatientname());
					invalidSample.setSex(sample.getSex());
					invalidSample.setAge(Integer.parseInt(sample.getAge()));
					invalidSample.setSampleType(sample.getSampleType());
				} else
					msg="该医嘱号不存在";
				invalidSample.setRejectTime(new Date());
			
			}
			if(sampleManager.exists(Long.parseLong(id))){
				Sample sample = sampleManager.get(Long.parseLong(id));
				request.setAttribute("hosSection",sample.getHosSection() );
				request.setAttribute("sampleNo",sample.getSampleNo() );
				request.setAttribute("inspectionName",sample.getInspectionName() );
				request.setAttribute("description",sample.getDescription() );
			} else
				msg="该医嘱号不存在";
			
			request.setAttribute("rejectTime",ymd.format(invalidSample.getRejectTime()) );
			request.setAttribute("msg", msg);
//			System.out.println("end:"+invalidSample.getRejectTime());
		}
		return invalidSample;
	}
	
	/*@ModelAttribute("invalidSample")
    protected InvalidSample loadIvalidSample(final HttpServletRequest request) {
        final String sampleId = request.getParameter("id");
        System.out.println(sampleId);
        if (request.getMethod().equalsIgnoreCase("post") && StringUtils.isNotBlank(sampleId)) {
        	InvalidSample sample = invalidSampleManager.get(Long.parseLong(sampleId));
        	System.out.println("数据库"+sampleId);
            return sample;
        }
        return new InvalidSample();
    }*/
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveInvalidSample( InvalidSample invalidSample,BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		
		invalidSample.setRejectPerson(user.getName());
		invalidSample.setRejectTime(new Date());
		
		if(invalidSample.getSampleId()!=null){
			invalidSampleManager.save(invalidSample);
		}
		else{
			request.setAttribute("msg", "1");
		}
		
		return "redirect:/quality/invalidSamples";
	}
	
	
	
}
