package com.smart.service.impl.lis;

import com.smart.model.lis.ProfileTest;
import com.smart.service.GenericManager;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProfileTestManager;
import org.springframework.stereotype.Service;

/**
 * Title: ProfileTestManagerImpl
 * Description:试验组合
 *
 * @Author:zhou
 * @Date:2016/6/7 15:36
 * @Version:
 */
@Service("profileTestManager")
public class ProfileTestManagerImpl extends GenericManagerImpl<ProfileTest,Long> implements ProfileTestManager{
}
