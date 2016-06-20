package com.smart.webapp.controller.rule;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.service.rule.IndexManager;
import com.smart.service.rule.ItemManager;
import com.smart.service.rule.ResultManager;
import com.smart.service.UserManager;
import com.smart.model.rule.Index;
import com.smart.model.rule.Item;
import com.smart.model.user.User;

@Controller
@RequestMapping("/item/ajax")
public class ItemAjaxController {

	@Autowired
	private IndexManager indexManager = null;
	@Autowired
	private ItemManager itemManager = null;
	@Autowired
	private UserManager userManager = null;
	@Autowired
	private ResultManager resultManager = null;
	
	@RequestMapping(value = "/getItem*", method = RequestMethod.GET)
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		if (id.startsWith("I")) {
			id = id.substring(1);
		}
				
		Index index = indexManager.get(Long.parseLong(id));
		JSONArray array = new JSONArray();
		if (index != null) {
			for (Item item : itemManager.getByIndexId(index.getId())) {
				String unit = item.getUnit();
				String value = item.getValue().replace("&&", "与").replace("||", "或");
				if (!StringUtils.isEmpty(unit)) {
					unit = "," + unit;
				} else {
					unit = "";
				}
				JSONObject o = new JSONObject();
				o.put("id", "I" + item.getId());
				o.put("content", index.getName()+":"+value+" ("+index.getSampleFrom()+unit+")");
				array.put(o);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}
	
	@RequestMapping(value = "/addItem*", method = RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Item item = new Item();
		String id = request.getParameter("id");
		if (id.startsWith("I")) {
			id = id.substring(1);
		}
		String value = request.getParameter("value");
		String unit = request.getParameter("unit");
		Index index = indexManager.get(Long.parseLong(id));
		item.setIndexId(index.getIndexId());
		item.setValue(value);
		item.setUnit(unit);

		// 创建者信息保存
		User createUser = userManager.getUserByUsername(request.getRemoteUser());
		item.setCreateUser(createUser.getName());
		item.setCreateTime(new Date());
		
		Item newItem = itemManager.save(item);

		JSONObject obj = new JSONObject();
		value = newItem.getValue().replace("&&", "与").replace("||", "或");
		unit = StringUtils.isEmpty(unit) ? "" : ("," + unit);
		obj.put("value", index.getName()+":"+value+" ("+index.getSampleFrom()+unit+")");
		obj.put("id", "I" + newItem.getId().toString());

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj.toString());
		return null;
	}
	
	@RequestMapping(value = "/deleteItem*", method = RequestMethod.GET)
	@ResponseBody
	public int delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return -1;
		}
		if (id.startsWith("I")) {
			id = id.substring(1);
			long _id = Long.parseLong(id);
			int num = itemManager.get(_id).getRules().size();
			if (num == 0) {
				itemManager.remove(_id);
				return 0;
			} else {
				return num;
			}
		} else if (id.startsWith("R")) {
			id = id.substring(1);
			long _id = Long.parseLong(id);
			int num = resultManager.get(_id).getRules().size();
			if (num == 0) {
				resultManager.remove(_id);
				return 0;
			} else {
				return num;
			}
		}
		return -1;
	}
}
