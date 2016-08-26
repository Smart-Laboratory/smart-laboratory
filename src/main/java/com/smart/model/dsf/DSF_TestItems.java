package com.smart.model.dsf;

import com.smart.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zjn on 2016/8/17.
 */
@Entity
@Table(name = "dsf_testitems")
public class DSF_TestItems extends BaseObject implements Serializable {

    // Primary Key
    private Long id;
    private String indexId;
    private String english;
    private String name;
    private String sampleFrom;
    private String unit;
    private String type;
    private int diffAlgo;
    private String description;
    private String enumData;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;
    private String importance;
    private String knowledgename;
    private int isprint;
    private String printord;
    private int needhistory;
    private String method;
    private String defaultvalue;
    private String dictionaries;
    private String customerid;

    private String guide;
    private String labdepartment;    //关联部门
    private String instrument;        //关联仪器

    /*不常用信息*/
    private String principle;            //测定原理
    private String workcriterion;        //操作规范
    private String increasedHint;        //升高意义
    private String decreasedHint;        //降低意义
    private String notes;                //注意事项
    private String methodName;            //方法名称


    /*常用信息*/
    private Date inureDate;                //生效日期
    private Date outDate;                //失效日期
    private String outDateOperator;        //失效人
    private String TEA;
    private String CCV;
    private String testClass;            //分类代码
    private Integer qcNeed;                //质控需求

    public DSF_TestItems() {
    }

    /**
     * 主键、自增
     */
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DSF_TestItems")
    @SequenceGenerator(name = "SEQ_DSF_TestItems", sequenceName = "dsf_TestItems_sequence", allocationSize = 1)
    @DocumentId
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 医院对应的项目id
     */
    @Column(name = "index_id", nullable = false, length = 10, unique = true)
    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    /**
     * 检验项目名称
     */
    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 检验项目英文项目
     */
    @Column(name = "english", length = 20)
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * 检验项目样本来源
     */
    @Column(name = "sample_from", length = 20)
    public String getSampleFrom() {
        return sampleFrom;
    }

    public void setSampleFrom(String sampleFrom) {
        this.sampleFrom = sampleFrom;
    }

    /**
     * 检验项目单位
     */
    @Column(length = 20)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 检验项目类型，0：枚举型；1：数字型；2：字符型；
     */
    @Column(length = 2)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 检验项目的差值算法。1：差值；2：差值百分率；3：差值变化；4：差值变化率
     */
    @Column(name = "algorithm")
    public int getDiffAlgo() {
        return diffAlgo;
    }

    public void setDiffAlgo(int diffAlgo) {
        this.diffAlgo = diffAlgo;
    }

    /**
     * 检验项目描述
     */
    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 枚举数据
     */
    @Column(name = "enum_data")
    public String getEnumData() {
        return enumData;
    }

    public void setEnumData(String enumData) {
        this.enumData = enumData;
    }

    /**
     * 指标创建者
     */
    @Column(name = "create_user", nullable = true)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 指标创建时间
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 指标修改者
     */
    @Column(name = "modify_user")
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * 指标修改时间
     */
    @Column(name = "modify_time")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 指标重要性
     */
    @Column(name = "importance")
    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    /**
     * 知识库名称
     */
    @Column(name = "knowledgename")
    public String getKnowledgename() {
        return knowledgename;
    }

    public void setKnowledgename(String knowledgename) {
        this.knowledgename = knowledgename;
    }

    /**
     * 指标是否打印
     */
    @Column(name = "isprint")
    public int getIsprint() {
        return isprint;
    }

    public void setIsprint(int isprint) {
        this.isprint = isprint;
    }


    /**
     * 指标打印顺序
     */
    @Column(name = "printord")
    public String getPrintord() {
        return printord;
    }

    public void setPrintord(String printord) {
        this.printord = printord;
    }

    @Column
    public int getNeedhistory() {
        return needhistory;
    }

    public void setNeedhistory(int needhistory) {
        this.needhistory = needhistory;
    }

    @Column
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Column
    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    @Column
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @Column
    public String getLabdepartment() {
        return labdepartment;
    }

    public void setLabdepartment(String labdepartment) {
        this.labdepartment = labdepartment;
    }

    @Column
    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String toString() {
        return null;
    }

    public boolean equals(Object o) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @Transient
    public String getAlgorithm() {

        String algorithm = "";

        switch (this.getDiffAlgo()) {
            case 1:
                algorithm = "差值";
                break;
            case 2:
                algorithm = "差值百分率";
                break;
            case 3:
                algorithm = "差值变化";
                break;
            case 4:
                algorithm = "差值变化率";
                break;
            default:
                algorithm = "差值百分率";
                break;
        }
        return algorithm;
    }
    @Column
    public String getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(String dictionaries) {
        this.dictionaries = dictionaries;
    }
    @Column
    public Integer getQcNeed() {
        return qcNeed;
    }

    public void setQcNeed(Integer qcNeed) {
        this.qcNeed = qcNeed;
    }
    @Column
    public Date getInureDate() {
        return inureDate;
    }

    public void setInureDate(Date inureDate) {
        this.inureDate = inureDate;
    }
    @Column
    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }
    @Column
    public String getOutDateOperator() {
        return outDateOperator;
    }

    public void setOutDateOperator(String outDateOperator) {
        this.outDateOperator = outDateOperator;
    }
    @Column
    public String getTEA() {
        return TEA;
    }

    public void setTEA(String TEA) {
        this.TEA = TEA;
    }
    @Column
    public String getCCV() {
        return CCV;
    }

    public void setCCV(String CCV) {
        this.CCV = CCV;
    }
    @Column
    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getWorkcriterion() {
        return workcriterion;
    }

    public void setWorkcriterion(String workcriterion) {
        this.workcriterion = workcriterion;
    }

    public String getIncreasedHint() {
        return increasedHint;
    }

    public void setIncreasedHint(String increasedHint) {
        this.increasedHint = increasedHint;
    }

    public String getDecreasedHint() {
        return decreasedHint;
    }

    public void setDecreasedHint(String decreasedHint) {
        this.decreasedHint = decreasedHint;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    @Transient
    public String getHistory() {
        return this.needhistory == 1 ? "是" : "否";
    }
}