package com.smart.webapp.controller.manage;

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

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smart.Constants;
import com.smart.model.lis.ReceivePoint;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.ReceivePointManager;
import com.smart.service.lis.SampleManager;
import com.smart.service.lis.TestResultManager;

@Controller
@RequestMapping("/manage/modify*")
public class ModifyController {

	@Autowired
	private UserManager userManager = null;

	@Autowired
	private SampleManager sampleManager = null;
	
	@Autowired
	private TestResultManager testResultManager = null;

	@Autowired
	private ReceivePointManager receivePointManager = null;

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
	public String getModifyTest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String error = "error";
		String search_date = request.getParameter("search_date");
		String testSection = request.getParameter("testSection");
		String sampleNumber = request.getParameter("sampleNumber");
		String operation = request.getParameter("operation");
		String operationValue = request.getParameter("operationValue");
		//1样本信息，0结果信息
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
		//
		if (0 == stringTOint(operationValue)) {
			return error;
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
			trList = testResultManager.getTestBySampleNos(sNo.toString().substring(
					0, sNo.toString().length() - 1));
		} else {
			return error;
		}
		// 加减后新的sampleNo的集合，用户判断新的集合中是否在数据库中存在
		List<String> resultNewNoList = new ArrayList<String>();
		switch (switchValue) {
		case 0: // 操作错误
			return error;
		case 1: // 加
			if("1".equals(modifyResult)){
				//标本号信息修改
				if (null != sampleList&&sampleList.size()>0) {
					//批量更新需要的MAP
					Map <String, String>trMap =new HashMap<String, String>() ;
					for (int i = 0; i < sampleList.size(); i++) {
						String sNo = updateSampleNo(
								sampleList.get(i).getSampleNo(), operationValue,
								switchValue);
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
					if (null != sNewList && sNewList.size() < 1) 
					{
						for (int i = 0; i < sampleList.size(); i++) {
							String sNo = updateSampleNo(
									sampleList.get(i).getSampleNo(), operationValue,
									switchValue);
							sampleList.get(i).setSampleNo(sNo);
						}
						sampleManager.saveAll(sampleList);
					} else {
						return error;
					}
				}else{
					return error;
				}
			}else{//标本号结果修改
				if (null != trList&&trList.size()>0) {
					//批量更新需要的MAP
					Map <String, String>trMap =new HashMap<String, String>() ;
					for (int i = 0; i < trList.size(); i++) {
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
					if (null != trNewList && trNewList.size() <1) 
					{
						testResultManager.updateAll(trMap);
					} else {
						return error;
					}
				}else{
					return error;
				}
			}
			
			break;
		case 2: // 减
			if("1".equals(modifyResult)){
				//标本号信息修改
				if (null != sampleList&&sampleList.size()>0) {
					//批量更新需要的MAP
					Map <String, String>trMap =new HashMap<String, String>() ;
					
					for (int i = 0; i < sampleList.size(); i++) {
						String sNo = updateSampleNo(
								sampleList.get(i).getSampleNo(), operationValue,
								switchValue);
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
							String sNo = updateSampleNo(
									sampleList.get(i).getSampleNo(), operationValue,
									switchValue);
							sampleList.get(i).setSampleNo(sNo);
						}
							sampleManager.saveAll(sampleList);
						}
					} else {
						return error;
					}
			}else{
				if (null != trList&&trList.size()>0) {
					//批量更新需要的MAP
					Map <String, String>trMap =new HashMap<String, String>() ;
					for (int i = 0; i < trList.size(); i++) {
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
					if (null != trNewList && trNewList.size() <1 ) {
						testResultManager.updateAll(trMap);
					}else{
						return error;
					}
				}else{
					return error;
				}
			}
			break;
		case 3: // 倒置
			// --------------------------------------------------------
			List<String> sampleNoList = new ArrayList<String>();
			List<Sample> sampleNoListSort = new ArrayList<Sample>();
			if (sampleNumber.indexOf(",") != -1) {
				String sampleNums[] = sampleNumber.split(",");
				//批量更新需要的MAP
				Map <String, String>trMap =new HashMap<String, String>() ;
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
							return error;
						} else {
							// 001-002 后面的数字必须大于前面的数字
							if (sb < se
									&& snumBegin.length() == snumEnd.length()) {
								for (int i = sb; i <= se; i++) {
									sampleNoList.add(sampleNo+i);
								}
							} else {
								return error;
							}
						}
						// 有多个001-003，005-009的文件排序
						// 获取页面设置的，需要修改的sampleNo
						List<Sample> sampleList1 = new ArrayList<Sample>();
						if (null != sampleNoList) {
							StringBuffer sNo = new StringBuffer();
							for (String value : sampleNoList) {
								sNo.append("'");
								sNo.append(value);
								sNo.append("',");
							}
							sampleList1 = sampleManager.getBysampleNos(sNo
									.toString().substring(0,
											sNo.toString().length() - 1));
						} else {
							return error;
						}
						// 实现更新
						sampleNoListSort.addAll(sampleList1);
						Collections.reverse(sampleNoListSort);
						if (null != sampleList1) {
							for (int i = 0; i < sampleList1.size(); i++) {
								trMap.put(sampleList1.get(i).getSampleNo(), sampleNoListSort.get(i).toString());
								sampleList1.get(i).setSampleNo(
										sampleNoListSort.get(i).toString());
							}
						}
						
						if("1".equals(modifyResult)){
							sampleManager.saveAll(sampleList1);
						}else{
							testResultManager.updateAll(trMap);
						}
					} else {
						return error;
					}
				}
			} else {
				Map <String, String>trMap =new HashMap<String, String>() ;
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
								sampleNoList.add(sampleNo+i);
							}
						} else {
							return null;
						}
					}
				}
				// 单个001-00的文件排序
				// 获取页面设置的，需要修改的sampleNo
				List<Sample> sampleList2 = new ArrayList<Sample>();
				if (null != sampleNoList) {
					StringBuffer sNo = new StringBuffer();
					for (String value : sampleNoList) {
						sNo.append("'");
						sNo.append(value);
						sNo.append("',");
					}
					sampleList2 = sampleManager.getBysampleNos(sNo.toString()
							.substring(0, sNo.toString().length() - 1));
					// 实现更新
					sampleNoListSort.addAll(sampleList2);
					Collections.reverse(sampleNoListSort);
					if (null != sampleList2&&sampleList2.size()>0) {
						for (int i = 0; i < sampleList2.size(); i++) {
							trMap.put(sampleList2.get(i).getSampleNo(), sampleNoListSort.get(i).getSampleNo().toString());
						}
						if("1".equals(modifyResult)){
							for (int i = 0; i < sampleList2.size(); i++) {
								sampleList2.get(i).setSampleNo(trMap.get(sampleList2.get(i).getSampleNo().toString()));
							}
							sampleManager.saveAll(sampleList2);
						}else{
							testResultManager.updateAll(trMap);
						}
					} else {
						return error;
					}
				}
				// --------------------------------------------------------
				break;
			}
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
			return sampleNo.substring(0,11)+result;
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
								;
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
							;
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
}