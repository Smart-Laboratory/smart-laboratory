package com.smart.webapp.controller.lis.audit;

import java.io.ByteArrayInputStream;	
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.smart.Constants;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.model.util.NeedWriteCount;
import com.smart.service.reagent.OutManager;
import com.smart.webapp.util.UserUtil;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/audit/ajax*")
public class AjaxController extends BaseAuditController {
	
	private static final Log log = LogFactory.getLog(AjaxController.class);
	
	@Autowired
	private OutManager outManager;
	
	@RequestMapping(value = "/singleChart*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSingleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String testid = request.getParameter("id");
		String sample = request.getParameter("sample");
		DecimalFormat deFormat = new DecimalFormat("#.##");
		
		Sample info = sampleManager.getBySampleNo(sample);
		Date measuretime = new Date();
		String patientid = info.getPatientblh();
		Set<String> sameTests = new HashSet<String>();
		sameTests.add(testid);
		String tests = sameTests.toString().replace("[", "'").replace("]", "'");
		tests = tests.replaceAll(", ", "','");
		List<TestResult> list = testResultManager.getSingleHistory(tests, patientid);
		List<Double> loArr = new ArrayList<Double>(); 
		List<Double> reArr = new ArrayList<Double>();
		List<Double> hiArr = new ArrayList<Double>();
		List<String> timeArr = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(TestResult t: testResultManager.getPrintTestBySampleNo(info.getSampleNo())) {
			if (testid.equals(t.getTestId())) {
				measuretime = t.getMeasureTime();
			}
		}
		List<Reagent> rlist  = reagentManager.getByTestId(testid);
		if(rlist.size()>0) {
			String rids = "";
			Map<Long, Reagent> rm = new HashMap<Long, Reagent>();
			for(Reagent r : rlist) {
				rids += r.getId() + ",";
				rm.put(r.getId(), r);
			}
			List<Out> out = outManager.getLastHMs(rids.substring(0, rids.length()-1), measuretime);
			List<String> html = new ArrayList<String>();
			for(int i=0; i<out.size(); i++) {
				Out o = out.get(i);
				StringBuilder s = new StringBuilder("");
				s.append("<p>");
				s.append((i+1) + ". ");
				s.append(rm.get(o.getRgId()).getName());
				s.append(" 批号:");
				s.append(" " + o.getBatch());
				s.append(" 出库日期:");
				s.append(" " + Constants.SDF.format(o.getOutdate()));
				s.append("</p>");
				html.add(s.toString());
			}
			map.put("hmList", html);
		} else {
			map.put("hmList", "");
		}
		
		if (idMap.size() == 0)
			initMap();
		
        if(list.size()>1) {
        	if(list.get(0).getUnit() != null && !list.get(0).getUnit().isEmpty() && idMap.containsKey(list.get(0).getTestId())) {
        		map.put("name", idMap.get(list.get(0).getTestId()).getName() + " (" + list.get(0).getUnit() + ")");
        	} else {
        		map.put("name", idMap.get(list.get(0).getTestId()).getName());
        	}
        	int num = list.size();
        	int count = 0;
        	Double average;
    		Double max = 0.0;
    		Double min = 100000.0;
    		Double total = 0.0;
    		Double sd;
    		List<Double> resultList = new ArrayList<Double>();
        	for (int i = 0; i < num; i++) {
        		if(StringUtils.isNumericSpace(list.get(i).getTestResult().replace(".", ""))) {
        			double d = Double.parseDouble(list.get(i).getTestResult());
					if(d > max){
						max = d;
					}
					if(d < min){
						min = d;
					}
					total = total + d;
					count = count +1;
					resultList.add(d);
        			loArr.add(Double.parseDouble(list.get(i).getRefLo()));
            		reArr.add(Double.parseDouble(list.get(i).getTestResult()));
            		hiArr.add(Double.parseDouble(list.get(i).getRefHi()));
            		timeArr.add(Constants.SDF.format(list.get(i).getMeasureTime()));
            	}
        	}
        	map.put("max", max);
        	map.put("min", min);
        	map.put("num", count);
        	if (resultList.size()%2 == 0) {
        		map.put("mid", resultList.get(resultList.size()/2-1));
			} else {
				map.put("mid", resultList.get(resultList.size()/2));
			}
        	
			average = (count == 0 ? 0 : total/count);
			map.put("ave", deFormat.format(average));
			Double variance = 0.0;
			for (Double d : resultList) {
				variance = variance + Math.pow(d-average, 2);
			}
			sd = Math.sqrt(variance/resultList.size());
			map.put("sd", deFormat.format(sd));
			map.put("cov", deFormat.format(sd*100/average));
        }
        Collections.reverse(timeArr);
		Collections.reverse(reArr);
		Collections.reverse(hiArr);
		Collections.reverse(loArr);
        map.put("lo", loArr);
        map.put("re", reArr);
        map.put("hi", hiArr);
        map.put("time", timeArr);
        return map;
	}
	
	@RequestMapping(value = "/relativeTest*", method = RequestMethod.GET)
	@ResponseBody
	public String getRelativeTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sample = request.getParameter("sample");
		String html = "";
		Sample info = sampleManager.getBySampleNo(sample);
		String history = ylxhManager.getRelativeTest(info.getYlxh());
		if(history == null || history.isEmpty()) {
			return "";
		}
		String[] tests = history.split(",");
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String s : tests) {
			map.put(s, 0);
		}
		history = history.substring(0, history.length()-1);
		history = "('" + history.replaceAll(",", "','") + "')";
		List<TestResult> hisTests = testResultManager.getRelative(info.getPatientId(), info.getPatientblh(), history);
		if(hisTests.size()>0) {
			if (idMap.size() == 0)
				initMap();
			Map<String, String> htmlMap = new HashMap<String, String>();
			html += "<table>";
			for(int i=0; i<hisTests.size(); i++) {
				TestResult tr = hisTests.get(i);
				if(map.get(tr.getTestId()) < 3) {
					if(htmlMap.containsKey(tr.getSampleNo())) {
						String s = htmlMap.get(tr.getSampleNo()) 
								+ "<td>" + idMap.get(tr.getTestId()).getName() + "</td>"
								+ "<td width='50px;'>" + tr.getTestResult() + "</td>";
						htmlMap.put(tr.getSampleNo(), s);
					} else {
						htmlMap.put(tr.getSampleNo(), 
								"<tr><td>" + tr.getSampleNo() + "</td>"
								+ "<td>" + idMap.get(tr.getTestId()).getName() + "</td>"
								+ "<td width='50px;'>" + tr.getTestResult() + "</td>");
					}
					map.put(tr.getTestId(), map.get(tr.getTestId()) + 1);
				}
			}
			for(String s : htmlMap.keySet()) {
				html += htmlMap.get(s) + "</tr>";
			}
			html += "</table>";
		}
		JSONObject obj = new JSONObject();
		obj.put("html", html);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
	
	
	
	
	/**
	 * 文件上传
	 * 张晋南 2016-5-30
	 * 修改电子签名图片
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.POST, value="/uploadElectronicSignatureImg")
	@ResponseBody
	public void uploadElectronicSignatureImgs(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		String username = multipartRequest.getRemoteUser();
		String realPath = multipartRequest.getServletContext().getRealPath("/");
	    String uploadFileUrl = realPath+"\\images\\bmp";
	    String uploadFileUrl_bak = "C:\\images\\bmp";
	    
	    int count = 0;
	    multipartRequest.setAttribute("addr", realPath);
        File dir = new File(uploadFileUrl);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println("创建图片目录");
		} else {
			System.out.println("图片目录存在");
			count = dir.listFiles().length;
		}
		 File backdir = new File(uploadFileUrl_bak);
			if (!backdir.exists()) {
				backdir.mkdirs();
				System.out.println("创建备份目录");
			} else {
				System.out.println("备份目录存在");
				count = backdir.listFiles().length;
			}
		//获取多个file
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = it.next();
			MultipartFile imgFile = multipartRequest.getFile(key);
			if (imgFile.getOriginalFilename().length() > 0) {
				String fileName = imgFile.getOriginalFilename();
				String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				String newFileName = username + suffix;
				try {
					saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, "", newFileName);
					saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl_bak, "", newFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				count++;
			}
		}
		
		 //返回结果  
		JSONObject obj = new JSONObject();
		String imgurl = "../images/bmp/"+username+".bmp";
		obj.put("imgurl", imgurl);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
	}
	
	/**
	 * 文件上传
	 * 张晋南 2016-5-15
	 * 修改上传目录，上传目录自动获取为真实路径
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.POST, value="/uploadFile")
	@ResponseBody
	public void uploadImg(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
        String sampleno = multipartRequest.getParameter("sampleno");
        String imgDescription = multipartRequest.getParameter("imgnote");
        //String uploadFileUrl = Constants.imageUrl + sampleno;
       
        int count = 0;

        String realPath = multipartRequest.getServletContext().getRealPath("/");
        
        String uploadFileUrl = realPath+"\\images\\upload\\"+sampleno;
        String uploadFileUrl_bak = "C:\\images\\upload\\" + sampleno;
        
//        realPath = realPath.substring(0, realPath.lastIndexOf("\\"));
        multipartRequest.setAttribute("addr", realPath);
        System.out.println(uploadFileUrl);
        File dir = new File(uploadFileUrl);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println("创建图片目录");
		} else {
			System.out.println("图片目录存在");
			count = dir.listFiles().length;
		}
		 File backdir = new File(uploadFileUrl_bak);
			if (!backdir.exists()) {
				backdir.mkdirs();
				System.out.println("创建备份目录");
			} else {
				System.out.println("备份目录存在");
				count = backdir.listFiles().length;
			}
		//获取多个file
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = it.next();
			MultipartFile imgFile = multipartRequest.getFile(key);
			if (imgFile.getOriginalFilename().length() > 0) {
				String fileName = imgFile.getOriginalFilename();
				String newFileName = (count + 1) + fileName.substring(fileName.lastIndexOf("."), fileName.length());
				try {
					saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, "", newFileName);
					saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl_bak, "", newFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				count++;
			}
		}
		
//		File dir_bak = new File(uploadFileUrl_bak);
//		dir_bak.setWritable(true,false);
//		if (!dir.exists()) {
//			dir.mkdirs();
//			System.out.println("创建目录2");
//		} else {
//			System.out.println("目录存在2");
//			count = dir.listFiles().length;
//		}
//		//获取多个file
//		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
//			String key = (String) it.next();
//			MultipartFile imgFile = multipartRequest.getFile(key);
//			if (imgFile.getOriginalFilename().length() > 0) {
//				String fileName = imgFile.getOriginalFilename();
//				String newFileName = (count + 1) + fileName.substring(fileName.lastIndexOf("."), fileName.length());
//				try {
//					saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl_bak, newFileName);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				count++;
//			}
//		}
		
		Sample info = sampleManager.getBySampleNo(sampleno);
		info.setNote(info.getNote()+imgDescription);
		info.setHasimages(1);
		sampleManager.save(info);
	}

    //保存文件
	private void saveFileFromInputStream(InputStream stream, String path, String backpath, String filename) throws IOException {
		Thumbnails.of(stream).forceSize(300, 200).outputQuality(1.0f).toFile(path + "/" + filename);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/getImage")
	@ResponseBody
	public void getImages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		String html = "";
        String sampleno = request.getParameter("sampleno");
        Sample info = sampleManager.getBySampleNo(sampleno);
        String uploadFileUrl = Constants.imageUrl + sampleno;
        String imgUrl = "fxglabfxgimagesfxguploadfxg" + sampleno + "fxg";
        //String imgUrl = "/images/upload/" + sampleno + "/";
		File dir = new File(uploadFileUrl);
		if (dir.exists()) {
			System.out.println("dir  exist!");
			File[] files = dir.listFiles();
			for (File f : files) {
				if (f.getName().endsWith(".jpg") || f.getName().endsWith(".JPG") || f.getName().endsWith(".PNG") || f.getName().endsWith(".png")) {
					html += "<a href='" + imgUrl + f.getName() + "'><img src='" + imgUrl + f.getName() 
							+ "' data-title='" + sampleno + "' data-description='" + info.getNote() + "'><fxga>";
				}
			}
			/*if (files.length >= 1) {
				html = "<div id='galleria_" + sampleno +"'>" + html + "</div>";
			}*/
			obj.put("html", html);
			obj.put("exit", "true");
		}else
			obj.put("exit", "false");
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
	}
	
	@RequestMapping(value = "/writeBack*", method = RequestMethod.GET)
	@ResponseBody
	public void getNeedWriteBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NeedWriteCount> list = sampleManager.getAllWriteBack(Constants.DF3.format(new Date()));
		JSONArray array = new JSONArray();
		for (NeedWriteCount nwc : list) {
			JSONObject obj = new JSONObject();
			obj.put("code", nwc.getCode());
			obj.put("count", nwc.getCount());
			obj.put("list", nwc.getList());
			array.put(obj);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
	/**
	 * 打印报告单时，保存为PDF文件
	 * 张晋南 2016-05-23
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHtml*", method = RequestMethod.POST)
	@ResponseBody
	public boolean getSaveHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sampleNo = request.getParameter("sampleNo");
		String lastNo = request.getParameter("lastNo");
		String docIdNo = request.getParameter("docIdNo");
		String baseurl = "http://localhost:8080/print/sample?docId="+docIdNo+"&sampleNo="+sampleNo+"&last="+lastNo;
		String filename = new StringBuffer().append("c:\\").append(sampleNo).append(".pdf").toString();
//		PDFConverterUtil.getInstance().processFile("c:\\", filename, baseurl);
//		String html = PDFConversionUtil.getInstance().readFile(baseurl, "UTF-8");
//		System.out.println(html);
//		PDFConversionUtil.getInstance().doConversion(html, filename);
		System.out.println();
		System.out.println(docIdNo+"|"+sampleNo+"|"+lastNo+"|"+baseurl);
		return true;
	}
}
