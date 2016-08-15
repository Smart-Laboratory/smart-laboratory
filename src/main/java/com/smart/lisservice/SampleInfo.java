
package com.smart.lisservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>sampleInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="sampleInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditDoctor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditDoctorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="auditTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bedNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billDepartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billDepartmentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clinicalDiagnosis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="cycle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departmentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fee" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="feeStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hosSection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hosSectionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inspectCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inspectDoctor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="operator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="paperSize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientFileNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="patientPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="reportDoctor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportDoctorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestmode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="samplingPart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="samplingTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="section" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sectionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="testDestinationName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testDestinationNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testDoctor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testDoctorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sampleInfo", propOrder = {
    "age",
    "ageType",
    "auditDoctor",
    "auditDoctorCode",
    "auditTime",
    "barcode",
    "bedNo",
    "billDepartment",
    "billDepartmentCode",
    "birthday",
    "clinicalDiagnosis",
    "count",
    "createTime",
    "cycle",
    "department",
    "departmentCode",
    "deviceId",
    "fee",
    "feeStatus",
    "hosSection",
    "hosSectionCode",
    "inspectCode",
    "inspectDoctor",
    "invoiceNum",
    "operateTime",
    "operator",
    "operatorCode",
    "paperSize",
    "patientCode",
    "patientFileNo",
    "patientId",
    "patientName",
    "patientPhone",
    "reportDateTime",
    "reportDoctor",
    "reportDoctorCode",
    "requestmode",
    "sampleDescription",
    "sampleId",
    "sampleNote",
    "sampleStatus",
    "sampleType",
    "sampleTypeCode",
    "samplingPart",
    "samplingTime",
    "section",
    "sectionId",
    "sex",
    "testDateTime",
    "testDestinationName",
    "testDestinationNo",
    "testDoctor",
    "testDoctorCode"
})
public class SampleInfo {

    protected String age;
    protected String ageType;
    protected String auditDoctor;
    protected String auditDoctorCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar auditTime;
    protected String barcode;
    protected String bedNo;
    protected String billDepartment;
    protected String billDepartmentCode;
    protected String birthday;
    protected String clinicalDiagnosis;
    protected String count;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createTime;
    protected String cycle;
    protected String department;
    protected String departmentCode;
    protected String deviceId;
    protected Double fee;
    protected String feeStatus;
    protected String hosSection;
    protected String hosSectionCode;
    protected String inspectCode;
    protected String inspectDoctor;
    protected String invoiceNum;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operateTime;
    protected String operator;
    protected String operatorCode;
    protected String paperSize;
    protected String patientCode;
    protected String patientFileNo;
    protected String patientId;
    protected String patientName;
    protected String patientPhone;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reportDateTime;
    protected String reportDoctor;
    protected String reportDoctorCode;
    protected String requestmode;
    protected String sampleDescription;
    protected String sampleId;
    protected String sampleNote;
    protected String sampleStatus;
    protected String sampleType;
    protected String sampleTypeCode;
    protected String samplingPart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar samplingTime;
    protected String section;
    protected String sectionId;
    protected String sex;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar testDateTime;
    protected String testDestinationName;
    protected String testDestinationNo;
    protected String testDoctor;
    protected String testDoctorCode;

    /**
     * ��ȡage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAge() {
        return age;
    }

    /**
     * ����age���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAge(String value) {
        this.age = value;
    }

    /**
     * ��ȡageType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgeType() {
        return ageType;
    }

    /**
     * ����ageType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgeType(String value) {
        this.ageType = value;
    }

    /**
     * ��ȡauditDoctor���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditDoctor() {
        return auditDoctor;
    }

    /**
     * ����auditDoctor���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditDoctor(String value) {
        this.auditDoctor = value;
    }

    /**
     * ��ȡauditDoctorCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditDoctorCode() {
        return auditDoctorCode;
    }

    /**
     * ����auditDoctorCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditDoctorCode(String value) {
        this.auditDoctorCode = value;
    }

    /**
     * ��ȡauditTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuditTime() {
        return auditTime;
    }

    /**
     * ����auditTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuditTime(XMLGregorianCalendar value) {
        this.auditTime = value;
    }

    /**
     * ��ȡbarcode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * ����barcode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcode(String value) {
        this.barcode = value;
    }

    /**
     * ��ȡbedNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedNo() {
        return bedNo;
    }

    /**
     * ����bedNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedNo(String value) {
        this.bedNo = value;
    }

    /**
     * ��ȡbillDepartment���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillDepartment() {
        return billDepartment;
    }

    /**
     * ����billDepartment���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillDepartment(String value) {
        this.billDepartment = value;
    }

    /**
     * ��ȡbillDepartmentCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillDepartmentCode() {
        return billDepartmentCode;
    }

    /**
     * ����billDepartmentCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillDepartmentCode(String value) {
        this.billDepartmentCode = value;
    }

    /**
     * ��ȡbirthday���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * ����birthday���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(String value) {
        this.birthday = value;
    }

    /**
     * ��ȡclinicalDiagnosis���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    /**
     * ����clinicalDiagnosis���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClinicalDiagnosis(String value) {
        this.clinicalDiagnosis = value;
    }

    /**
     * ��ȡcount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * ����count���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * ��ȡcreateTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateTime() {
        return createTime;
    }

    /**
     * ����createTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateTime(XMLGregorianCalendar value) {
        this.createTime = value;
    }

    /**
     * ��ȡcycle���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCycle() {
        return cycle;
    }

    /**
     * ����cycle���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCycle(String value) {
        this.cycle = value;
    }

    /**
     * ��ȡdepartment���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * ����department���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * ��ȡdepartmentCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    /**
     * ����departmentCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartmentCode(String value) {
        this.departmentCode = value;
    }

    /**
     * ��ȡdeviceId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * ����deviceId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * ��ȡfee���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFee() {
        return fee;
    }

    /**
     * ����fee���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFee(Double value) {
        this.fee = value;
    }

    /**
     * ��ȡfeeStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeStatus() {
        return feeStatus;
    }

    /**
     * ����feeStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeStatus(String value) {
        this.feeStatus = value;
    }

    /**
     * ��ȡhosSection���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHosSection() {
        return hosSection;
    }

    /**
     * ����hosSection���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHosSection(String value) {
        this.hosSection = value;
    }

    /**
     * ��ȡhosSectionCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHosSectionCode() {
        return hosSectionCode;
    }

    /**
     * ����hosSectionCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHosSectionCode(String value) {
        this.hosSectionCode = value;
    }

    /**
     * ��ȡinspectCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectCode() {
        return inspectCode;
    }

    /**
     * ����inspectCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectCode(String value) {
        this.inspectCode = value;
    }

    /**
     * ��ȡinspectDoctor���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectDoctor() {
        return inspectDoctor;
    }

    /**
     * ����inspectDoctor���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectDoctor(String value) {
        this.inspectDoctor = value;
    }

    /**
     * ��ȡinvoiceNum���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNum() {
        return invoiceNum;
    }

    /**
     * ����invoiceNum���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNum(String value) {
        this.invoiceNum = value;
    }

    /**
     * ��ȡoperateTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperateTime() {
        return operateTime;
    }

    /**
     * ����operateTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperateTime(XMLGregorianCalendar value) {
        this.operateTime = value;
    }

    /**
     * ��ȡoperator���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * ����operator���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * ��ȡoperatorCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * ����operatorCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorCode(String value) {
        this.operatorCode = value;
    }

    /**
     * ��ȡpaperSize���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaperSize() {
        return paperSize;
    }

    /**
     * ����paperSize���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaperSize(String value) {
        this.paperSize = value;
    }

    /**
     * ��ȡpatientCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientCode() {
        return patientCode;
    }

    /**
     * ����patientCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientCode(String value) {
        this.patientCode = value;
    }

    /**
     * ��ȡpatientFileNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientFileNo() {
        return patientFileNo;
    }

    /**
     * ����patientFileNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientFileNo(String value) {
        this.patientFileNo = value;
    }

    /**
     * ��ȡpatientId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * ����patientId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * ��ȡpatientName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * ����patientName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientName(String value) {
        this.patientName = value;
    }

    /**
     * ��ȡpatientPhone���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientPhone() {
        return patientPhone;
    }

    /**
     * ����patientPhone���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientPhone(String value) {
        this.patientPhone = value;
    }

    /**
     * ��ȡreportDateTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportDateTime() {
        return reportDateTime;
    }

    /**
     * ����reportDateTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportDateTime(XMLGregorianCalendar value) {
        this.reportDateTime = value;
    }

    /**
     * ��ȡreportDoctor���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportDoctor() {
        return reportDoctor;
    }

    /**
     * ����reportDoctor���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportDoctor(String value) {
        this.reportDoctor = value;
    }

    /**
     * ��ȡreportDoctorCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportDoctorCode() {
        return reportDoctorCode;
    }

    /**
     * ����reportDoctorCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportDoctorCode(String value) {
        this.reportDoctorCode = value;
    }

    /**
     * ��ȡrequestmode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestmode() {
        return requestmode;
    }

    /**
     * ����requestmode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestmode(String value) {
        this.requestmode = value;
    }

    /**
     * ��ȡsampleDescription���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleDescription() {
        return sampleDescription;
    }

    /**
     * ����sampleDescription���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleDescription(String value) {
        this.sampleDescription = value;
    }

    /**
     * ��ȡsampleId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleId() {
        return sampleId;
    }

    /**
     * ����sampleId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleId(String value) {
        this.sampleId = value;
    }

    /**
     * ��ȡsampleNote���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleNote() {
        return sampleNote;
    }

    /**
     * ����sampleNote���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleNote(String value) {
        this.sampleNote = value;
    }

    /**
     * ��ȡsampleStatus���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleStatus() {
        return sampleStatus;
    }

    /**
     * ����sampleStatus���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleStatus(String value) {
        this.sampleStatus = value;
    }

    /**
     * ��ȡsampleType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * ����sampleType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleType(String value) {
        this.sampleType = value;
    }

    /**
     * ��ȡsampleTypeCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleTypeCode() {
        return sampleTypeCode;
    }

    /**
     * ����sampleTypeCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleTypeCode(String value) {
        this.sampleTypeCode = value;
    }

    /**
     * ��ȡsamplingPart���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSamplingPart() {
        return samplingPart;
    }

    /**
     * ����samplingPart���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSamplingPart(String value) {
        this.samplingPart = value;
    }

    /**
     * ��ȡsamplingTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSamplingTime() {
        return samplingTime;
    }

    /**
     * ����samplingTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSamplingTime(XMLGregorianCalendar value) {
        this.samplingTime = value;
    }

    /**
     * ��ȡsection���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSection() {
        return section;
    }

    /**
     * ����section���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSection(String value) {
        this.section = value;
    }

    /**
     * ��ȡsectionId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionId() {
        return sectionId;
    }

    /**
     * ����sectionId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionId(String value) {
        this.sectionId = value;
    }

    /**
     * ��ȡsex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * ����sex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * ��ȡtestDateTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTestDateTime() {
        return testDateTime;
    }

    /**
     * ����testDateTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTestDateTime(XMLGregorianCalendar value) {
        this.testDateTime = value;
    }

    /**
     * ��ȡtestDestinationName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestDestinationName() {
        return testDestinationName;
    }

    /**
     * ����testDestinationName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestDestinationName(String value) {
        this.testDestinationName = value;
    }

    /**
     * ��ȡtestDestinationNo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestDestinationNo() {
        return testDestinationNo;
    }

    /**
     * ����testDestinationNo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestDestinationNo(String value) {
        this.testDestinationNo = value;
    }

    /**
     * ��ȡtestDoctor���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestDoctor() {
        return testDoctor;
    }

    /**
     * ����testDoctor���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestDoctor(String value) {
        this.testDoctor = value;
    }

    /**
     * ��ȡtestDoctorCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestDoctorCode() {
        return testDoctorCode;
    }

    /**
     * ����testDoctorCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestDoctorCode(String value) {
        this.testDoctorCode = value;
    }

}
