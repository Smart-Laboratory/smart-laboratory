package com.smart.dao.hibernate.lis;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ChannelDao;
import com.smart.model.lis.Channel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 批量保存仪器通道数据
     * @param channels  //仪器通道List
     */
    public void saveChannels(List<Channel> channels) {
        Session session = null;
        if(channels !=null && channels.size()>0){
            try{
                session = getSession();     //获取session
                session.beginTransaction(); //开启事务
                Channel channel = null;
                for(int i = 0; i < channels.size(); i++){
                    channel = channels.get(i);
                    session.saveOrUpdate(channel);
                }
                // 批插入的对象立即写入数据库并释放内存
                session.flush();
                session.clear();
                session.getTransaction().commit(); // 提交事务
            }catch (Exception e){
                log.error(e);
                session.getTransaction().rollback(); // 出错将回滚事务
            }finally{
                session.close(); // 关闭Session
            }
        }
    }

    /**
     * 获取仪器通道
     * @param deviceid  //仪器ID
     * @param testid    //项目ID
     * @return
     */
    @SuppressWarnings("unchecked")
	public Channel getChannel(String deviceid, String testid){
        String sql = "from Channel s where s.deviceId =:deviceid and s.testId =:testid";
        Query query= getSession().createQuery(sql);
        query.setString("deviceid",deviceid);
        query.setString("testid",testid);

        List<Channel> channels = query.list();
        if(channels.size()>0)
            return channels.get(0);
        else
            return null;
    }
}
