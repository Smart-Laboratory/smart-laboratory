package com.smart.model.lis;

import java.io.Serializable;

/**
 * Title: ChannelPK
 * Description:
 *
 * @Author:zhou
 * @Date:2016/6/3 14:48
 * @Version:
 */

public class ChannelPK implements Serializable {


    private static final long serialVersionUID = 7681037876611533953L;

    public ChannelPK(){}

    public ChannelPK(String testId,String deviceId){
        this.deviceId = deviceId;
        this.testId = testId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChannelPK other = (ChannelPK) obj;
        if (deviceId == null) {
            if (other.deviceId != null)
                return false;
        } else if (!deviceId.equals(other.deviceId))
            return false;
        if (testId == null) {
            if (other.testId != null)
                return false;
        } else if (!testId.equals(other.testId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        result = prime * result + ((testId == null) ? 0 : testId.hashCode());
        return result;
    }
    private String testId;
    private String deviceId;
}

