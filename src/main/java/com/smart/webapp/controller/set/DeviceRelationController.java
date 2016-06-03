package com.smart.webapp.controller.set;

import com.smart.model.lis.Channel;
import com.smart.model.lis.Device;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.rule.IndexManager;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.UserUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.drools.core.util.index.IndexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: DeviceRelation
 * Description:仪器项目关系设置
 *
 * @Author:zhou
 * @Date:2016/5/31 10:47
 * @Version:
 */
@Controller
@RequestMapping("/set/devicerelation*")
public class DeviceRelationController {
    @Autowired
    private DeviceManager deviceManager = null;
    @Autowired
    private IndexManager indexManager = null;
    @Autowired
    private SectionManager sectionManager=null;
    @Autowired
    private UserManager userManager = null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        //部门编号
        String departmentId = request.getParameter("department");
        //departmentId="3010109";
        User operator = userManager.getUserByUsername(request.getRemoteUser());
        departmentId = operator.getLastLab();
        System.out.println("departmentId22=>"+operator.getLastLab());
        String departmentName = sectionManager.getByCode(departmentId).getName();
        //检验项目
        List<Index> indexes = indexManager.getAll();
        JSONArray jsonArray = new JSONArray();

        JSONObject root = new JSONObject();
        root.put("id",departmentId);
        root.put("pId","0");
        root.put("name",departmentName);
        root.put("open","true");
        jsonArray.put(root);
        for(Index index :indexes){
            String labDepartment = index.getLabdepartment();
            if(labDepartment==null || "".equals(labDepartment)) continue;
            //判断是否当前部门项目
            JSONObject node = new JSONObject();
            //System.out.println("labDepartment==>"+labDepartment+ "=="+departmentId);
            if(labDepartment.indexOf(departmentId)>=0){
                node.put("id",index.getId());
                node.put("indexid",index.getIndexId());
                node.put("name",index.getName());
                node.put("pId",departmentId);
                jsonArray.put(node);
            }
        }
        //System.out.println(jsonArray.toString());
        view.addObject("treeNodes",jsonArray);
        return  view;
    }

    /**
     * 查询仪器及部门列表信息
     * @param id   项目ID
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "getDataList*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String getData(@RequestParam(value = "id",required=true) Long id, HttpServletRequest request, HttpServletResponse response) throws JSONException,Exception{
        ModelAndView view = new ModelAndView();

        //indexid ="3129";
        //获取仪器信息
        Index index= indexManager.get(id);

        //已选择部门
        String labDepartment = index.getLabdepartment();
        //已选择仪器
        String instrument =index.getInstrument();

        //所有仪器信息
        List<Device> devicelist = deviceManager.getAll();
        //获取所有部门信息
        Map<String,String> departmentList = DepartUtil.getInstance(sectionManager).getMap();

        JSONObject jsonResult = new JSONObject();
        jsonResult.put("labDepartment",labDepartment);
        jsonResult.put("instrument",instrument);
        jsonResult.put("departmentList",departmentList);
        JSONObject jDevicelist = new JSONObject();
        Map<String,String> mDevices = new HashMap<>();
        for(Device a : devicelist){
            mDevices.put(a.getId(),a.getName());
        }

        jsonResult.put("devicelist",mDevices);

        System.out.println(jsonResult.toString());
        return jsonResult.toString();
    }

    /**
     * 更新项目所关联部门和所关联仪器
     * @param id       //项目ID
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveRelation",method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam (value="id",required=true) Long id,HttpServletRequest request, HttpServletResponse response)throws Exception{
        Index index = indexManager.get(id);
        String labDepartment = request.getParameter("department");  //部门
        String instrument = request.getParameter("instrument");     //仪器
        if(labDepartment!=null && !labDepartment.equals("")){
            index.setLabdepartment(labDepartment);
        }
        if(instrument!=null && !instrument.equals("")){
            index.setInstrument(instrument);
        }
        try{
            indexManager.save(index);
            return new JSONObject().put("result", "true").toString();
        }catch (Exception e){
            throw  e;
        }
    }
}
