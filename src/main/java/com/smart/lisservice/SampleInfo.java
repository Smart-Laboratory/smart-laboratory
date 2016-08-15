
package com.smart.lisservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>sampleInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取age属性的值。
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
     * 设置age属性的值。
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
     * 获取ageType属性的值。
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
     * 设置ageType属性的值。
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
     * 获取auditDoctor属性的值。
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
     * 设置auditDoctor属性的值。
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
     * 获取auditDoctorCode属性的值。
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
     * 设置auditDoctorCode属性的值。
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
     * 获取auditTime属性的值。
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
     * 设置auditTime属性的值。
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
     * 获取barcode属性的值。
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
     * 设置barcode属性的值。
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
     * 获取bedNo属性的值。
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
     * 设置bedNo属性的值。
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
     * 获取billDepartment属性的值。
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
     * 设置billDepartment属性的值。
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
     * 获取billDepartmentCode属性的值。
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
     * 设置billDepartmentCode属性的值。
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
     * 获取birthday属性的值。
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
     * 设置birthday属性的值。
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
     * 获取clinicalDiagnosis属性的值。
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
     * 设置clinicalDiagnosis属性的值。
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
     * 获取count属性的值。
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
     * 设置count属性的值。
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
     * 获取createTime属性的值。
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
     * 设置createTime属性的值。
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
     * 获取cycle属性的值。
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
     * 设置cycle属性的值。
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
     * 获取department属性的值。
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
     * 设置department属性的值。
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
     * 获取departmentCode属性的值。
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
     * 设置departmentCode属性的值。
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
     * 获取deviceId属性的值。
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
     * 设置deviceId属性的值。
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
     * 获取fee属性的值。
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
     * 设置fee属性的值。
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
     * 获取feeStatus属性的值。
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
     * 设置feeStatus属性的值。
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
     * 获取hosSection属性的值。
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
     * 设置hosSection属性的值。
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
     * 获取hosSectionCode属性的值。
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
     * 设置hosSectionCode属性的值。
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
     * 获取inspectCode属性的值。
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
     * 设置inspectCode属性的值。
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
     * 获取inspectDoctor属性的值。
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
     * 设置inspectDoctor属性的值。
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
     * 获取invoiceNum属性的值。
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
     * 设置invoiceNum属性的值。
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
     * 获取operateTime属性的值。
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
     * 设置operateTime属性的值。
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
     * 获取operator属性的值。
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
     * 设置operator属性的值。
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
     * 获取operatorCode属性的值。
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
     * 设置operatorCode属性的值。
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
     * 获取paperSize属性的值。
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
     * 设置paperSize属性的值。
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
     * 获取patientCode属性的值。
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
     * 设置patientCode属性的值。
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
     * 获取patientFileNo属性的值。
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
     * 设置patientFileNo属性的值。
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
     * 获取patientId属性的值。
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
     * 设置patientId属性的值。
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
     * 获取patientName属性的值。
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
     * 设置patientName属性的值。
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
     * 获取patientPhone属性的值。
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
     * 设置patientPhone属性的值。
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
     * 获取reportDateTime属性的值。
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
     * 设置reportDateTime属性的值。
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
     * 获取reportDoctor属性的值。
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
     * 设置reportDoctor属性的值。
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
     * 获取reportDoctorCode属性的值。
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
     * 设置reportDoctorCode属性的值。
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
     * 获取requestmode属性的值。
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
     * 设置requestmode属性的值。
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
     * 获取sampleDescription属性的值。
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
     * 设置sampleDescription属性的值。
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
     * 获取sampleId属性的值。
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
     * 设置sampleId属性的值。
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
     * 获取sampleNote属性的值。
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
     * 设置sampleNote属性的值。
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
     * 获取sampleStatus属性的值。
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
     * 设置sampleStatus属性的值。
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
     * 获取sampleType属性的值。
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
     * 设置sampleType属性的值。
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
     * 获取sampleTypeCode属性的值。
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
     * 设置sampleTypeCode属性的值。
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
     * 获取samplingPart属性的值。
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
     * 设置samplingPart属性的值。
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
     * 获取samplingTime属性的值。
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
     * 设置samplingTime属性的值。
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
     * 获取section属性的值。
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
     * 设置section属性的值。
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
     * 获取sectionId属性的值。
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
     * 设置sectionId属性的值。
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
     * 获取sex属性的值。
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
     * 设置sex属性的值。
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
     * 获取testDateTime属性的值。
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
     * 设置testDateTime属性的值。
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
     * 获取testDestinationName属性的值。
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
     * 设置testDestinationName属性的值。
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
     * 获取testDestinationNo属性的值。
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
     * 设置testDestinationNo属性的值。
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
     * 获取testDoctor属性的值。
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
     * 设置testDoctor属性的值。
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
     * 获取testDoctorCode属性的值。
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
     * 设置testDoctorCode属性的值。
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
