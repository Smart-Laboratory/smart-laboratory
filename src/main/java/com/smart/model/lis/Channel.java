package com.smart.model.lis;

import javax.persistence.*;

/**
 * Title: Channel
 * Description: 仪器通道Model
 *
 * @Author:zhou
 * @Date:2016/6/2 8:49
 * @Version:
 */
@Entity
@IdClass(ChannelId.class)
@Table(name = "lab_channel")
public class Channel {
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

    private long id;
    private String deviceId;        //仪器ID
    private String channel;         //仪器通道
    private String testId;          //检验项目ID
    private String sampleType;      //标本类型
    //待定，表什么意思？
    //private String yqTestId;

}
