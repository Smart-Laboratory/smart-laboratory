package com.smart.webapp.controller.qc;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.qc.QcBatchManager;
import com.smart.model.lis.Device;
import com.smart.model.pb.SxSchool;
import com.smart.model.qc.QcBatch;

@Controller
@RequestMapping("/qc/data*")
public class QcDataController {

	@Autowired
	private QcBatchManager qcBatchManager = null;
	
	@Autowired
	private DeviceManager deviceManager = null;
	
	@Autowired
	private UserManager userManager = null;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView view = new ModelAndView();
		List<Device> list = 
				deviceManager.getDeviceByLab(userManager.getUserByUsername(request.getRemoteUser()).getLastLab());
		view.addObject("today", Constants.DF2.format(new Date()));
		view.addObject("deviceList", list);
		return view;
	}
	
	@RequestMapping(value = "/ajax/getQcBatch*", method = RequestMethod.GET)
	@ResponseBody
	public String getSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String deviceid = request.getParameter("deviceid");
		List<QcBatch> list = qcBatchManager.getByDevice(deviceid);
		JSONObject o = new JSONObject();
		
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
	
}
