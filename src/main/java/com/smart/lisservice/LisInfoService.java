
package com.smart.lisservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "LisInfoService", targetNamespace = "http://server.webservice.zcw.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface LisInfoService {


    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPatientTypeList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetPatientTypeList")
    @ResponseWrapper(localName = "getPatientTypeListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetPatientTypeListResponse")
    public String getPatientTypeList();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSampleNo", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetSampleNo")
    @ResponseWrapper(localName = "getSampleNoResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetSampleNoResponse")
    public String getSampleNo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPatientInfoList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetPatientInfoList")
    @ResponseWrapper(localName = "getPatientInfoListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetPatientInfoListResponse")
    public String getPatientInfoList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "returnSample", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.ReturnSample")
    @ResponseWrapper(localName = "returnSampleResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.ReturnSampleResponse")
    public String returnSample(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        XMLGregorianCalendar arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDrugList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetDrugList")
    @ResponseWrapper(localName = "getDrugListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetDrugListResponse")
    public String getDrugList();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveSampleFlowLog", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.SaveSampleFlowLog")
    @ResponseWrapper(localName = "saveSampleFlowLogResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.SaveSampleFlowLogResponse")
    public String saveSampleFlowLog(
        @WebParam(name = "arg0", targetNamespace = "")
        SampleLog arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "booking", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.Booking")
    @ResponseWrapper(localName = "bookingResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.BookingResponse")
    public String booking(
        @WebParam(name = "arg0", targetNamespace = "")
        AccountItem arg0);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBacteriaList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetBacteriaList")
    @ResponseWrapper(localName = "getBacteriaListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetBacteriaListResponse")
    public String getBacteriaList();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getWardList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetWardList")
    @ResponseWrapper(localName = "getWardListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetWardListResponse")
    public String getWardList();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTestPurposeList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetTestPurposeList")
    @ResponseWrapper(localName = "getTestPurposeListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetTestPurposeListResponse")
    public String getTestPurposeList();

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "saveTestResult", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.SaveTestResult")
    @ResponseWrapper(localName = "saveTestResultResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.SaveTestResultResponse")
    public String saveTestResult(
        @WebParam(name = "arg0", targetNamespace = "")
        Report arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCollectedSampleList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetCollectedSampleList")
    @ResponseWrapper(localName = "getCollectedSampleListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetCollectedSampleListResponse")
    public String getCollectedSampleList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSampleTypeList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetSampleTypeList")
    @ResponseWrapper(localName = "getSampleTypeListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetSampleTypeListResponse")
    public String getSampleTypeList();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDepartMentList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetDepartMentList")
    @ResponseWrapper(localName = "getDepartMentListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetDepartMentListResponse")
    public String getDepartMentList();

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getReceivedSampleList", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetReceivedSampleList")
    @ResponseWrapper(localName = "getReceivedSampleListResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetReceivedSampleListResponse")
    public String getReceivedSampleList(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTestInfo", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetTestInfo")
    @ResponseWrapper(localName = "getTestInfoResponse", targetNamespace = "http://server.webservice.zcw.com/", className = "com.smart.lisservice.GetTestInfoResponse")
    public String getTestInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
