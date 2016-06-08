package com.smart.service.impl.lis;

import com.smart.dao.lis.AuditTraceDao;
import com.smart.dao.lis.ChannelDao;
import com.smart.model.lis.Channel;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ChannelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: ChannelManagerImpl
 * Description:仪器通道
 *
 * @Author:zhou
 * @Date:2016/6/2 9:03
 * @Version:
 */
@Service(value = "channelManager")
public class ChannelManagerImpl extends GenericManagerImpl<Channel,Long> implements ChannelManager {
    private ChannelDao channelDao = null;

    @Autowired
    public void setChannelDaoDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
        this.dao = channelDao;
    }

    /**
     * 批量保存仪器通道数据
     * @param channels
     */
    @Override
    public void saveChannels(List<Channel> channels) {
        channelDao.saveChannels(channels);
    }

    @Override
    public Channel getChannel(String deviceid, String testid) {
        return channelDao.getChannel(deviceid,testid);
    }
}