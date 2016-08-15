
package com.smart.lisservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>testResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="testResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abnormalFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="drugResistance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resultTypeId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="testItemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testItemOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testResult", propOrder = {
    "abnormalFlag",
    "code",
    "count",
    "drugResistance",
    "reference",
    "result",
    "resultType",
    "resultTypeId",
    "testItemCode",
    "testItemOrder",
    "unit"
})
public class TestResult {

    protected String abnormalFlag;
    protected String code;
    protected String count;
    protected String drugResistance;
    protected String reference;
    protected String result;
    protected String resultType;
    protected int resultTypeId;
    protected String testItemCode;
    protected String testItemOrder;
    protected String unit;

    /**
     * 获取abnormalFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbnormalFlag() {
        return abnormalFlag;
    }

    /**
     * 设置abnormalFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbnormalFlag(String value) {
        this.abnormalFlag = value;
    }

    /**
     * 获取code属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * 获取drugResistance属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrugResistance() {
        return drugResistance;
    }

    /**
     * 设置drugResistance属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrugResistance(String value) {
        this.drugResistance = value;
    }

    /**
     * 获取reference属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * 设置reference属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * 获取result属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置result属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * 获取resultType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * 设置resultType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultType(String value) {
        this.resultType = value;
    }

    /**
     * 获取resultTypeId属性的值。
     * 
     */
    public int getResultTypeId() {
        return resultTypeId;
    }

    /**
     * 设置resultTypeId属性的值。
     * 
     */
    public void setResultTypeId(int value) {
        this.resultTypeId = value;
    }

    /**
     * 获取testItemCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestItemCode() {
        return testItemCode;
    }

    /**
     * 设置testItemCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestItemCode(String value) {
        this.testItemCode = value;
    }

    /**
     * 获取testItemOrder属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestItemOrder() {
        return testItemOrder;
    }

    /**
     * 设置testItemOrder属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestItemOrder(String value) {
        this.testItemOrder = value;
    }

    /**
     * 获取unit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置unit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

}
