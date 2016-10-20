package com.smart.webapp.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smart.model.lis.TestReference;
import com.smart.model.rule.Index;
import com.smart.service.lis.TestReferenceManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.smart.model.lis.TestResult;
import com.zju.api.model.Describe;
import com.zju.api.model.Reference;
import com.zju.api.service.RMIService;


public class FillFieldUtil {

	private Map<String, Index> indexMap = null;
    private Map<String, List<TestReference>> testReferenceMap = null;
	private static FillFieldUtil util = null;

	private FillFieldUtil(IndexManager indexManager, TestReferenceManager testReferenceManager) {
		if(indexMap == null) {
			indexMap = TestIdMapUtil.getInstance(indexManager).getIdMap();
		}
		if(testReferenceMap == null) {
			testReferenceMap = new HashMap<String, List<TestReference>>();
			for(TestReference testReference : testReferenceManager.getAll()) {
				if(testReferenceMap.containsKey(testReference.getTestId())) {
					testReferenceMap.get(testReference.getTestId()).add(testReference);
				} else {
					List<TestReference> list = new ArrayList<TestReference>();
					list.add(testReference);
					testReferenceMap.put(testReference.getTestId(), list);
				}
			}
		}
	}

	public synchronized static FillFieldUtil getInstance(IndexManager indexManager, TestReferenceManager testReferenceManager) {
		if (util == null) {
			util = new FillFieldUtil(indexManager, testReferenceManager);
		}
		return util;
	}

	public TestResult fillResult(TestResult result, int cycle, double age, int sex) {

		// 完善字段数据
		int li_direct = fillReference(result, age, cycle, sex);
		filleResultFlag(result, li_direct);
		return result;
	}

	public void fillReference(String testid,JSONObject obj) throws JSONException{
		if (testReferenceMap.containsKey(testid)) {
			List<TestReference> referList = testReferenceMap.get(testid);
            TestReference reference = referList.get(0);
			obj.put("reference", reference.getReference());
		}
	}

	private int fillReference(TestResult result, double age, int cycle, int sex) {

		int direct = 0;
		String testid = result.getTestId();
		String value = result.getTestResult();
		if (testReferenceMap.containsKey(testid)) {
			List<TestReference> referList = testReferenceMap.get(testid);
            Double ageLow = 0d;
            Double ageHigh = 0d;
            for(TestReference testReference : referList) {
                ageLow = new AgeUtil().getAge(String.valueOf(testReference.getAgeLow()), testReference.getAgeLowUnit());
                ageHigh = new AgeUtil().getAge(String.valueOf(testReference.getAgeHigh()), testReference.getAgeHighUnit());
                if((sex == testReference.getSex() || testReference.getSex() == 3) && testid.equals(testReference.getTestId())
                        && age >= ageLow && age < ageHigh && testReference.getReference() != null) {
                	if(testReference.getReference().indexOf("-") >= 0) {
						String[] refArr = testReference.getReference().split("-");
                        if(refArr.length == 2) {
                            result.setRefLo(refArr[0]);
                            result.setRefHi(refArr[1]);
                        } else if (refArr.length == 3) {
                            result.setRefLo("-" + refArr[1]);
                            result.setRefHi(refArr[2]);
                        } else {
                            result.setRefLo(testReference.getReference());
                            result.setRefHi("");
                        }
                    } else if (testReference.getReference().indexOf(">") == 0) {
                        result.setRefLo(testReference.getReference().substring(1));
                        result.setRefHi("");
                    } else if (testReference.getReference().indexOf("<") == 0) {
						int round = 0;
                    	if(testReference.getReference().substring(1).indexOf(".") > 0) {
                    		round = testReference.getReference().substring(1).split("[.]")[1].length();
						}
                        StringBuilder sb = new StringBuilder("#0.");
                        for(int i = 0; i < round; i++) {
                            sb.append("0");
                        }
                        DecimalFormat df  = new DecimalFormat(sb.toString());
                        result.setRefLo(df.format(0d));
                        result.setRefHi(testReference.getReference().substring(1));
                    } else {
                        result.setRefLo(testReference.getReference());
                        result.setRefHi("");
                    }
                }
            }
			String reflo = result.getRefLo();
			String refhi = result.getRefHi();
			try {
				if (value != null && value.length() > 0) {
					if (value.charAt(0) == '.') {
						value = "0" + value;
					}

					if (reflo != null && isDouble(reflo) && isDouble(refhi) && isDouble(value)) {
						if (reflo.contains(".") && reflo.split("[.]").length > 1) {
							int round = reflo.split("[.]")[1].length();
							StringBuilder sb = new StringBuilder("#0.");
							for (int i = 0; i < round; i++) {
								sb.append("0");
							}
							DecimalFormat df = new DecimalFormat(sb.toString());
							if(isDouble(value)) {
								value = df.format(Double.parseDouble(value));
							}
						} else {
							StringBuilder sb = new StringBuilder("#0");
							DecimalFormat df = new DecimalFormat(sb.toString());
							value = df.format(Double.parseDouble(value));
							if (value.equals("-0")) {
								value = "0";
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.setTestResult(value);
		if(result.getUnit() == null || result.getUnit().isEmpty()) {
			result.setUnit(ConvertUtil.null2String(indexMap.get(testid).getUnit()));
		}
		result.setSampleType(indexMap.get(testid).getSampleFrom());
		return direct;
	}

	private void filleResultFlag(TestResult result, int li_direct) {

		Index index = indexMap.get(result.getTestId());
		String ls_result = result.getTestResult();
		String ls_reflo = result.getRefLo();
		String ls_refhi = result.getRefHi();
		String resultFlag = result.getResultFlag();
		char[] flags;
		
		if (resultFlag != null && resultFlag.length() == 6) {
			flags = result.getResultFlag().toCharArray();
		} else {
			flags = new char[] { 'A', 'A', 'A', 'A', 'A', 'A' };
		}
		
		if (index != null) {
			result.setIsprint(index.getIsprint());
		} else {
			result.setIsprint(0);
		}
		
		if (ls_reflo == null || ls_reflo.trim().length() == 0) {
			ls_reflo = "";
			result.setRefLo(ls_reflo);
		}
		if (ls_refhi == null || ls_refhi.trim().length() == 0) {
			ls_refhi = "";
			result.setRefHi(ls_refhi);
		}
		if (ls_result == null) {
			ls_result = "";
		}
		if (ls_result.indexOf("<") == 0 || ls_result.indexOf(">") == 0) {
			ls_result = ls_result.substring(1);
		}
		
		if (isDouble(ls_result) && index != null && isDouble(ls_reflo)) {
			double ld_result = dbl(ls_result);
            if (ld_result < dbl(ls_reflo)) {
                flags[0] = 'C';
            } else if (ld_result > dbl(ls_refhi)) {
                flags[0] = 'B';
            } else {
                flags[0] = 'A';
            }
		} else {
			if((ls_reflo.indexOf("X10E") > 0 || ls_refhi.indexOf("X10E") > 0) && ls_result.indexOf("X10E") > 0) {
				double doubleResult = Double.parseDouble(ls_result.split("X")[0]) * Math.pow(10, Integer.parseInt(ls_result.split("E")[1]));
				double doubleRefLo = 0d;
				double doubleRefHi = 0d;
				if(ls_reflo.indexOf("X10E") < 0) {
					doubleRefLo = Double.parseDouble(ls_reflo);
				} else {
					doubleRefLo = Double.parseDouble(ls_reflo.split("X")[0]) * Math.pow(10, Integer.parseInt(ls_reflo.split("E")[1]));
				}
				if(ls_refhi.indexOf("X10E") < 0) {
					doubleRefHi = Double.parseDouble(ls_refhi);
				} else {
					doubleRefHi = Double.parseDouble(ls_refhi.split("X")[0]) * Math.pow(10, Integer.parseInt(ls_refhi.split("E")[1]));
				}

				if (doubleResult < doubleRefLo) {
					flags[0] = 'C';
				} else if (doubleResult > doubleRefHi) {
					flags[0] = 'B';
				} else {
					flags[0] = 'A';
				}
			}else {
				flags[1] = 'B';
				if (ls_result.indexOf("+") > -1 || ls_result.indexOf("阳") > -1) {
					flags[0] = 'B';
				} else if (ls_result.indexOf("-") > -1 || ls_result.indexOf("阴") > -1) {
					flags[0] = 'A';
				} else {
					flags[0] = 'B';
				}
			}
		}
		// 把flags写回resultFlag
		result.setResultFlag(String.valueOf(flags));
	}

	private double dbl(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Index getIndex(String testId) {
		if (indexMap != null) {
			if (indexMap.containsKey(testId)) {
				return indexMap.get(testId);
			} else {
				return null;
			}
		}
		return null;
	}
	
	public static String getJYZ(RMIService rmiService, String profileName, String deviceId) {
		
		List<String> jyzList = rmiService.getProfileJYZ(profileName, deviceId);
		//System.out.println(jyzList);
		if (jyzList != null && jyzList.size() > 0) {
			if (!StringUtils.isEmpty(jyzList.get(0))) {
				return jyzList.get(0);
			}
		}
		List<String> profileList = rmiService.getProfileJYZ(profileName, null);
		for (String jyz : profileList) {
			if (!StringUtils.isEmpty(jyz))
				return jyz;
		}
		return null;
	}

	public void updateTestReferenceMap(List<TestReference> list) {
		for(TestReference testReference : list) {
			if(testReferenceMap.containsKey(testReference.getTestId())) {
				testReferenceMap.get(testReference.getTestId()).add(testReference);
			} else {
				List<TestReference> references = new ArrayList<TestReference>();
				list.add(testReference);
				testReferenceMap.put(testReference.getTestId(), references);
			}
		}
	}

	public void removeFromTestReferenceMap(TestReference testReference) {
		testReferenceMap.get(testReference.getTestId()).remove(testReference);
	}
}

