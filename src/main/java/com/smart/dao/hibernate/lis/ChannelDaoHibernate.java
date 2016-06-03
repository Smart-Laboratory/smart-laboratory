package com.smart.dao.hibernate.lis;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ChannelDao;
import com.smart.model.lis.Channel;
import com.smart.model.lis.Device;
import org.springframework.stereotype.Repository;

/**
 * Title: ChannelDaoHibernate
 * Description:仪器通道
 *
 * @Author:zhou
 * @Date:2016/6/2 8:58
 * @Version:
 */
@Repository("channelDao")
public class ChannelDaoHibernate extends GenericDaoHibernate<Channel,Long> implements ChannelDao {
    public ChannelDaoHibernate() {
        super(Channel.class);
    }
}
