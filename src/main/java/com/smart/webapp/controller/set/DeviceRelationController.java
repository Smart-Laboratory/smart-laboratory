package com.smart.webapp.controller.set;

import com.smart.model.lis.Device;
import com.smart.model.lis.TestReference;
import com.smart.model.rule.Index;
import com.smart.model.user.User;
import com.smart.service.DictionaryManager;
import com.smart.service.UserManager;
import com.smart.service.lis.DeviceManager;
import com.smart.service.lis.SectionManager;
import com.smart.service.lis.TestReferenceManager;
import com.smart.service.rule.IndexManager;
import com.smart.util.ConvertUtil;
import com.smart.webapp.util.DepartUtil;
import com.smart.webapp.util.SampleUtil;
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
import java.util.*;

/**
 * Title: DeviceRelation
 * Description:仪器项目关系设置
 *
 * @Author:zhou
 * @Date:2016/5/31 10:47
 * @Version:
 */
@Controller
@RequestMapping("/set/devicerelation")
public class DeviceRelationController {
    @Autowired
    private DeviceManager deviceManager = null;
    @Autowired
    private IndexManager indexManager = null;
    @Autowired
    private SectionManager sectionManager=null;
    @Autowired
    private UserManager userManager = null;
    @Autowired
    private DictionaryManager dictionaryManager =null;
    @Autowired
    private TestReferenceManager  testReferenceManager =null;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        //部门编号
        String departmentIds = request.getParameter("department");
        User operator = userManager.getUserByUsername(request.getRemoteUser());
        departmentIds = operator.getDepartment();
        List<Index> indexes = indexManager.getAll();
        JSONArray  arrTree = new JSONArray();
        String departmentId [] = departmentIds.split(",");
        for (int  i=0;i<departmentId.length;i++){
            JSONObject root = new JSONObject();
            String departmentName = sectionManager.getByCode(departmentId[i]).getName();
            root.put("id","p_"+departmentId[i]);
            root.put("pId","0");
            root.put("name",departmentName);
            root.put("open","true");
            arrTree.put(root);
        }

        //检验项目
        for(Index index :indexes){
            String labDepartment = index.getLabdepartment();
            if(labDepartment==null || "".equals(labDepartment)) continue;
            //判断是否当前部门项目
            JSONObject node = new JSONObject();
            //System.out.println("labDepartment==>"+labDepartment+ "=="+departmentId);
            for (int  i=0;i<departmentId.length;i++) {
                if(labDepartment.indexOf(departmentId[i])>=0){
                    node.put("id",index.getId());
                    node.put("indexid",index.getIndexId());
                    node.put("name",index.getName());
                    node.put("sampletype",index.getSampleFrom());
                    node.put("pId","p_"+departmentId[i]);
                    arrTree.put(node);
                }

            }

        }
        //获取所有部门信息
        Map<String,String> departmentList = DepartUtil.getInstance(sectionManager).getMap();
        //获取标本信息
        Map<String,String> sampleList = SampleUtil.getInstance(dictionaryManager).getMap();

        //获取所有仪器信息
        List<Device> devicelist = deviceManager.getAll();
        Map<String,String> mDevices = new HashMap<String,String>();
        for(Device a : devicelist){
            mDevices.put(a.getId(),a.getName());
        }
        JSONObject jsonDevice = new JSONObject(mDevices);
        JSONObject jsonDepartList = new JSONObject(departmentList);
        System.out.println(jsonDevice.toString());
        view.addObject("devicelist",jsonDevice);
        view.addObject("departlist",jsonDepartList);
        view.addObject("samplelist",sampleList);
        view.addObject("treeNodes",arrTree);
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
    public String getData(@RequestParam(value = "id",required=true) Long id, HttpServletRequest request, HttpServletResponse response) throws Exception{
        JSONObject jsonResult = new JSONObject();
        //获取仪器信息
        Index index= indexManager.get(id);
        //获取参考范围信息
        List<TestReference> testReferences = testReferenceManager.getTestRefenreceListByTestId(index.getIndexId());
        JSONArray jsonArray = new JSONArray();
        for(TestReference t:testReferences){
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("testid",ConvertUtil.null2String(t.getTestId()));
            jsonObject.put("testname",ConvertUtil.null2String(index.getName()));
            jsonObject.put("sex",ConvertUtil.null2String(t.getSex()));
            jsonObject.put("sampletype",ConvertUtil.null2String(t.getSampleType()));
            jsonObject.put("ageLow", ConvertUtil.null2String(t.getAgeLow()));
            jsonObject.put("ageHigh", ConvertUtil.null2String(t.getAgeHigh()));
            jsonObject.put("ageLowUnit", ConvertUtil.null2String(t.getAgeLowUnit()));
            jsonObject.put("ageHighUnit", ConvertUtil.null2String(t.getAgeHighUnit()));
            jsonObject.put("deviceid", ConvertUtil.null2String(t.getDeviceId()));
            jsonObject.put("direct", ConvertUtil.null2String(t.getDirect()));
            jsonObject.put("reference", ConvertUtil.null2String(t.getReference()));
            jsonObject.put("orderno", ConvertUtil.null2String(t.getOrderNo()));
            jsonArray.put(jsonObject);
        }
        //返回常用信息
        JSONObject jsonIndex = toJSON(index);
        jsonResult.put("index",jsonIndex);
        jsonResult.put("references",jsonArray);
//        System.out.println(jsonResult.toString());
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

    /**
     * 保存项目常用信息
     * @param id       //项目ID
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveActiveInfo*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String saveActiveInfo(@RequestParam (value="id",required=true) Long id,HttpServletRequest request, HttpServletResponse response)throws Exception{
        Index index = indexManager.get(id);

        String name = ConvertUtil.null2String(request.getParameter("name"));
        String eglish = ConvertUtil.null2String(request.getParameter("eglish"));
        String sampleFrom = ConvertUtil.null2String(request.getParameter("samplefrom"));
        String unit = ConvertUtil.null2String(request.getParameter("unit"));
        String defaultValue = ConvertUtil.null2String(request.getParameter("defaultvalue"));
        String outDate = ConvertUtil.null2String(request.getParameter("outdate"));
        int qcNeed = ConvertUtil.getIntValue(request.getParameter("qcneed"),0);
        String tea = ConvertUtil.null2String(request.getParameter("tea"));
        String testClass = ConvertUtil.null2String(request.getParameter("testclass"));
        int needHistory = ConvertUtil.getIntValue(request.getParameter("needhistory"),0);
        String method = ConvertUtil.null2String(request.getParameter("method"));
        String description = ConvertUtil.null2String(request.getParameter("description"));
        String guide = ConvertUtil.null2String(request.getParameter("guide"));
        String type = ConvertUtil.null2String(request.getParameter("type"));

        if(!name.equals("")) index.setName(name);
        if(!eglish.equals("")) index.setEnglish(eglish);
        if(!sampleFrom.equals("")) index.setSampleFrom(sampleFrom);
        if(!defaultValue.equals("")) index.setDefaultvalue(defaultValue);
        if(qcNeed >=0 ) index.setNeedhistory(qcNeed);
        if(!tea.equals("")) index.setTEA(tea);
        if(!testClass.equals("")) index.setTestClass(testClass);
        if(needHistory>=0) index.setNeedhistory(needHistory);
        if(!method.equals("")) index.setMethod(method);
        if(!description.equals("")) index.setDescription(description);
        if(!guide.equals("")) index.setGuide(guide);
        if(!type.equals("")) index.setType(type);
        if(!unit.equals("")) index.setUnit(unit);
        try{
            indexManager.save(index);
            return new JSONObject().put("result", "true").toString();
        }catch (Exception e){
            throw  e;
        }
    }


    /**
     * 保存不常用信息
     * @param id        项目ID
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveInActiveInfo*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String saveInActiveInfo(@RequestParam (value="id",required=true) Long id,HttpServletRequest request, HttpServletResponse response)throws Exception{
        Index index = indexManager.get(id);
        String principle = ConvertUtil.null2String(request.getParameter("principle"));          //测定原理
        String workCriterion = ConvertUtil.null2String(request.getParameter("workcriterion")); //工作规范
        String increasedHint = ConvertUtil.null2String(request.getParameter("increasedhint"));//升高意义
        String decreasedHint = ConvertUtil.null2String(request.getParameter("decreasedhint"));//降低意义
        String notes= ConvertUtil.null2String(request.getParameter("notes"));                   //注意事项
        String methodName = ConvertUtil.null2String(request.getParameter("methodname"));        //方法名称

        if(!principle.equals("")) index.setPrinciple(principle);
        if(!workCriterion.equals("")) index.setWorkCriterion(workCriterion);
        if(!increasedHint.equals("")) index.setIncreasedHint(increasedHint);
        if(!decreasedHint.equals("")) index.setDecreasedHint(decreasedHint);
        if(!notes.equals("")) index.setNotes(notes);
        if(!methodName.equals("")) index.setMethodName(methodName);
        try{
            indexManager.save(index);
            return new JSONObject().put("result", "true").toString();
        }catch (Exception e){
            throw  e;
        }
    }

    /**
     * 保存参考范围
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/saveReference*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String save(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String datas = request.getParameter("datas");
        JSONArray jsonArray = new JSONArray(datas);
        List<TestReference> testReferences = new ArrayList<TestReference>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String deviceid = ConvertUtil.null2String(obj.getString("deviceid"));                                             //仪器ID
            String testid = obj.getString("testid");                                        //检验项目ID
            String sampletype = ConvertUtil.null2String(obj.getString("sampletype"));       //标本类型
            int sex = ConvertUtil.getIntValue(obj.getString("sex"), 0);                     //性别
            int orderno = ConvertUtil.getIntValue(obj.getString("orderno"), 0);              //序号
            int direct = ConvertUtil.getIntValue(obj.getString("direct"), 0);
            int ageLow = ConvertUtil.getIntValue(obj.getString("ageLow"));//年龄低限
            int ageHigh = ConvertUtil.getIntValue(obj.getString("ageHigh"));//年龄高限
            String ageLowUnit = ConvertUtil.null2String(obj.getString("ageLowUnit"));//年龄低限单位
            String ageHighUnit = ConvertUtil.null2String(obj.getString("ageHighUnit"));//年龄高限单位
            String reference = ConvertUtil.null2String(obj.getString("reference"));            //参考值

            TestReference testReference = new TestReference();
            testReference.setDeviceId(deviceid);
            testReference.setTestId(testid);
            testReference.setSampleType(sampletype);
            testReference.setSex(sex);
            testReference.setAgeLow(ageLow);
            testReference.setAgeHigh(ageHigh);
            testReference.setAgeLowUnit(ageLowUnit);
            testReference.setAgeHighUnit(ageHighUnit);
            testReference.setOrderNo(orderno);
            testReference.setDirect(direct);
            testReference.setReference(reference);
            testReferences.add(testReference);
        }
        try
        {
            testReferenceManager.saveTestReferences(testReferences);  //批量保存数据
            return new JSONObject().put("result","true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return new JSONObject().put("result","保存错误").toString();
        }
    }

    /**
     * 删除参考范围
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/deleteReference*",method =RequestMethod.POST)
    @ResponseBody
    public String deleteReference(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String testid = ConvertUtil.null2String(request.getParameter("testid"));
        int sex = ConvertUtil.getIntValue(request.getParameter("sex"),-1);
        int orderno = ConvertUtil.getIntValue(request.getParameter("orderno"),-1);
        try {
            testReferenceManager.deleteTestReference(testid,sex,orderno);
            return  new JSONObject().put("result","true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return  new JSONObject().put("result","false").toString();
        }
    }

    /**
     * 转换为JSON
     * @param index
     * @return
     * @throws JSONException
     */
    private JSONObject toJSON(Index index) throws JSONException{
        JSONObject jsonObject = new JSONObject();

        //常用信息
        jsonObject.put("id", ConvertUtil.null2String(index.getId()));
        jsonObject.put("indexid",ConvertUtil.null2String(index.getIndexId()));
        jsonObject.put("name",ConvertUtil.null2String(index.getName()));
        jsonObject.put("testclass",ConvertUtil.null2String(index.getTestClass()));
        jsonObject.put("eglish",ConvertUtil.null2String(index.getEnglish()));
        jsonObject.put("samplefrom",ConvertUtil.null2String(index.getSampleFrom()));
        jsonObject.put("labdepartment",ConvertUtil.null2String(index.getLabdepartment()));
        jsonObject.put("instrument",ConvertUtil.null2String(index.getInstrument()));
        jsonObject.put("type",ConvertUtil.null2String(index.getType()));
        jsonObject.put("algorithm",ConvertUtil.null2String(index.getAlgorithm()));
        jsonObject.put("method",ConvertUtil.null2String(index.getMethod()));
        jsonObject.put("description",ConvertUtil.null2String(index.getDescription()));
        jsonObject.put("guide",ConvertUtil.null2String(index.getGuide()));
        jsonObject.put("unit",ConvertUtil.null2String(index.getUnit()));
        jsonObject.put("needhistory",ConvertUtil.null2String(index.getNeedhistory()));
        jsonObject.put("tea",ConvertUtil.null2String(index.getTEA()));
        jsonObject.put("ccv",ConvertUtil.null2String(index.getCCV()));
        jsonObject.put("inuredate",ConvertUtil.null2String(index.getInureDate()));
        jsonObject.put("outdate",ConvertUtil.null2String(index.getOutDate()));
        jsonObject.put("defaultvalue",ConvertUtil.null2String(index.getDefaultvalue()));
        jsonObject.put("qcneed",ConvertUtil.null2String(index.getQcNeed()));

        //不常用信息
        jsonObject.put("principle",ConvertUtil.null2String(index.getPrinciple()));
        jsonObject.put("workcriterion",ConvertUtil.null2String(index.getWorkCriterion()));
        jsonObject.put("increasedhint",ConvertUtil.null2String(index.getIncreasedHint()));
        jsonObject.put("decreasedhint",ConvertUtil.null2String(index.getDecreasedHint()));
        jsonObject.put("notes",ConvertUtil.null2String(index.getNotes()));
        jsonObject.put("methodname",ConvertUtil.null2String(index.getMethodName()));

        return jsonObject;
    }
}
