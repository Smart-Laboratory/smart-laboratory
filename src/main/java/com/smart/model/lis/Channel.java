package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * Title: Channel
 * Description: 仪器通道Model
 *
 * @Author:zhou
 * @Date:2016/6/2 8:49
 * @Version:
 */
@Entity
@IdClass(ChannelPK.class)
@Table(name = "lab_channel")
public class Channel implements Serializable {
    private static final long serialVersionUID = 3142043106827517693L;

    @Id
    @Column(name = "deviceid")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Column
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Id
    @Column(name = "testid")
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    private String deviceId;        //仪器ID
    private String channel;         //仪器通道
    private String testId;          //检验项目ID
    private String sampleType;      //标本类型
    //待定，表什么意思？
    //private String yqTestId;

}
