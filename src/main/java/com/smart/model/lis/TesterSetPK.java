package com.smart.model.lis;

import java.io.Serializable;

/**
 * Created by yuzh on 2016/9/23.
 * 检验者设置组合主键
 */
public class TesterSetPK implements Serializable {

    public TesterSetPK() {
    }

    public TesterSetPK(String segment, String deviceId) {
        this.segment = segment;
        this.deviceId = deviceId;
    }

    private String segment;
    private String deviceId;

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TesterSetPK testerSetPK = (TesterSetPK) o;

        if (!segment.equals(testerSetPK.segment)) return false;
        return deviceId.equals(testerSetPK.deviceId);

    }

    public int hashCode() {
        int result = segment.hashCode();
        result = 31 * result + deviceId.hashCode();
        return result;
    }
}
