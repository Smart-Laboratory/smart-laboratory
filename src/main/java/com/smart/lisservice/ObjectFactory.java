
package com.smart.lisservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.smart.lisservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetListTestResultResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getListTestResultResponse");
    private final static QName _GetPatientInfoList_QNAME = new QName("http://server.webservice.zcw.com/", "getPatientInfoList");
    private final static QName _ReturnSample_QNAME = new QName("http://server.webservice.zcw.com/", "returnSample");
    private final static QName _SaveSampleFlowLog_QNAME = new QName("http://server.webservice.zcw.com/", "saveSampleFlowLog");
    private final static QName _SaveSampleFlowLogResponse_QNAME = new QName("http://server.webservice.zcw.com/", "saveSampleFlowLogResponse");
    private final static QName _GetBacteriaList_QNAME = new QName("http://server.webservice.zcw.com/", "getBacteriaList");
    private final static QName _GetBacteriaListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getBacteriaListResponse");
    private final static QName _GetPatientInfoListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getPatientInfoListResponse");
    private final static QName _GetCollectedSampleList_QNAME = new QName("http://server.webservice.zcw.com/", "getCollectedSampleList");
    private final static QName _GetSampleTypeList_QNAME = new QName("http://server.webservice.zcw.com/", "getSampleTypeList");
    private final static QName _GetDepartMentList_QNAME = new QName("http://server.webservice.zcw.com/", "getDepartMentList");
    private final static QName _GetDepartMentListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getDepartMentListResponse");
    private final static QName _GetReceivedSampleList_QNAME = new QName("http://server.webservice.zcw.com/", "getReceivedSampleList");
    private final static QName _GetTestPurposeListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getTestPurposeListResponse");
    private final static QName _GetCollectedSampleListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getCollectedSampleListResponse");
    private final static QName _GetSampleNoResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getSampleNoResponse");
    private final static QName _GetTestInfo_QNAME = new QName("http://server.webservice.zcw.com/", "getTestInfo");
    private final static QName _GetReceivedSampleListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getReceivedSampleListResponse");
    private final static QName _GetPatientTypeList_QNAME = new QName("http://server.webservice.zcw.com/", "getPatientTypeList");
    private final static QName _BookingResponse_QNAME = new QName("http://server.webservice.zcw.com/", "bookingResponse");
    private final static QName _ReturnSampleResponse_QNAME = new QName("http://server.webservice.zcw.com/", "returnSampleResponse");
    private final static QName _GetSampleNo_QNAME = new QName("http://server.webservice.zcw.com/", "getSampleNo");
    private final static QName _GetListTestResult_QNAME = new QName("http://server.webservice.zcw.com/", "getListTestResult");
    private final static QName _GetDrugList_QNAME = new QName("http://server.webservice.zcw.com/", "getDrugList");
    private final static QName _GetSampleTypeListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getSampleTypeListResponse");
    private final static QName _GetPatientTypeListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getPatientTypeListResponse");
    private final static QName _Booking_QNAME = new QName("http://server.webservice.zcw.com/", "booking");
    private final static QName _GetWardList_QNAME = new QName("http://server.webservice.zcw.com/", "getWardList");
    private final static QName _SaveTestResultResponse_QNAME = new QName("http://server.webservice.zcw.com/", "saveTestResultResponse");
    private final static QName _GetTestInfoResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getTestInfoResponse");
    private final static QName _GetTestPurposeList_QNAME = new QName("http://server.webservice.zcw.com/", "getTestPurposeList");
    private final static QName _SaveTestResult_QNAME = new QName("http://server.webservice.zcw.com/", "saveTestResult");
    private final static QName _GetWardListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getWardListResponse");
    private final static QName _GetDrugListResponse_QNAME = new QName("http://server.webservice.zcw.com/", "getDrugListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.smart.lisservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWardList }
     * 
     */
    public GetWardList createGetWardList() {
        return new GetWardList();
    }

    /**
     * Create an instance of {@link SaveTestResultResponse }
     * 
     */
    public SaveTestResultResponse createSaveTestResultResponse() {
        return new SaveTestResultResponse();
    }

    /**
     * Create an instance of {@link Booking }
     * 
     */
    public Booking createBooking() {
        return new Booking();
    }

    /**
     * Create an instance of {@link SaveTestResult }
     * 
     */
    public SaveTestResult createSaveTestResult() {
        return new SaveTestResult();
    }

    /**
     * Create an instance of {@link GetTestInfoResponse }
     * 
     */
    public GetTestInfoResponse createGetTestInfoResponse() {
        return new GetTestInfoResponse();
    }

    /**
     * Create an instance of {@link GetTestPurposeList }
     * 
     */
    public GetTestPurposeList createGetTestPurposeList() {
        return new GetTestPurposeList();
    }

    /**
     * Create an instance of {@link GetWardListResponse }
     * 
     */
    public GetWardListResponse createGetWardListResponse() {
        return new GetWardListResponse();
    }

    /**
     * Create an instance of {@link GetDrugListResponse }
     * 
     */
    public GetDrugListResponse createGetDrugListResponse() {
        return new GetDrugListResponse();
    }

    /**
     * Create an instance of {@link GetPatientTypeList }
     * 
     */
    public GetPatientTypeList createGetPatientTypeList() {
        return new GetPatientTypeList();
    }

    /**
     * Create an instance of {@link GetReceivedSampleListResponse }
     * 
     */
    public GetReceivedSampleListResponse createGetReceivedSampleListResponse() {
        return new GetReceivedSampleListResponse();
    }

    /**
     * Create an instance of {@link ReturnSampleResponse }
     * 
     */
    public ReturnSampleResponse createReturnSampleResponse() {
        return new ReturnSampleResponse();
    }

    /**
     * Create an instance of {@link BookingResponse }
     * 
     */
    public BookingResponse createBookingResponse() {
        return new BookingResponse();
    }

    /**
     * Create an instance of {@link GetDrugList }
     * 
     */
    public GetDrugList createGetDrugList() {
        return new GetDrugList();
    }

    /**
     * Create an instance of {@link GetSampleTypeListResponse }
     * 
     */
    public GetSampleTypeListResponse createGetSampleTypeListResponse() {
        return new GetSampleTypeListResponse();
    }

    /**
     * Create an instance of {@link GetListTestResult }
     * 
     */
    public GetListTestResult createGetListTestResult() {
        return new GetListTestResult();
    }

    /**
     * Create an instance of {@link GetSampleNo }
     * 
     */
    public GetSampleNo createGetSampleNo() {
        return new GetSampleNo();
    }

    /**
     * Create an instance of {@link GetPatientTypeListResponse }
     * 
     */
    public GetPatientTypeListResponse createGetPatientTypeListResponse() {
        return new GetPatientTypeListResponse();
    }

    /**
     * Create an instance of {@link GetBacteriaList }
     * 
     */
    public GetBacteriaList createGetBacteriaList() {
        return new GetBacteriaList();
    }

    /**
     * Create an instance of {@link SaveSampleFlowLogResponse }
     * 
     */
    public SaveSampleFlowLogResponse createSaveSampleFlowLogResponse() {
        return new SaveSampleFlowLogResponse();
    }

    /**
     * Create an instance of {@link SaveSampleFlowLog }
     * 
     */
    public SaveSampleFlowLog createSaveSampleFlowLog() {
        return new SaveSampleFlowLog();
    }

    /**
     * Create an instance of {@link GetCollectedSampleList }
     * 
     */
    public GetCollectedSampleList createGetCollectedSampleList() {
        return new GetCollectedSampleList();
    }

    /**
     * Create an instance of {@link GetPatientInfoListResponse }
     * 
     */
    public GetPatientInfoListResponse createGetPatientInfoListResponse() {
        return new GetPatientInfoListResponse();
    }

    /**
     * Create an instance of {@link GetBacteriaListResponse }
     * 
     */
    public GetBacteriaListResponse createGetBacteriaListResponse() {
        return new GetBacteriaListResponse();
    }

    /**
     * Create an instance of {@link GetDepartMentList }
     * 
     */
    public GetDepartMentList createGetDepartMentList() {
        return new GetDepartMentList();
    }

    /**
     * Create an instance of {@link GetSampleTypeList }
     * 
     */
    public GetSampleTypeList createGetSampleTypeList() {
        return new GetSampleTypeList();
    }

    /**
     * Create an instance of {@link GetSampleNoResponse }
     * 
     */
    public GetSampleNoResponse createGetSampleNoResponse() {
        return new GetSampleNoResponse();
    }

    /**
     * Create an instance of {@link GetTestInfo }
     * 
     */
    public GetTestInfo createGetTestInfo() {
        return new GetTestInfo();
    }

    /**
     * Create an instance of {@link GetCollectedSampleListResponse }
     * 
     */
    public GetCollectedSampleListResponse createGetCollectedSampleListResponse() {
        return new GetCollectedSampleListResponse();
    }

    /**
     * Create an instance of {@link GetDepartMentListResponse }
     * 
     */
    public GetDepartMentListResponse createGetDepartMentListResponse() {
        return new GetDepartMentListResponse();
    }

    /**
     * Create an instance of {@link GetReceivedSampleList }
     * 
     */
    public GetReceivedSampleList createGetReceivedSampleList() {
        return new GetReceivedSampleList();
    }

    /**
     * Create an instance of {@link GetTestPurposeListResponse }
     * 
     */
    public GetTestPurposeListResponse createGetTestPurposeListResponse() {
        return new GetTestPurposeListResponse();
    }

    /**
     * Create an instance of {@link GetListTestResultResponse }
     * 
     */
    public GetListTestResultResponse createGetListTestResultResponse() {
        return new GetListTestResultResponse();
    }

    /**
     * Create an instance of {@link GetPatientInfoList }
     * 
     */
    public GetPatientInfoList createGetPatientInfoList() {
        return new GetPatientInfoList();
    }

    /**
     * Create an instance of {@link ReturnSample }
     * 
     */
    public ReturnSample createReturnSample() {
        return new ReturnSample();
    }

    /**
     * Create an instance of {@link SampleLog }
     * 
     */
    public SampleLog createSampleLog() {
        return new SampleLog();
    }

    /**
     * Create an instance of {@link Report }
     * 
     */
    public Report createReport() {
        return new Report();
    }

    /**
     * Create an instance of {@link DrugResult }
     * 
     */
    public DrugResult createDrugResult() {
        return new DrugResult();
    }

    /**
     * Create an instance of {@link AccountItem }
     * 
     */
    public AccountItem createAccountItem() {
        return new AccountItem();
    }

    /**
     * Create an instance of {@link SampleInfo }
     * 
     */
    public SampleInfo createSampleInfo() {
        return new SampleInfo();
    }

    /**
     * Create an instance of {@link TestResult }
     * 
     */
    public TestResult createTestResult() {
        return new TestResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTestResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getListTestResultResponse")
    public JAXBElement<GetListTestResultResponse> createGetListTestResultResponse(GetListTestResultResponse value) {
        return new JAXBElement<GetListTestResultResponse>(_GetListTestResultResponse_QNAME, GetListTestResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPatientInfoList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getPatientInfoList")
    public JAXBElement<GetPatientInfoList> createGetPatientInfoList(GetPatientInfoList value) {
        return new JAXBElement<GetPatientInfoList>(_GetPatientInfoList_QNAME, GetPatientInfoList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnSample }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "returnSample")
    public JAXBElement<ReturnSample> createReturnSample(ReturnSample value) {
        return new JAXBElement<ReturnSample>(_ReturnSample_QNAME, ReturnSample.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveSampleFlowLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "saveSampleFlowLog")
    public JAXBElement<SaveSampleFlowLog> createSaveSampleFlowLog(SaveSampleFlowLog value) {
        return new JAXBElement<SaveSampleFlowLog>(_SaveSampleFlowLog_QNAME, SaveSampleFlowLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveSampleFlowLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "saveSampleFlowLogResponse")
    public JAXBElement<SaveSampleFlowLogResponse> createSaveSampleFlowLogResponse(SaveSampleFlowLogResponse value) {
        return new JAXBElement<SaveSampleFlowLogResponse>(_SaveSampleFlowLogResponse_QNAME, SaveSampleFlowLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBacteriaList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getBacteriaList")
    public JAXBElement<GetBacteriaList> createGetBacteriaList(GetBacteriaList value) {
        return new JAXBElement<GetBacteriaList>(_GetBacteriaList_QNAME, GetBacteriaList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBacteriaListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getBacteriaListResponse")
    public JAXBElement<GetBacteriaListResponse> createGetBacteriaListResponse(GetBacteriaListResponse value) {
        return new JAXBElement<GetBacteriaListResponse>(_GetBacteriaListResponse_QNAME, GetBacteriaListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPatientInfoListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getPatientInfoListResponse")
    public JAXBElement<GetPatientInfoListResponse> createGetPatientInfoListResponse(GetPatientInfoListResponse value) {
        return new JAXBElement<GetPatientInfoListResponse>(_GetPatientInfoListResponse_QNAME, GetPatientInfoListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCollectedSampleList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getCollectedSampleList")
    public JAXBElement<GetCollectedSampleList> createGetCollectedSampleList(GetCollectedSampleList value) {
        return new JAXBElement<GetCollectedSampleList>(_GetCollectedSampleList_QNAME, GetCollectedSampleList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSampleTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getSampleTypeList")
    public JAXBElement<GetSampleTypeList> createGetSampleTypeList(GetSampleTypeList value) {
        return new JAXBElement<GetSampleTypeList>(_GetSampleTypeList_QNAME, GetSampleTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartMentList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getDepartMentList")
    public JAXBElement<GetDepartMentList> createGetDepartMentList(GetDepartMentList value) {
        return new JAXBElement<GetDepartMentList>(_GetDepartMentList_QNAME, GetDepartMentList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDepartMentListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getDepartMentListResponse")
    public JAXBElement<GetDepartMentListResponse> createGetDepartMentListResponse(GetDepartMentListResponse value) {
        return new JAXBElement<GetDepartMentListResponse>(_GetDepartMentListResponse_QNAME, GetDepartMentListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReceivedSampleList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getReceivedSampleList")
    public JAXBElement<GetReceivedSampleList> createGetReceivedSampleList(GetReceivedSampleList value) {
        return new JAXBElement<GetReceivedSampleList>(_GetReceivedSampleList_QNAME, GetReceivedSampleList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTestPurposeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getTestPurposeListResponse")
    public JAXBElement<GetTestPurposeListResponse> createGetTestPurposeListResponse(GetTestPurposeListResponse value) {
        return new JAXBElement<GetTestPurposeListResponse>(_GetTestPurposeListResponse_QNAME, GetTestPurposeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCollectedSampleListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getCollectedSampleListResponse")
    public JAXBElement<GetCollectedSampleListResponse> createGetCollectedSampleListResponse(GetCollectedSampleListResponse value) {
        return new JAXBElement<GetCollectedSampleListResponse>(_GetCollectedSampleListResponse_QNAME, GetCollectedSampleListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSampleNoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getSampleNoResponse")
    public JAXBElement<GetSampleNoResponse> createGetSampleNoResponse(GetSampleNoResponse value) {
        return new JAXBElement<GetSampleNoResponse>(_GetSampleNoResponse_QNAME, GetSampleNoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTestInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getTestInfo")
    public JAXBElement<GetTestInfo> createGetTestInfo(GetTestInfo value) {
        return new JAXBElement<GetTestInfo>(_GetTestInfo_QNAME, GetTestInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReceivedSampleListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getReceivedSampleListResponse")
    public JAXBElement<GetReceivedSampleListResponse> createGetReceivedSampleListResponse(GetReceivedSampleListResponse value) {
        return new JAXBElement<GetReceivedSampleListResponse>(_GetReceivedSampleListResponse_QNAME, GetReceivedSampleListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPatientTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getPatientTypeList")
    public JAXBElement<GetPatientTypeList> createGetPatientTypeList(GetPatientTypeList value) {
        return new JAXBElement<GetPatientTypeList>(_GetPatientTypeList_QNAME, GetPatientTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "bookingResponse")
    public JAXBElement<BookingResponse> createBookingResponse(BookingResponse value) {
        return new JAXBElement<BookingResponse>(_BookingResponse_QNAME, BookingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnSampleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "returnSampleResponse")
    public JAXBElement<ReturnSampleResponse> createReturnSampleResponse(ReturnSampleResponse value) {
        return new JAXBElement<ReturnSampleResponse>(_ReturnSampleResponse_QNAME, ReturnSampleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSampleNo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getSampleNo")
    public JAXBElement<GetSampleNo> createGetSampleNo(GetSampleNo value) {
        return new JAXBElement<GetSampleNo>(_GetSampleNo_QNAME, GetSampleNo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTestResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getListTestResult")
    public JAXBElement<GetListTestResult> createGetListTestResult(GetListTestResult value) {
        return new JAXBElement<GetListTestResult>(_GetListTestResult_QNAME, GetListTestResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDrugList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getDrugList")
    public JAXBElement<GetDrugList> createGetDrugList(GetDrugList value) {
        return new JAXBElement<GetDrugList>(_GetDrugList_QNAME, GetDrugList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSampleTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getSampleTypeListResponse")
    public JAXBElement<GetSampleTypeListResponse> createGetSampleTypeListResponse(GetSampleTypeListResponse value) {
        return new JAXBElement<GetSampleTypeListResponse>(_GetSampleTypeListResponse_QNAME, GetSampleTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPatientTypeListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getPatientTypeListResponse")
    public JAXBElement<GetPatientTypeListResponse> createGetPatientTypeListResponse(GetPatientTypeListResponse value) {
        return new JAXBElement<GetPatientTypeListResponse>(_GetPatientTypeListResponse_QNAME, GetPatientTypeListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "booking")
    public JAXBElement<Booking> createBooking(Booking value) {
        return new JAXBElement<Booking>(_Booking_QNAME, Booking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWardList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getWardList")
    public JAXBElement<GetWardList> createGetWardList(GetWardList value) {
        return new JAXBElement<GetWardList>(_GetWardList_QNAME, GetWardList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveTestResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "saveTestResultResponse")
    public JAXBElement<SaveTestResultResponse> createSaveTestResultResponse(SaveTestResultResponse value) {
        return new JAXBElement<SaveTestResultResponse>(_SaveTestResultResponse_QNAME, SaveTestResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTestInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getTestInfoResponse")
    public JAXBElement<GetTestInfoResponse> createGetTestInfoResponse(GetTestInfoResponse value) {
        return new JAXBElement<GetTestInfoResponse>(_GetTestInfoResponse_QNAME, GetTestInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTestPurposeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getTestPurposeList")
    public JAXBElement<GetTestPurposeList> createGetTestPurposeList(GetTestPurposeList value) {
        return new JAXBElement<GetTestPurposeList>(_GetTestPurposeList_QNAME, GetTestPurposeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveTestResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "saveTestResult")
    public JAXBElement<SaveTestResult> createSaveTestResult(SaveTestResult value) {
        return new JAXBElement<SaveTestResult>(_SaveTestResult_QNAME, SaveTestResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWardListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getWardListResponse")
    public JAXBElement<GetWardListResponse> createGetWardListResponse(GetWardListResponse value) {
        return new JAXBElement<GetWardListResponse>(_GetWardListResponse_QNAME, GetWardListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDrugListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webservice.zcw.com/", name = "getDrugListResponse")
    public JAXBElement<GetDrugListResponse> createGetDrugListResponse(GetDrugListResponse value) {
        return new JAXBElement<GetDrugListResponse>(_GetDrugListResponse_QNAME, GetDrugListResponse.class, null, value);
    }

}
