package com.smart.dao.lis;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Channel;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Title: ChannelDao
 * Description:仪器通道
 *
 * @Author:zhou
 * @Date:2016/6/2 8:56
 * @Version:
 */
public interface ChannelDao  extends GenericDao<Channel, Long> {

    void saveChannels(List<Channel> channels);

    @Transactional
    Channel getChannel(String deviceid, String testid);
}
