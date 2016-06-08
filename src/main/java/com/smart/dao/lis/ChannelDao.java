package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Channel;
import com.smart.model.lis.Device;

import java.util.List;

/**
 * Title: ChannelDao
 * Description:仪器通道
 *
 * @Author:zhou
 * @Date:2016/6/2 8:56
 * @Version:
 */
public interface ChannelDao  extends GenericDao<Channel, Long> {

    public void saveChannels(List<Channel> channels);
    public Channel getChannel(String deviceid,String testid);
}
