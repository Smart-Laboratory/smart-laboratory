package com.smart.webapp.util;

import com.smart.model.lis.Channel;
import com.smart.service.lis.ChannelManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 2016/9/8.
 * 通道UTIL
 */
public class ChannelUtil {
    private static ChannelUtil instance = new ChannelUtil();
    private static Map<String, Channel> map = null;
    private ChannelUtil() {}


    public static ChannelUtil getInstance(ChannelManager channelManager) {
        if (map == null) {
            synchronized (instance) {
                map = new HashMap<String, Channel>();
                for (Channel channel : channelManager.getAll()) {
                    map.put(channel.getDeviceId()+"_"+channel.getTestId(), channel);
                }
            }
        }
        return instance;
    }

    public Map<String, Channel> getMap (){
        return map;
    }
    public Channel getValue(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return null;
        }
    }

    public String getKey(Channel channel) {
        for(String name: map.keySet()) {
            String key = channel.getDeviceId()+"_"+channel.getTestId();
            if(key.equals(map.get(name))) {
                return name;
            }
        }
        return "";
    }

}
