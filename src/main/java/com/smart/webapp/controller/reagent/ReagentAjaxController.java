package com.smart.webapp.controller.reagent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.Constants;
import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.In;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;
import com.smart.model.user.User;
import com.smart.webapp.util.DataResponse;
import com.zju.api.model.SyncReagent;

@Controller
@RequestMapping("/ajax/reagent*")
public class ReagentAjaxController extends ReagentBaseController {

	@RequestMapping(value = "/getReagent*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();
		List<Reagent> list = reagentManager.getReagents(name, lab);
		JSONArray array = new JSONArray();
		if (list != null) {
			for (Reagent r : list) {
				JSONObject o = new JSONObject();
				o.put("id", r.getId());
				o.put("name", r.getName());
				array.put(o);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/getByType*", method = RequestMethod.GET)
	@ResponseBody
	public String searchByType(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		int type = Integer.parseInt(request.getParameter("type"));
		
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();		
		JSONArray array = new JSONArray();
		if(type == 1) {
			
		} else if(type == 2) {
			List<Reagent> list = reagentManager.getReagents(name, lab);
			if (list != null) {
				for (Reagent r : list) {
					JSONObject o = new JSONObject();
					o.put("id", r.getId());
					o.put("name", r.getNameAndSpecification() + 
							"[" + r.getPlaceoforigin() + "][" + r.getBrand() + "][单价:" 
							+ r.getPrice() + "][包装:" + r.getBaozhuang() + "]");
					array.put(o);
				}
			}
		} else {
			List<Combo> list = comboManager.getCombos(name);
			if (list != null) {
				for (Combo c : list) {
					JSONObject o = new JSONObject();
					o.put("id", c.getId());
					o.put("name", c.getName() + " " + c.getCreator() + " " + Constants.DF.format(c.getCreatetime()));
					array.put(o);
				}
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(array.toString());
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/candr*")
    public void comboAndReagent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Combo c = comboManager.get(Long.parseLong(request.getParameter("cid")));
		if(request.getParameter("oper").equals("add")) {
			Reagent r = reagentManager.getByname(request.getParameter("name"));
			c.getReagents().add(r);
		} else {
			Reagent r = reagentManager.get(Long.parseLong(request.getParameter("id")));
			c.getReagents().remove(r);
		}
		comboManager.save(c);
	}
	
	@RequestMapping(value = "/indata*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getInData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		int type = Integer.parseInt(request.getParameter("type"));
		String lab = userManager.getUserByUsername(request.getRemoteUser()).getLastLab();
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		if(type == 1) {
			List<SyncReagent> srList = rmiService.getSyncReagent(id+"");
			String productcode = "";
			Set<String> productSet = new HashSet<String>();
			Map<String, SyncReagent> srMap = new HashMap<String, SyncReagent>();
			for(SyncReagent sr : srList) {
				productSet.add(sr.getSpec_id());
				productSet.add(sr.getWzbm());
				srMap.put(sr.getSpec_id(), sr);
				srMap.put(sr.getWzbm(), sr);
			}
			productcode = productSet.toString().replace(", ", "','");
			productcode = "'" + productcode.substring(1, productcode.length()-1) + "'";
			List<Reagent> list = reagentManager.getByProduct(productcode,lab);
			dataResponse.setRecords(list.size());
			for(Reagent r : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				SyncReagent sr = srMap.get(r.getProductcode());
				map.put("id", r.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", "<input type='text' id='" + r.getId() + "_batch' class='editable' style='height:18px;width:98%' value='" + sr.getProduct_lot() + "'>" + "</input>");
				map.put("place", r.getPlaceoforigin());
				map.put("brand", r.getBrand());
				map.put("baozhuang", r.getBaozhuang());
				map.put("price", r.getPrice());
				map.put("num", "<input type='text' id='" + r.getId() + "_num' class='editable' style='height:18px;width:98%' value='" + sr.getQuantity() + "'>" + "</input>");
				map.put("isqualified", "是");
				map.put("exedate", "<input type='text' id='" + r.getId() + "_exedate' class='editable' style='height:18px;width:98%' value='" + sr.getExpired_date() + "'>" + "</input>");
				dataRows.add(map);
			}
		} else if(type == 2) {
			Reagent r = reagentManager.get(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", r.getId());
			map.put("name", r.getNameAndSpecification());
			map.put("batch", "<input type='text' id='" + r.getId() + "_batch' class='editable' style='height:18px;width:98%'>" + "</input>");
			map.put("place", r.getPlaceoforigin());
			map.put("brand", r.getBrand());
			map.put("baozhuang", r.getBaozhuang());
			map.put("price", r.getPrice());
			map.put("num", "<input type='text' id='" + r.getId() + "_num' class='editable' style='height:18px;width:98%' value='1'>" + "</input>");
			map.put("isqualified", "是");
			map.put("exedate", "<input type='text' id='" + r.getId() + "_exedate' class='editable' style='height:18px;width:98%'>" + "</input>");
			dataRows.add(map);
			dataResponse.setRecords(1);
		} else {
			Combo c = comboManager.get(id);
			dataResponse.setRecords(c.getReagents().size());
			for(Reagent r : c.getReagents()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.getId());
				map.put("name", r.getNameAndSpecification());
				map.put("batch", "<input type='text' id='" + r.getId() + "_batch' class='editable' style='height:18px;width:98%'>" + "</input>");
				map.put("place", r.getPlaceoforigin());
				map.put("brand", r.getBrand());
				map.put("baozhuang", r.getBaozhuang());
				map.put("price", r.getPrice());
				map.put("num", "<input type='text' id='" + r.getId() + "_num' class='editable' style='height:18px;width:98%' value='1'>" + "</input>");
				map.put("isqualified", "是");
				map.put("exedate", "<input type='text' id='" + r.getId() + "_exedate' class='editable' style='height:18px;width:98%'>" + "</input>");
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/outdata*", method = { RequestMethod.GET })
	@ResponseBody
	public DataResponse getOutData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		int type = Integer.parseInt(request.getParameter("type"));
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		if(type == 1) {
	
		} else if(type == 2) {
			Reagent r = reagentManager.get(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", r.getId());
			map.put("name", r.getNameAndSpecification());
			String batch = "<select id='" + r.getId() + "_batch' class='editable' style='height:18px;width:98%'>";
			for(Batch b : batchManager.getByRgId(r.getId())) {
				if(b.getNum() > 0 || b.getSubnum() > 0) {
					if(r.getSubtnum() > 1) {
						batch += "<option value='" + b.getBatch() + "'>" + b.getBatch() + "[库存:" + b.getNum() + r.getUnit() + "]</option>";
					} else {
						batch += "<option value='" + b.getBatch() + "'>" + b.getBatch() + "[库存:" + (b.getNum()*r.getSubtnum() + b.getSubnum()) + r.getSubunit() +  "]</option>";
					}
				}
			}
			batch += "</select>";
			map.put("batch", batch);
			map.put("place", r.getPlaceoforigin());
			map.put("brand", r.getBrand());
			map.put("baozhuang", r.getBaozhuang());
			map.put("price", r.getPrice());
			map.put("num", "<input type='text' id='" + r.getId() + "_num' class='editable' style='height:18px;width:98%' value='1'>" + "</input>");
			dataRows.add(map);
			dataResponse.setRecords(1);
		} else {
			Combo c = comboManager.get(id);
			dataResponse.setRecords(c.getReagents().size());
			for(Reagent r : c.getReagents()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.getId());
				map.put("name", r.getNameAndSpecification());
				String batch = "<select id='" + r.getId() + "_batch class='editable' style='height:18px;width:98%'>";
				for(Batch b : batchManager.getByRgId(r.getId())) {
					if(b.getNum() > 0) {
						if(r.getSubtnum() > 1) {
							batch += "<option>" + b.getBatch() + "[库存:" + b.getNum() + r.getUnit() + "]</option>";
						} else {
							batch += "<option>" + b.getBatch() + "[库存:" + (b.getNum()*r.getSubtnum() + b.getSubnum()) + r.getSubunit() +  "]</option>";
						}
					}
				}
				batch += "</select>";
				map.put("batch", batch);
				map.put("place", r.getPlaceoforigin());
				map.put("brand", r.getBrand());
				map.put("baozhuang", r.getBaozhuang());
				map.put("price", r.getPrice());
				map.put("num", "<input type='text' id='" + r.getId() + "_num' class='editable' style='height:18px;width:98%' value='1'>" + "</input>");
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);
		response.setContentType("text/html; charset=UTF-8");
		return dataResponse;
	}
	
	@RequestMapping(value = "/savein*", method = RequestMethod.POST)
	@ResponseBody
	public String savein(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long nowtime = System.currentTimeMillis();
		Date now = new Date(nowtime);
		try {
			String text = request.getParameter("text");
			User user = userManager.getUserByUsername(request.getRemoteUser());
			Map<Long, Map<String, Object>> inmap = new HashMap<Long, Map<String, Object>>();
			for(String s : text.split(";")) {
				String[] idNum = s.split(":");
				Map<String, Object> in = new HashMap<String, Object>();
				in.put("batch", idNum[1]);
				in.put("num", Integer.parseInt(idNum[2]));
				in.put("exedate", idNum[3]);
				inmap.put(Long.parseLong(idNum[0]), in);
			}
			String s = inmap.keySet().toString().replace("[", "").replace("]", "");
			List<Reagent> list = reagentManager.getByIds(s);
			List<Batch> needSaveBatch = new ArrayList<Batch>();
			List<In> needSaveIn = new ArrayList<In>();
			for(Reagent r : list) {
				boolean needNew = true;
				for(Batch b : batchManager.getByRgId(r.getId())) {
					if(b.getBatch().equals((String)inmap.get(r.getId()).get("batch"))) {
						b.setNum(b.getNum() + (Integer)inmap.get(r.getId()).get("num"));
						needNew = false;
						needSaveBatch.add(b);
					}
				}
				if(needNew) {
					Batch b = new Batch();
					b.setBatch((String)inmap.get(r.getId()).get("batch"));
					b.setExpdate((String)inmap.get(r.getId()).get("exedate"));
					b.setNum((Integer)inmap.get(r.getId()).get("num"));
					b.setSubnum(0);
					b.setRgId(r.getId());
					needSaveBatch.add(b);
				}
				In indata = new In();
				indata.setBatch((String)inmap.get(r.getId()).get("batch"));
				indata.setExdate((String)inmap.get(r.getId()).get("exedate"));
				indata.setIndate(now);
				indata.setIsqualified(1);
				indata.setNum((Integer)inmap.get(r.getId()).get("num"));
				indata.setOperator(user.getName());
				indata.setRgId(r.getId());
				indata.setLab(user.getLastLab());
				needSaveIn.add(indata);
			}
			batchManager.saveAll(needSaveBatch);
			inManager.saveAll(needSaveIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Long.toString(nowtime);
	}
	
	@RequestMapping(value = "/saveout*", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean success = true;
		try {
			String text = request.getParameter("text");
			String barcode = request.getParameter("barcode");
			String barcodeBatch = "";
			User user = userManager.getUserByUsername(request.getRemoteUser());
			List<Batch> needSaveBatch = new ArrayList<Batch>();
			List<Out> needSaveOut = new ArrayList<Out>();
			List<Reagent> list = new ArrayList<Reagent>();
			Map<Long, Map<String, Object>> inmap = new HashMap<Long, Map<String, Object>>();
			System.out.println(text + " " + barcode);
			if(text != null) {
				for(String s : text.split(";")) {
					String[] idNum = s.split(":");
					Map<String, Object> in = new HashMap<String, Object>();
					in.put("batch", idNum[1]);
					in.put("num", Integer.parseInt(idNum[2]));
					inmap.put(Long.parseLong(idNum[0]), in);
				}
				String s = inmap.keySet().toString().replace("[", "").replace("]", "");
				list = reagentManager.getByIds(s);
			}
			if(barcode != null) {
				In i = inManager.get(Long.parseLong(barcode.substring(0, barcode.length()-3)));
				System.out.println(i.getRgId());
				barcodeBatch = i.getBatch();
				list.add(reagentManager.get(i.getRgId()));
			}
			Set<String> rSet = new HashSet<String>();
			for(Reagent r : list) {
				rSet.add(r.getId() + "");
			}
			String rids = rSet.toString();
			rids = rids.substring(1, rids.length()-1);
			List<Batch> bList = batchManager.getByRgIds(rids);
			Map<Long, List<Batch>> batchMap = new HashMap<Long, List<Batch>>();
			for(Batch b : bList) {
				if(batchMap.containsKey(b.getRgId())) {
					batchMap.get(b.getRgId()).add(b);
				} else {
					List<Batch> bl = new ArrayList<Batch>();
					bl.add(b);
					batchMap.put(b.getRgId(), bl);
				}
			}
			if(barcode != null) {
				for(Reagent r : list) {
					for(Batch b : batchMap.get(r.getId())) {
						if(b.getBatch().equals(barcodeBatch)) {
							Out outdata = new Out();
							if(r.getSubtnum() > 1) {
								int num = r.getSubtnum() * b.getNum() + b.getSubnum();
								b.setNum((num- 1)/r.getSubtnum());
								b.setSubnum((num- 1)%r.getSubtnum());
							} else {
								b.setNum(b.getNum() - 1);
							}
							outdata.setNum(1);
							outdata.setBatch(b.getBatch());
							outdata.setOperator(user.getName());
							outdata.setOutdate(new Date());
							outdata.setRgId(r.getId());
							outdata.setLab(user.getLastLab());
							needSaveBatch.add(b);
							needSaveOut.add(outdata);
						}
					}
				}
			}
			if(text != null) {
				for(Reagent r : list) {
					for(Batch b : batchMap.get(r.getId())) {
						if(b.getBatch().equals((String)inmap.get(r.getId()).get("batch"))) {
							Out outdata = new Out();
							if(r.getSubtnum() > 1) {
								int num = r.getSubtnum() * b.getNum() + b.getSubnum();
								b.setNum((num- (Integer)inmap.get(r.getId()).get("num"))/r.getSubtnum());
								b.setSubnum((num- (Integer)inmap.get(r.getId()).get("num"))%r.getSubtnum());
							} else {
								b.setNum(b.getNum() - (Integer)inmap.get(r.getId()).get("num"));
							}
							outdata.setNum((Integer)inmap.get(r.getId()).get("num"));
							outdata.setBatch(b.getBatch());
							outdata.setOperator(user.getName());
							outdata.setOutdate(new Date());
							outdata.setRgId(r.getId());
							outdata.setLab(user.getLastLab());
							needSaveBatch.add(b);
							needSaveOut.add(outdata);
						}
					}
				}
			}
			batchManager.saveAll(needSaveBatch);
			outManager.saveAll(needSaveOut);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
	@RequestMapping(value = "/cancelout*", method = RequestMethod.POST)
	@ResponseBody
	public boolean cancelout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean success = true;
		try {
			Out out = outManager.get(Long.parseLong(request.getParameter("id")));
			Reagent r = reagentManager.get(out.getRgId());
			for(Batch b : batchManager.getByRgId(r.getId())) {
				if(b.getBatch().equals(out.getBatch())) {
					if(r.getSubtnum() > 1) {
						int num = b.getNum() * r.getSubtnum() + b.getSubnum() + out.getNum();
						b.setNum(num/r.getSubtnum());
						b.setSubnum(num%r.getSubtnum());
					} else {
						b.setNum(b.getNum() + out.getNum());
					}
					batchManager.save(b);
				}
			}
			outManager.remove(out);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
}
