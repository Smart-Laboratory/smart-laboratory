package com.smart.webapp.controller.manage;

import java.net.InetAddress;		
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.Process;
import com.smart.model.lis.ProcessLog;
import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Sample;
import com.smart.model.lis.SampleLog;
import com.smart.model.lis.TestResult;
import com.smart.model.lis.TestResultLog;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.ProcessLogManager;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SampleLogManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.TestResultLogManager;
import com.smart.service.lis.TestResultManager;
import com.smart.webapp.util.DataResponse;
import com.smart.webapp.util.SampleUtil;
import com.smart.webapp.util.SectionUtil;
import com.smart.webapp.util.UserUtil;
import com.zju.api.service.RMIService;

@Controller
@RequestMapping("/manage/modify*")
public class ModifyController {

	@Autowired
	private UserManager userManager = null;

	@Autowired
	private SampleManager sampleManager = null;

	@Autowired
	private ProcessManager processManager = null;
	
	@Autowired
	private SampleLogManager sampleLogManager = null;
	
	@Autowired
	private ProcessLogManager processLogManager = null;
	
	@Autowired
	private TestResultManager testResultManager = null;
	
	@Autowired
	private TestResultLogManager testResultLogManager = null;
	
	@Autowired
	private ReceivePointManager receivePointManager = null;
	
	@Autowired
	private DictionaryManager dictionaryManager = null;
	
	@Autowired
	private RMIService rmiService = null;

	@Autowired
	private SectionManager sectionManager = null;
	
	private Map<String, String> pointMap = new HashMap<String, String>();

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request)
			throws Exception {
		User user = userManager.getUserByUsername(request.getRemoteUser());
		List<ReceivePoint> pointList = receivePointManager.getByType(0);
		for (ReceivePoint rp : pointList) {
			pointMap.put(rp.getCode(), rp.getLab());
		}
		ModelAndView view = new ModelAndView();
		view.addObject("name", user.getName());
		view.addObject("pointList", pointList);
		return view;
	}
	
	@RequestMapping(value = "/getLog*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getOldData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String text = request.getParameter("text");
		String type = request.getParameter("type");
		Sample sample = new Sample();
		if(Integer.parseInt(type) == 0) {
			sample = sampleManager.get(Long.parseLong(text));
		} else {
			sample = sampleManager.getBySampleNo(text);
		}
		Process process = processManager.getBySampleId(sample.getId());
		System.out.println(sample.getId());
		List<SampleLog> sampleLogList = sampleLogManager.getBySampleId(sample.getId());
		List<ProcessLog> processLogList = processLogManager.getBySampleId(sample.getId());
		Map<Long, ProcessLog> processLogMap  = new HashMap<Long, ProcessLog>();
		for(ProcessLog pl : processLogList) {
			processLogMap.put(pl.getSampleLogId(), pl);
		}
		DataResponse dataResponse = new DataResponse();
		if(sampleLogList == null || sampleLogList.size() == 0) {
			return null;
		}
		int size  = sampleLogList.size() + 1;
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(size);
		for(int i = 0; i <= sampleLogList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			SampleLog sl = new SampleLog();
			ProcessLog pl = new ProcessLog();
			if(i == 0) {
				sl.setSampleEntity(sample);
				pl.setProcessEntity(process);
				map.put("logtime", "当前记录");
				map.put("logip", "");
				map.put("logger", "");
				map.put("logoperate", "");
			} else {
				sl = sampleLogList.get(i-1);
				pl = processLogMap.get(sl.getId());
				map.put("logtime", Constants.SDF.format(sl.getLogtime()));
				map.put("logip", sl.getLogip());
				map.put("logger", sl.getLogger());
				map.put("logoperate", sl.getLogoperate());
			}
			map.put("id", sl.getId() == null ? "" : sl.getId());
			map.put("sampleid", sl.getSampleId());
			map.put("sampleno", sl.getSampleNo());
			map.put("shm", sl.getStayHospitalModelValue());
			map.put("pname", sl.getPatientname());
			map.put("pid", sl.getPatientId());
			map.put("sex", sl.getSexValue());
			map.put("age", sl.getAge() + sl.getAgeunit());
			map.put("bed", sl.getDepartBed());
			map.put("exam", sl.getInspectionName());
			map.put("sampletype", SampleUtil.getInstance(dictionaryManager).getValue(sl.getSampleType()));
			map.put("fee", sl.getFee());
			map.put("diag", sl.getDiagnostic());
			map.put("section", SectionUtil.getInstance(rmiService, sectionManager).getValue(sl.getHosSection()));
			map.put("lab", SectionUtil.getInstance(rmiService, sectionManager).getLabValue(sl.getSectionId()));
			map.put("requester", pl.getRequester());
			map.put("receiver", pl.getReceiver());
			map.put("receivetime", pl.getReceivetime());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/ajax/recoverLog*", method = RequestMethod.POST)
	@ResponseBody
	public boolean recoverLog(HttpServletRequest request, HttpServletResponse response) {
		try {
			Long logid = Long.parseLong(request.getParameter("id"));
			SampleLog slog = sampleLogManager.get(logid);
			ProcessLog plog = processLogManager.getBySampleLogId(logid);
			
			saveSampleLog(sampleManager.get(slog.getSampleId()), UserUtil.getInstance(userManager).getValue(request.getRemoteUser()), Constants.LOG_OPERATE_RECOVER);
			
			sampleManager.save(slog.getSampleEntity());
			processManager.save(plog.getProcessEntity());
		} catch (Exception e) {
			return false;
		}
		return true;
	}	
	
	/**
	 * 张晋南2016-5-31 testSection 检验段 sampleNumber 需要修改的编号，可多输入 operation 修改的操作类型
	 * operationValue 修改操作的值
	 * 
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax/sample*", method = RequestMethod.POST)
	@ResponseBody
	public String getModifyTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String error = "error";
		String search_date = request.getParameter("search_date");
		String testSection = request.getParameter("testSection");
		String sampleNumber = request.getParameter("sampleNumber");
		String operation = request.getParameter("operation");
		String operationValue = request.getParameter("operationValue");
		String username = UserUtil.getInstance(userManager).getValue(request.getRemoteUser());
		// 1样本信息，0结果信息
		String modifyResult = request.getParameter("modifyResult");
		int switchValue = 0;
		// 加
		if ("add".equals(operation)) {
			switchValue = 1;
		}
		// 减
		if ("reduce".equals(operation)) {
			switchValue = 2;
		}
		// 倒置
		if ("inversion".equals(operation)) {
			switchValue = 3;
		}
		// 替换
		if ("replace".equals(operation)) {
			switchValue = 4;
		}
		//
		if (!"inversion".equals(operation)) {
			if (0 == stringTOint(operationValue)) {
				return error;
			}
		}

		// 例：sampleNo = 20150601BAC
		String sampleNo = new StringBuffer().append(search_date)
				.append(testSection).toString().trim();
		// 获取页面设置的，需要修改的sampleNo
		List<Sample> sampleList = new ArrayList<Sample>();
		List<TestResult> trList = new ArrayList<TestResult>();

		Set<String> set = judgeSampleNumber(sampleNo, sampleNumber);
		if (null != set) {
			StringBuffer sNo = new StringBuffer();
			for (String value : set) {
				sNo.append("'");
				sNo.append(value);
				sNo.append("',");
			}
			sampleList = sampleManager.getBysampleNos(sNo.toString().substring(
					0, sNo.toString().length() - 1));
			trList = testResultManager.getTestBySampleNos(sNo.toString()
					.substring(0, sNo.toString().length() - 1));
		} else {
			return error;
		}
		// 加减后新的sampleNo的集合，用户判断新的集合中是否在数据库中存在
		List<String> resultNewNoList = new ArrayList<String>();
		switch (switchValue) {
		case 0: // 操作错误
			return error;
		case 1: // 加
			if ("1".equals(modifyResult)) {
				// 标本号信息修改
				if (null != sampleList && sampleList.size() > 0) {
					// 批量更新需要的MAP
					Map<String, String> trMap = new HashMap<String, String>();
					for (int i = 0; i < sampleList.size(); i++) {
						String sNo = updateSampleNo(sampleList.get(i)
								.getSampleNo(), operationValue, switchValue);
						if (null != sNo) {
							trMap.put(sampleList.get(i).getSampleNo(), sNo);
							resultNewNoList.add(sNo);
						} else {
							return error;
						}
					}
					// 判断新的编号集合数据库中是否存在
					StringBuffer sampleNewNo = new StringBuffer();
					for (String value : resultNewNoList) {
						sampleNewNo.append("'");
						sampleNewNo.append(value);
						sampleNewNo.append("',");
					}
					List<Sample> sNewList = sampleManager
							.getBysampleNos(sampleNewNo.toString().substring(0,
									sampleNewNo.toString().length() - 1));
					if (null != sNewList && sNewList.size() < 1) {
						for (int i = 0; i < sampleList.size(); i++) {
							//插入日志
							saveSampleLog(sampleList.get(i), username, Constants.LOG_OPERATE_INCREASE);
							
							String sNo = updateSampleNo(sampleList.get(i)
									.getSampleNo(), operationValue, switchValue);
							sampleList.get(i).setSampleNo(sNo);
						}
						sampleManager.saveAll(sampleList);
						
					} else {
						return error;
					}
				} else {
					return error;
				}
			} else {// 标本号结果修改
				if (null != trList && trList.size() > 0) {
					// 批量更新需要的MAP
					Map<String, String> trMap = new HashMap<String, String>();
					for (int i = 0; i < trList.size(); i++) {
						//插入日志
						saveTestResultLog(trList.get(i), username, Constants.LOG_OPERATE_INCREASE);
						
						String sNo = updateSampleNo(
								trList.get(i).getSampleNo(), operationValue,
								switchValue);
						if (null != sNo) {
							trMap.put(trList.get(i).getSampleNo(), sNo);
							resultNewNoList.add(sNo);
						} else {
							return error;
						}
					}
					// 判断新的编号集合数据库中是否存在
					StringBuffer trNewNo = new StringBuffer();
					for (String value : resultNewNoList) {
						trNewNo.append("'");
						trNewNo.append(value);
						trNewNo.append("',");
					}
					List<TestResult> trNewList = testResultManager
							.getTestBySampleNos(trNewNo.toString().substring(0,
									trNewNo.toString().length() - 1));
					if (null != trNewList && trNewList.size() < 1) {
						testResultManager.updateAll(trMap);
					} else {
						return error;
					}
				} else {
					return error;
				}
			}

			break;
		case 2: // 减
			if ("1".equals(modifyResult)) {
				// 标本号信息修改
				if (null != sampleList && sampleList.size() > 0) {
					// 批量更新需要的MAP
					Map<String, String> trMap = new HashMap<String, String>();

					for (int i = 0; i < sampleList.size(); i++) {
						String sNo = updateSampleNo(sampleList.get(i)
								.getSampleNo(), operationValue, switchValue);
						if (null != sNo) {
							trMap.put(sampleList.get(i).getSampleNo(), sNo);
							resultNewNoList.add(sNo);
						} else {
							return error;
						}
					}
					StringBuffer sampleNewNo = new StringBuffer();
					for (String value : resultNewNoList) {
						sampleNewNo.append("'");
						sampleNewNo.append(value);
						sampleNewNo.append("',");
					}
					List<Sample> sNewList = sampleManager
							.getBysampleNos(sampleNewNo.toString().substring(0,
									sampleNewNo.toString().length() - 1));
					if (null != sNewList && sNewList.size() < 1) {
						for (int i = 0; i < sampleList.size(); i++) {
							//插入日志
							saveSampleLog(sampleList.get(i), username, Constants.LOG_OPERATE_REDUCE);
							
							String sNo = updateSampleNo(sampleList.get(i)
									.getSampleNo(), operationValue, switchValue);
							sampleList.get(i).setSampleNo(sNo);
						}
						sampleManager.saveAll(sampleList);
					}
				} else {
					return error;
				}
			} else {
				if (null != trList && trList.size() > 0) {
					// 批量更新需要的MAP
					Map<String, String> trMap = new HashMap<String, String>();
					for (int i = 0; i < trList.size(); i++) {
						//插入日志
						saveTestResultLog(trList.get(i), username, Constants.LOG_OPERATE_REDUCE);
						
						String sNo = updateSampleNo(
								trList.get(i).getSampleNo(), operationValue,
								switchValue);
						if (null != sNo) {
							trMap.put(trList.get(i).getSampleNo(), sNo);
							resultNewNoList.add(sNo);
						} else {
							return error;
						}
					}
					StringBuffer trNewNo = new StringBuffer();
					for (String value : resultNewNoList) {
						trNewNo.append("'");
						trNewNo.append(value);
						trNewNo.append("',");
					}
					List<TestResult> trNewList = testResultManager
							.getTestBySampleNos(trNewNo.toString().substring(0,
									trNewNo.toString().length() - 1));
					if (null != trNewList && trNewList.size() < 1) {
						testResultManager.updateAll(trMap);
					} else {
						return error;
					}
				} else {
					return error;
				}
			}
			break;
		case 3: // 倒置
			// --------------------------------------------------------
			List<String> resultNoList = new ArrayList<String>();
			List<String> resultNoListSort = new ArrayList<String>();
			if (sampleNumber.indexOf(",") != -1) {
				return error;
			} else {
				// 如果是001-005格式的
				if (sampleNumber.indexOf("-") != -1) {
					String snums[] = sampleNumber.split("-");
					String snumBegin = snums[0];
					String snumEnd = snums[1];
					int sb = stringTOint(snumBegin);
					int se = stringTOint(snumEnd);
					// 如果有0代表有错误。return掉
					if (sb == 0 || se == 0) {
						return null;
					} else {
						// 001-002 后面的数字必须大于前面的数字
						if (sb < se && snumBegin.length() == snumEnd.length()) {
							for (int i = sb; i <= se; i++) {
								resultNoList.add(sampleNo + String.format("%" + snumBegin.length() + "d",i).replace(" ", "0"));
							}
						} else {
							return null;
						}
					}
				}
				resultNoListSort.addAll(resultNoList);
				Collections.reverse(resultNoListSort);
				Map<String, String> oldAndNew = new HashMap<String,String>();
				for(int i =0; i<resultNoList.size(); i ++){
					oldAndNew.put(resultNoList.get(i), resultNoListSort.get(i));
				}
				
				
				
				// 单个001-00的文件排序
				// 获取页面设置的，需要修改的sampleNo
				List<Sample> sampleList2 = new ArrayList<Sample>();
				List<TestResult> trList2 = new ArrayList<TestResult>();
				StringBuffer sNo = new StringBuffer();
				for (String value : resultNoList) {
					sNo.append("'");
					sNo.append(value);
					sNo.append("',");
				}
				if ("1".equals(modifyResult)) {// 修改样本信息表
					sampleList2 = sampleManager.getBysampleNos(sNo.toString()
							.substring(0, sNo.toString().length() - 1));
					
					List<Sample> saveList = new ArrayList<Sample>();
					for(Sample sample : sampleList2) {
						if(oldAndNew.containsKey(sample.getSampleNo())) {
							Sample s = sample;
							//插入日志
							saveSampleLog(s, username, Constants.LOG_OPERATE_INVERSION);
							s.setSampleNo(oldAndNew.get(sample.getSampleNo()));
							saveList.add(s);
						}
					}
					sampleManager.saveAll(saveList);
				}else{
					trList2 = testResultManager.getTestBySampleNos(sNo.toString()
							.substring(0, sNo.toString().length() - 1));
					
					List<TestResult> saveList = new ArrayList<TestResult>();
					for(TestResult tr : trList2) {
						if(oldAndNew.containsKey(tr.getSampleNo())) {
							TestResult t = tr;
							//插入日志
							saveTestResultLog(tr, username, Constants.LOG_OPERATE_INVERSION);
							t.setSampleNo(oldAndNew.get(tr.getSampleNo()));
							saveList.add(t);
						}
					}
					testResultManager.deleteAll(trList2);
					testResultManager.saveAll(saveList);
				}
				break;
			}
		case 4:// 替换
				// 参数中不能有","和"-"
			if (sampleNumber.indexOf(",") != -1
					|| operationValue.indexOf(",") != -1
					|| sampleNumber.indexOf("-") != -1
					|| operationValue.indexOf("-") != -1) {
				return error;
			} else {
				String sNoOld = sampleNo + sampleNumber;
				String sNoNew = sampleNo + operationValue;
				if (sNoOld.length() != sNoNew.length()) {
					return error;
				}
				Sample sample1 = sampleManager.getBySampleNo(sNoOld);
				Sample sample2 = sampleManager.getBySampleNo(sNoNew);
				
				if (null == sample2 && null != sample1) {
					//插入日志
					saveSampleLog(sample1, username, Constants.LOG_OPERATE_REPLACE);
					
					if ("1".equals(modifyResult)) {// 修改样本信息表
						sample1.setSampleNo(sNoNew);
						sampleManager.save(sample1);
					} else {// 修改结果表
						Map<String, String> trMap = new HashMap<String, String>();
						List  <TestResult>trOldList = testResultManager.getTestBySampleNo(sampleNo);
						if(null!=trOldList&&trOldList.size()>0){
							return error;
						}else{
							for(TestResult trOld:trOldList){
								//插入日志
								saveTestResultLog(trOld, username, Constants.LOG_OPERATE_REDUCE);
							}
							trMap.put(sNoOld, sNoNew);
							testResultManager.updateAll(trMap);
						}
					}
				} else {
					return error;
				}
			}
			break;
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print("success");
		return null;
	}

	/**
	 * 判断是不是整数
	 * 
	 * @param value
	 * @return
	 */
	private static int stringTOint(String value) {
		int sti = 0;
		try {
			sti = Integer.parseInt(value);
		} catch (Exception e) {
			sti = 0;
		}
		return sti;
	}

	/**
	 * 20160601ABC 对sampleNo进行加减，计算结果大于999或者小于1的返回NULL
	 * 
	 * @param sampleNo
	 * @param operationValue
	 * @return String
	 */
	private static String updateSampleNo(String sampleNo,
			String operationValue, int switchValue) {
		String subSampleNo = sampleNo.substring(11, sampleNo.length());
		int ssn = stringTOint(subSampleNo);
		int ov = stringTOint(operationValue);
		boolean flag = false;
		int result = 0;
		// 相加大于999错误
		if (1 == switchValue) {
			result = ssn + ov;
			if (result < 1000) {
				flag = true;
			}
		} else if (2 == switchValue) {
			// 相减小于1错误
			result = ssn - ov;
			if (result > 0) {
				flag = true;
			}
		}
		if (flag) {
			return sampleNo.substring(0, 11) + String.format(
					"%" + subSampleNo.length() + "d", result)
					.replace(" ", "0");
		} else {
			return null;
		}
	}

	/**
	 * 判断SampleNumber中格式，并把samplenumber放SET中 倒置操作除外
	 * 
	 * @param sampleNo
	 * @param sampleNumber
	 * @return Set
	 */
	private static Set<String> judgeSampleNumber(String sampleNo,
			String sampleNumber) {
		String sampleNoNew = "";
		Set<String> sampleNoSet = new HashSet<String>();
		// 如果有 ","号，说明有多个段
		if (sampleNumber.indexOf(",") != -1) {
			String sampleNums[] = sampleNumber.split(",");
			for (String sampleNum : sampleNums) {
				// 如果是001-005格式的
				if (sampleNum.indexOf("-") != -1) {
					String snums[] = sampleNum.split("-");
					String snumBegin = snums[0];
					String snumEnd = snums[1];
					int sb = stringTOint(snumBegin);
					int se = stringTOint(snumEnd);
					// 如果有0代表有错误。return掉
					if (sb == 0 || se == 0) {
						return null;
					} else {
						// 001-002 后面的数字必须大于前面的数字
						if (sb < se && snumBegin.length() == snumEnd.length()) {
							for (int i = sb; i <= se; i++) {
								// s.length表示补0后一共的长度，5表示当前的数字，即在5前面补零
								// String str =
								// String.format("%"+s.length()+"d",
								// 5).replace(" ", "0");
								sampleNoNew = sampleNo
										+ String.format(
												"%" + snumBegin.length() + "d",
												i).replace(" ", "0");
                                sampleNoSet.add(sampleNoNew);
							}
						} else {
							return null;
						}
					}
				} else {
					// 单个格式的001，有"，"分隔符
					int snum = stringTOint(sampleNum);
					if (snum != 0) {
						sampleNo = sampleNo + sampleNum;
						sampleNoSet.add(sampleNo);
					} else {
						return null;
					}
				}
			}
		} else {
			if (sampleNumber.indexOf("-") != -1) {
				// 单个格式001，有分隔符-
				String snums[] = sampleNumber.split("-");
				String snumBegin = snums[0];
				String snumEnd = snums[1];
				int sb = stringTOint(snumBegin);
				int se = stringTOint(snumEnd);
				// 如果有0代表有错误。return掉
				if (sb == 0 || se == 0) {
					return null;
				} else {
					// 001-002 后面的数字必须大于前面的数字
					if (sb < se && snumBegin.length() == snumEnd.length()) {
						for (int i = sb; i <= se; i++) {
							// s.length表示补0后一共的长度，5表示当前的数字，即在5前面补零
							// String str = String.format("%"+s.length()+"d",
							// 5).replace(" ", "0");
							sampleNoNew = sampleNo
									+ String.format(
											"%" + snumBegin.length() + "d", i)
											.replace(" ", "0");
                            sampleNoSet.add(sampleNoNew);
						}
					} else {
						return null;
					}
				}
			} else {
				int snum = stringTOint(sampleNumber);
				if (snum != 0) {
					sampleNo = sampleNo + sampleNumber;
					sampleNoSet.add(sampleNo);
				} else {
					return null;
				}
			}
		}
		return sampleNoSet;
	}
	
	private void saveSampleLog(Sample sample, String username,String operation) throws Exception{
		SampleLog slog = new SampleLog();
		slog.setSampleEntity(sample);
		slog.setLogger(username);
		slog.setLogip(InetAddress.getLocalHost().getHostAddress());
		slog.setLogoperate(operation);//Constants.LOG_OPERATE_ADD
		slog.setLogtime(new Date());
		slog = sampleLogManager.save(slog);
		
		Process process = processManager.getBySampleId(sample.getId());
		ProcessLog plog = new ProcessLog();
		plog.setSampleLogId(slog.getId());
		plog.setProcessEntity(process);
		plog.setLogger(username);
		plog.setLogip(InetAddress.getLocalHost().getHostAddress());
		plog.setLogoperate(operation);
		plog.setLogtime(new Date());
		processLogManager.save(plog);
	}
	
	private void saveTestResultLog(TestResult tr, String username,String operation) throws Exception{
		try {
			TestResultLog slog = new TestResultLog();
			slog.setTestResultEntity(tr);
			slog.setLogger(username);
			slog.setLogip(InetAddress.getLocalHost().getHostAddress());
			slog.setLogoperate(operation);
			slog.setLogtime(new Date());
			testResultLogManager.save(slog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}