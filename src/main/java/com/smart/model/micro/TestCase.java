package com.smart.model.micro;

import javax.persistence.*;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 16:26
 * @Version:
 */
@Entity
@Table(name = "LAB_MICRO_TESTCASE")
public class TestCase {
    private Long id;
    private String testId;
    private int lablenumber;
    private int reportDays;
    private int autoReportDays;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TESTCASE")
    @SequenceGenerator(name = "SEQ_TESTCASE", sequenceName = "testcase_sequences", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getLablenumber() {
        return lablenumber;
    }

    public void setLablenumber(int lablenumber) {
        this.lablenumber = lablenumber;
    }

    public int getReportDays() {
        return reportDays;
    }

    public void setReportDays(int reportDays) {
        this.reportDays = reportDays;
    }

    public int getAutoReportDays() {
        return autoReportDays;
    }

    public void setAutoReportDays(int autoReportDays) {
        this.autoReportDays = autoReportDays;
    }
}
