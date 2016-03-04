package com.smart.webapp.controller.quality;

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
import com.smart.model.lis.Patient;
import com.smart.model.lis.Sample;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.InvalidSampleManager;
import com.smart.service.lis.PatientManager;
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
	@Autowired
	private PatientManager patientManager;
	
	
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
		
		String id = request.getParameter("id");
		InvalidSample invalidSample = new InvalidSample();
		
		if(id!=null && !id.isEmpty()){
			if(sampleManager.exists(Long.parseLong(id))){
				Sample sample = sampleManager.get(Long.parseLong(id));
				Patient patient = patientManager.getByBlh(sample.getPatientblh());
				invalidSample.setSampleId(sample.getId());
				invalidSample.setPatientName(patient.getPatientName());
				invalidSample.setSex(patient.getSex());
				invalidSample.setAge(patient.getAge());
				invalidSample.setSampleType(sample.getSampleType());
			} else
				msg="该医嘱号不存在";
		}
		
		
		invalidSample.setRejectTime(new Date());
		request.setAttribute("msg", msg);
		
		return invalidSample;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveInvalidSample(InvalidSample invalidSample,BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
