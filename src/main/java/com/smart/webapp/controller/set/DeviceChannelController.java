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
import com.smart.webapp.util.ChannelUtil;
import com.smart.webapp.util.DeviceUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        User operator = userManager.getUserByUsername(request.getRemoteUser()); //获取当前用户
        departmentId = operator.getLastLab();       //当前用户所选择科室

        String departmentName = sectionManager.getByCode(departmentId).getName(); //科室名称
        //获取仪器
        List<Index> indexes = indexManager.getIndexs("","",false,0,0,"","");
        JSONArray jsonArray = new JSONArray();
        String instruments = "";
        for(Index index :indexes){
            String labDepartment = index.getLabdepartment();
            String instrument =  index.getInstrument();
            if(labDepartment==null || "".equals(labDepartment)) continue;
            if(instrument==null || "".equals(instrument)) continue;
            //获取部门对应的仪器
            if(labDepartment.indexOf(departmentId)>=0){
               String lastChar = instrument.substring(instrument.length()-1,instrument.length());//
               instruments += lastChar.equals(",")?"":","; //末尾不为","则增加分隔符号","
               instruments += instrument;
            }
        }
        String [] arrayInstrument = instruments.split(",");
        Map<String,String> deviceMap = new HashMap<String,String>();
        for(String code:arrayInstrument){
            if(code == null || code.equals("")) continue;
            String value = DeviceUtil.getInstance(deviceManager).getValue(code);
            deviceMap.put(code,value);
        }

        for(Map.Entry<String, String> entry:deviceMap.entrySet()){
            JSONObject node = new JSONObject();
            node.put("id",entry.getKey());
            node.put("name",entry.getValue());
            node.put("name",entry.getValue());
            jsonArray.put(node);
        }
       // System.out.println(jsonArray.toString());
        view.addObject("treeNodes",jsonArray);
        return  view;
    }

    /**
     * 保存仪器通道数据
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/save*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String datas = request.getParameter("datas");
        JSONArray jsonArray = new JSONArray(datas);
        List<Channel> channels = new ArrayList<Channel>();
        for(int i=0;i< jsonArray.length();i++){
            JSONObject obj=jsonArray.getJSONObject(i);
            String deviceid=obj.getString("deviceid");          //仪器ID
            String testid=obj.getString("testid");              //检验项目ID
            String channelValue  = obj.getString("channel");    //通道
            String sampleType = obj.getString("sampletype");    //标本类型

            Channel channel = new Channel();
            channel.setDeviceId(deviceid);
            channel.setTestId(testid);
            channel.setChannel(channelValue);
            channel.setSampleType(sampleType);
            channels.add(channel);
        }
        channelManager.saveChannels(channels);  //批量保存数据
    }


    /**
     * 获取仪器的所检验项目通道数据
     * @param deviceid      //仪器ID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getData*",method = RequestMethod.POST,produces = "application/json; charset=utf-8" )
    @ResponseBody
    public String getChannelData(@RequestParam(value = "deviceid") String deviceid, HttpServletRequest request, HttpServletResponse response) throws  Exception{
        List<Index> indexes = indexManager.getIndexs("","",false,0,0,"","");
        List<Channel> channels = new ArrayList<Channel>();
        JSONArray jsonArray = new JSONArray();
        for(Index index :indexes){
            String instrument =  index.getInstrument();
            String testid = index.getIndexId();
            if(instrument==null || "".equals(instrument)) continue;
            Channel channel = null;
            JSONObject jsonObject= new JSONObject();
            //获取仪器相关检验项目
            if(instrument.indexOf(deviceid)>=0){
                channel = ChannelUtil.getInstance(channelManager).getValue(deviceid+"_"+testid);
                if(channel == null){
                    channel = new Channel();
                    //如果为空则从检验项目中获取
                    channel.setTestId(testid);
                    channel.setDeviceId(deviceid);
                    channel.setSampleType(index.getSampleFrom());
                    channel.setChannel("");
                }
                jsonObject.put("deviceid",channel.getDeviceId()+"");
                jsonObject.put("testid",channel.getTestId()+"");
                jsonObject.put("testname",index.getName()+"");
                jsonObject.put("channel",channel.getChannel()+"");
                jsonObject.put("sampletype",channel.getSampleType()+"");
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray.toString();
    }
}
