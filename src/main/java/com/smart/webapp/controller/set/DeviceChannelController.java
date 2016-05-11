package com.smart.webapp.controller.set;

import com.smart.model.lis.Channel;
import com.smart.model.lis.Device;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.service.UserManager;
import com.smart.service.lis.ChannelManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.rule.IndexManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 仪器通道设置
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/2 9:08
 * @Version:
 */
@Controller
@RequestMapping(value = "/set/channelset*")
public class DeviceChannelController {
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private SectionManager sectionManager = null;
    @Autowired
    private DeviceManager  deviceManager = null;
    @Autowired
    private IndexManager indexManager = null;
    @Autowired
    private ChannelManager channelManager= null;
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
        //获取仪器
        //List<Device> devices = deviceManager.getAll();
        List<Index> indexes = indexManager.getAll();
        JSONArray jsonArray = new JSONArray();

//        JSONObject root = new JSONObject();
//        root.put("id",departmentId);
//        root.put("pId","0");
//        root.put("name",departmentName);
//        root.put("open","true");
//        jsonArray.put(root);

        String instruments = "";
        for(Index index :indexes){
            String labDepartment = index.getLabdepartment();
            String instrument =  index.getInstrument();
            if(labDepartment==null || "".equals(labDepartment)) continue;
            //System.out.println("labDepartment==>"+labDepartment+ "=="+departmentId);
            //获取部门对应的仪器
            if(labDepartment.indexOf(departmentId)>=0){
               String lastChar = instrument.substring(instrument.length()-1,instrument.length());//
               instruments += lastChar.equals(",")?"":","; //末尾不为","则增加分隔符号","
               instruments += instrument;
            }
        }
        String [] arrayInstrument = instruments.split(",");
        Map<String,String> deviceMap = new HashMap<String,String>();
        for (int i=0;i<arrayInstrument.length;i++){
            String code = arrayInstrument[i];
            if(code == null || code.equals("")) continue;
            Device device = deviceManager.getDeviceByCode(code);
            deviceMap.put(code,device.getName());
        }

        for(Map.Entry<String, String> entry:deviceMap.entrySet()){
            JSONObject node = new JSONObject();
            node.put("id",entry.getKey());
            node.put("name",entry.getValue());
            node.put("name",entry.getValue());
            jsonArray.put(node);
            //System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
        //System.out.println(deviceMap.toString());
        view.addObject("treeNodes",jsonArray);
        return  view;
    }

    @RequestMapping(value = "/save*",method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request,HttpServletResponse response) throws JSONException,Exception{

        String datas = request.getParameter("datas");

        JSONArray jsonArray = new JSONArray(datas);
        for(int i=0;i< jsonArray.length();i++){
            JSONObject iObj=jsonArray.getJSONObject(i);
            String deviceid=iObj.getString("deviceid");
            String testid=iObj.getString("testid");
            String channelValue  = iObj.getString("channel");

            System.out.println("deviceid=>"+deviceid);
            System.out.println("testid=>"+testid);
            System.out.println("channelValue=>"+channelValue);
            Channel channel = new Channel();
            //channel.setId(id);
            channel.setDeviceId(deviceid);
            channel.setTestId(testid);
            channel.setChannel(channelValue);
            channelManager.save(channel);
        }


        return "";
    }
}
