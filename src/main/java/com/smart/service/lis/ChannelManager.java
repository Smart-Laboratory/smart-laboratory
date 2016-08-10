package com.smart.service.lis;

import com.smart.model.lis.Channel;
import com.smart.service.GenericManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: ChannelManager
 * Description:仪器通道
 *
 * @Author:zhou
 * @Date:2016/6/2 9:02
 * @Version:
 */

public interface ChannelManager extends GenericManager<Channel, Long> {
    void saveChannels(List<Channel> channels);
    Channel getChannel(String deviceid, String testid);
}
