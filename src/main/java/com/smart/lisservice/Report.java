
package com.smart.lisservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>report complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="report">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="drugResults" type="{http://server.webservice.zcw.com/}drugResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reportType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="results" type="{http://server.webservice.zcw.com/}testResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sampleInfo" type="{http://server.webservice.zcw.com/}sampleInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "report", propOrder = {
    "drugResults",
    "reportType",
    "results",
    "sampleInfo"
})
public class Report {

    @XmlElement(nillable = true)
    protected List<DrugResult> drugResults;
    protected int reportType;
    @XmlElement(nillable = true)
    protected List<TestResult> results;
    protected SampleInfo sampleInfo;

    /**
     * Gets the value of the drugResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drugResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrugResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrugResult }
     * 
     * 
     */
    public List<DrugResult> getDrugResults() {
        if (drugResults == null) {
            drugResults = new ArrayList<DrugResult>();
        }
        return this.drugResults;
    }

    /**
     * 获取reportType属性的值。
     * 
     */
    public int getReportType() {
        return reportType;
    }

    /**
     * 设置reportType属性的值。
     * 
     */
    public void setReportType(int value) {
        this.reportType = value;
    }

    /**
     * Gets the value of the results property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the results property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestResult }
     * 
     * 
     */
    public List<TestResult> getResults() {
        if (results == null) {
            results = new ArrayList<TestResult>();
        }
        return this.results;
    }

    /**
     * 获取sampleInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SampleInfo }
     *     
     */
    public SampleInfo getSampleInfo() {
        return sampleInfo;
    }

    /**
     * 设置sampleInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SampleInfo }
     *     
     */
    public void setSampleInfo(SampleInfo value) {
        this.sampleInfo = value;
    }

}
