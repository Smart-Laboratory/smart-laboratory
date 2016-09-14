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
import com.smart.webapp.util.DataResponse;
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
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/12 21:29
 * @Version:
 */

@RequestMapping(value = "/set/devicerelationlist*")
@Controller
public class DeviceRelationListController {
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
    private TestReferenceManager testReferenceManager =null;
    /**
     * 试验项目列表主页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        //部门编号
        String departmentIds = request.getParameter("department");

        User operator = userManager.getUserByUsername(request.getRemoteUser());
        departmentIds = operator.getDepartment();
        List<Index> indexes = indexManager.getAll();
        JSONArray arrTree = new JSONArray();
        //加载有权限部门树形列表
        String departmentId [] = departmentIds.split(",");
        for (int  i=0;i<departmentId.length;i++){
            JSONObject root = new JSONObject();
            String departmentName = sectionManager.getByCode(departmentId[i]).getName();
            root.put("id",""+departmentId[i]);
            root.put("pId","0");
            root.put("name",departmentName);
            root.put("open","true");
            arrTree.put(root);
        }
        //加载未配置部门项目
        System.out.println(operator.getUsername());
        if(operator.getUsername().equals("admin")){
            JSONObject other = new JSONObject();
            other.put("id","other");
            other.put("pId","0");
            other.put("name","其他");
            other.put("open","true");
            arrTree.put(other);
        }

        view.addObject("treeNodes",arrTree);
        return  view;
    }

    /**
     * 查询项目列表信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping( value = "/getList" ,method = {RequestMethod.GET,RequestMethod.POST} )
    @ResponseBody
    public DataResponse getList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type =  request.getParameter("type");
        String pages = request.getParameter("page");
        String rows = request.getParameter("rows");
        String query = ConvertUtil.null2String(request.getParameter("query"));
        String sidx = request.getParameter("sidx");
        String sord = request.getParameter("sord");
        String departmentid = ConvertUtil.null2String(request.getParameter("departmentid"));
        boolean isAdmin  = request.getRemoteUser().equals("admin");
        int page = Integer.parseInt(pages);
        int row = Integer.parseInt(rows);
        int start = row * (page - 1);
        int end = row * page;

        DataResponse dataResponse = new DataResponse();
        List<Index> list = new ArrayList<Index>();
        int size = 0;
        try{
        	size = indexManager.getIndexsCount(query,departmentid,isAdmin,start,end,sidx,sord);
        }catch (Exception e){
            e.printStackTrace();
        }
        dataResponse.setRecords(size);
        int x = size % (row == 0 ? size : row);
        if (x != 0) {
            x = row - x;
        }
        int totalPage = (size + x) / (row == 0 ? size : row);
        dataResponse.setPage(page);
        dataResponse.setTotal(totalPage);
        list = indexManager.getIndexs(query,departmentid,isAdmin,start,end,sidx,sord);
        List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

        for(Index info :list) {
            Map<String, Object> map = new HashMap<String, Object>();

                String labdepart= ConvertUtil.null2String(info.getLabdepartment());

            if(isAdmin && departmentid.equals("other")){
                if(!labdepart.equals("")) continue;
            }
            if(!isAdmin){
                if(labdepart.indexOf(departmentid)<0) continue;
            }
            map.put("id", ConvertUtil.null2String(info.getId()));
            map.put("indexid", ConvertUtil.null2String(info.getIndexId()));
            map.put("name", ConvertUtil.null2String(info.getName()));
            map.put("english", ConvertUtil.null2String(info.getEnglish()));
            map.put("sampletype", ConvertUtil.null2String(info.getSampleFrom()));
            map.put("testclass", ConvertUtil.null2String(info.getTestClass()));
            map.put("labdepartment", ConvertUtil.null2String(info.getLabdepartment()));
            map.put("unit", ConvertUtil.null2String(info.getUnit()));
            dataRows.add(map);
        }
        dataResponse.setRows(dataRows);
        response.setContentType("text/html;charset=UTF-8");
        return dataResponse;
    }

    /**
     * 查询仪器及部门列表信息
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "ajaxeditdevicerelation*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ModelAndView getDevicerelation( HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView view = new ModelAndView("set/ajaxeditdevicerelation");
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        String method = ConvertUtil.null2String(request.getParameter("method"));    //add or edit
        JSONObject jsonResult = new JSONObject();
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


        JSONArray jsonRefArray = new JSONArray();       //参考范围DATA
        JSONArray jsonDicArray = new JSONArray();       //项目字典DATA
        if(id >0) {
            //获取指标信息
            Index index = indexManager.get(id);

            //生成项目字典
            String dictionaries = ConvertUtil.null2String(index.getDictionaries());
            if(!dictionaries.equals("")) {
                String[] arrDic = dictionaries.split(";");
                for (String s : arrDic) {
                    JSONObject jsonObject = new JSONObject();

                    String testKey = ConvertUtil.null2String(s.substring(0, s.indexOf(":")));
                    String testValue = ConvertUtil.null2String(s.substring(s.indexOf(":")+1));
                    jsonObject.put("textkey", testKey);
                    jsonObject.put("testvalue", testValue);
                    jsonDicArray.put(jsonObject);
                }
            }
            //System.out.println(jsonDicArray.toString());
            //获取参考范围信息
            List<TestReference> testReferences = testReferenceManager.getTestRefenreceListByTestId(index.getIndexId());

            for (TestReference t : testReferences) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("testid", ConvertUtil.null2String(t.getTestId()));
                jsonObject.put("testname", ConvertUtil.null2String(index.getName()));
                jsonObject.put("sex", ConvertUtil.null2String(t.getSex()));
                jsonObject.put("sampletype", ConvertUtil.null2String(t.getSampleType()));
                jsonObject.put("ageLow", ConvertUtil.null2String(t.getAgeLow()));
                jsonObject.put("ageHigh", ConvertUtil.null2String(t.getAgeHigh()));
                jsonObject.put("ageLowUnit", ConvertUtil.null2String(t.getAgeLowUnit()));
                jsonObject.put("ageHighUnit", ConvertUtil.null2String(t.getAgeHighUnit()));
                jsonObject.put("deviceid", ConvertUtil.null2String(t.getDeviceId()));
                jsonObject.put("direct", ConvertUtil.null2String(t.getDirect()));
                jsonObject.put("reference", ConvertUtil.null2String(t.getReference()));
                jsonObject.put("orderno", ConvertUtil.null2String(t.getOrderNo()));
                jsonRefArray.put(jsonObject);
            }

            view.addObject("index",index);                  //指标信息
            view.addObject("sampleType",SampleUtil.getInstance(dictionaryManager).getValue(index.getSampleFrom())); //样本类型中文
        }
        //返回常用信息
        //JSONObject jsonIndex = toJSON(index);
        JSONObject jsonDeviceList = new JSONObject(mDevices);
        JSONObject jsonDepartList = new JSONObject(departmentList);
        JSONObject jsonSampleList = new JSONObject(sampleList);
        view.addObject("references",jsonRefArray);       //参考范围信息
        view.addObject("departlist",jsonDepartList);    //部门信息
        view.addObject("devicelist",jsonDeviceList);    //仪器信息
        view.addObject("samplelist",sampleList);        //标本信息
        view.addObject("method",method);                //标本信息
        view.addObject("dictionaries",jsonDicArray);    //项目字典信息
        System.out.println(jsonResult.toString());
        return view;
    }


    /**
     * 保存项目常用信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveInfo*",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String saveInfo(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String indexId = ConvertUtil.null2String(request.getParameter("indexid"));
        Long id = ConvertUtil.getLongValue(request.getParameter("id"),-1l);
        Index index = null;
        if(id > 0){
            index = indexManager.get(id);
        }else {
            index = new Index();
            index.setIndexId(indexId);
            index.setCreateUser(request.getRemoteUser());
            index.setCreateTime(new Date());
            index.setModifyUser(request.getRemoteUser());
            index.setModifyTime(new Date());
        }
        //常用信息

        String name = ConvertUtil.null2String(request.getParameter("name"));
        String eglish = ConvertUtil.null2String(request.getParameter("eglish"));
        String sampleFrom = ConvertUtil.null2String(request.getParameter("hiddenSamplefrom"));
        String unit = ConvertUtil.null2String(request.getParameter("unit"));
        String defaultValue = ConvertUtil.null2String(request.getParameter("defaultvalue"));
        String outDate = ConvertUtil.null2String(request.getParameter("outdate"));
        int qcNeed = ConvertUtil.getIntValue(request.getParameter("qcneed"),0);
        String tea = ConvertUtil.null2String(request.getParameter("tea"));
        String ccv = ConvertUtil.null2String(request.getParameter("ccv"));
        String testClass = ConvertUtil.null2String(request.getParameter("testclass"));
        int needHistory = ConvertUtil.getIntValue(request.getParameter("needhistory"),0);
        String method = ConvertUtil.null2String(request.getParameter("method"));
        String description = ConvertUtil.null2String(request.getParameter("description"));
        String guide = ConvertUtil.null2String(request.getParameter("guide"));
        String type = ConvertUtil.null2String(request.getParameter("type"));
        int printord = ConvertUtil.getIntValue(request.getParameter("printord"),0);

        if(!name.equals("")) index.setName(name);
        if(!eglish.equals("")) index.setEnglish(eglish);
        if(!sampleFrom.equals("")) index.setSampleFrom(sampleFrom);
        if(!defaultValue.equals("")) index.setDefaultvalue(defaultValue);
        if(qcNeed >=0 ) index.setNeedhistory(qcNeed);
        if(!tea.equals("")) index.setTEA(tea);
        if(!ccv.equals("")) index.setCCV(ccv);
        if(!testClass.equals("")) index.setTestClass(testClass);
        if(needHistory>=0) index.setNeedhistory(needHistory);
        if(!method.equals("")) index.setMethod(method);
        if(!description.equals("")) index.setDescription(description);
        if(!guide.equals("")) index.setGuide(guide);
        if(!type.equals("")) index.setType(type);
        if(!unit.equals("")) index.setUnit(unit);
        if(printord>=0) index.setPrintord(ConvertUtil.null2String(printord));
        //不常用信息
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

        //部门仪器信息
        String labDepartment = request.getParameter("department");  //部门
        String instrument = request.getParameter("instrument");     //仪器
        if(!labDepartment.equals("")) index.setLabdepartment(labDepartment);
        if(!instrument.equals("")) index.setInstrument(instrument);

        //项目字典
        String dictionariesData = request.getParameter("dictionariesData");
        index.setDictionaries(dictionariesData);
//        JSONArray jsDictionaries = new JSONArray(dictionariesData);
//        for (int i = 0; i < jsDictionaries.length(); i++) {
//            JSONObject obj = jsDictionaries.getJSONObject(i);
//            String key = ConvertUtil.null2String(obj.getString("key"));
//            String value = ConvertUtil.null2String(obj.getString("value"));
//        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date outdate = sdf.parse(outDate);
//        if(outDate != null && !outDate.equals("")) index.setOutDate(outdate);
        //保存参考范围信息
        String datas = request.getParameter("datas");
        JSONArray jsonArray = new JSONArray(datas);
        List<TestReference> testReferences = new ArrayList<TestReference>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String deviceid = ConvertUtil.null2String(obj.getString("deviceid"));           //仪器ID
            String testid =  ConvertUtil.null2String(obj.getString("testid"));              //检验项目ID
            String sampletype = ConvertUtil.null2String(obj.getString("sampletype"));       //标本类型
            int sex = ConvertUtil.getIntValue(obj.getString("sex"), 0);                     //性别
            int ageLow = ConvertUtil.getIntValue(obj.getString("ageLow"));
            int ageHigh = ConvertUtil.getIntValue(obj.getString("ageHigh"));
            String ageLowUnit = ConvertUtil.null2String(obj.getString("ageLowUnit"));//年龄
            String ageHighUnit = ConvertUtil.null2String(obj.getString("ageHighUnit"));//年龄
            int orderno = ConvertUtil.getIntValue(obj.getString("orderno"), 0);              //序号
            int direct = ConvertUtil.getIntValue(obj.getString("direct"), 0);
            String reference = ConvertUtil.null2String(obj.getString("reference"));          //参考值

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


        try{
            indexManager.save(index);
            if(testReferences.size()>0) {
                testReferenceManager.saveTestReferences(testReferences);  //批量保存数据
            }
            //return new ModelAndView("redirect:/set/devicerelation","result", "true");
            return new JSONObject().put("result", "true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return new JSONObject().put("result", "false").toString();
        }
    }

    /**
     * 删除项目
     * @param request
     * @param response
     * @return
     * @throws JSONException
     * @throws Exception
     */
    @RequestMapping(value = "/deleteIndex*",method =RequestMethod.POST)
    @ResponseBody
    public String deleteIndex(@RequestParam(value = "id") Long id, HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            indexManager.remove(id);
            return  new JSONObject().put("result","true").toString();
        }catch (Exception e){
            e.printStackTrace();
            return  new JSONObject().put("result","false").toString();
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
