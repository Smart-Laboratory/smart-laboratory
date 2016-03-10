package com.smart.webapp.controller.lis.audit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.service.UserManager;
import com.smart.service.lis.SOPIndexManager;
import com.smart.service.lis.TestResultManager;
import com.smart.model.lis.SOPIndex;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;

@Controller
@RequestMapping("/sop*")
public class SOPController {
	
	@Autowired
	private SOPIndexManager sopIndexManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private TestResultManager testResultManager;

	@RequestMapping(value = "/ajax/schedule*", method = { RequestMethod.GET })
	@ResponseBody
	public String getSopByLab(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String lab = request.getParameter("lab");
		String sampleno = request.getParameter("sampleno");
		List<SOPIndex> list = sopIndexManager.getByLab(lab);
		List<TestResult> testlist = testResultManager.getTestBySampleNo(sampleno);
		Set<String> testids = new HashSet<String>();
		Set<String> deviceids = new HashSet<String>();
		for(TestResult t : testlist) {
			testids.add(t.getTestId());
			deviceids.add(t.getDeviceId());
		}
		int g1 = 0;
		int g2 = 0;
		int g3 = 0;
		int g4 = 0;
		for(SOPIndex si : list) {
			switch (si.getType()) {
			case 1:
				g2++;
				break;
			case 2:
				if(deviceids.contains(si.getIndexid())) {
					g3++;
					break;
				}
			case 3:
				if(testids.contains(si.getIndexid())) {
					g4++;
					break;
				}
			default:
				g1++;
				break;
			}
		}
		JSONObject o = new JSONObject();
		o.put("g1", g1);
		o.put("g2", g2);
		o.put("g3", g3);
		o.put("g4", g4);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
	
	@RequestMapping(value = "/ajax/detail*", method = { RequestMethod.GET })
	@ResponseBody
	public String getDetailSop(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int type = Integer.parseInt(request.getParameter("type"));
		String sampleno = request.getParameter("sampleno");
		User operator = userManager.getUserByUsername(request.getRemoteUser());
		String lab = request.getParameter("lab");
		List<SOPIndex> list = sopIndexManager.getByType(lab, type);
		List<TestResult> testlist = testResultManager.getTestBySampleNo(sampleno);
		Set<String> testids = new HashSet<String>();
		Set<String> deviceids = new HashSet<String>();
		for(TestResult t : testlist) {
			testids.add(t.getTestId());
			deviceids.add(t.getDeviceId());
		}
		String html = "";
		Set<String> isused = new HashSet<String>(); 
		int count = 1;
		for(SOPIndex si : list) {
			if(!isused.contains(si.getSopid())) {
				if(si.getType() == 2) {
					if(deviceids.contains(si.getIndexid())) {
						isused.add(si.getSopid());
						html += "<a target='_blank' href='http://192.168.15.73/zhishi/doc/document/detail.html?"
								+ si.getSopid() + "-" + operator.getName() +"'>" + count + "、 " + si.getSop() + " " + si.getSopname() + "</a><br/>";
						count++;
					}
				} else if(si.getType() == 3) {
					if(testids.contains(si.getIndexid())) {
						isused.add(si.getSopid());
						html += "<a target='_blank' href='http://192.168.15.73/zhishi/doc/document/detail.html?"
								+ si.getSopid() + "-" + operator.getName() +"'>" + count + "、 " + si.getSop() + " " + si.getSopname() + "</a><br/>";
						count++;
					}
				} else {
					isused.add(si.getSopid());
					html += "<a target='_blank' href='http://192.168.15.73/zhishi/doc/document/detail.html?"
							+ si.getSopid() + "-" + operator.getName() +"'>" + count + "、 " + si.getSop() + " " + si.getSopname() + "</a><br/>";
					count++;
				}
			}
		}
		JSONObject o = new JSONObject();
		o.put("html", html);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(o.toString());
		return null;
	}
}
