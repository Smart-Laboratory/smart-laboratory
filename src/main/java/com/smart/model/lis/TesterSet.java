package com.smart.model.lis;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yuzh on 2016/9/23.
 * 检验者设置信息
 *
 */
@Entity
@IdClass(TesterSetPK.class)
@Table(name = "l_tester_set")
public class TesterSet {

    private String deviceId;
    private String segment;
    private String tester;
    private Date setTime;
    private String lab;

    @Id
    @Column
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Id
    @Column
    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    @Column
    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    @Column
    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    @Column
    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }
}
