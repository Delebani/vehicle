package com.vehicle.utils;

import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.po.UserPo;
import com.vehicle.transform.UserTransform;

import java.util.UUID;

/**
 * 用户token
 *
 * @author lijianbing
 * @date 2023/8/2 14:44
 */
public class TokenUtil {

    public static void setToken(UserPo po) {
        String token = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        po.setToken(token);
        CurrentUser currentUser = UserTransform.INSTANCE.po2CurrentUser(po);
        CacheManager.setData(token, currentUser, 7200);
        UserHolder.put(currentUser);
    }
}
