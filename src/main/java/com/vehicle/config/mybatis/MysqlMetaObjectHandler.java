package com.vehicle.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MysqlMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        CurrentUser user = UserHolder.get();
        if(null == user){
            user = new CurrentUser();
            user.setName("注册");
        }
        this.setFieldValByName("createTime", localDateTime, metaObject);
        this.setFieldValByName("updateTime", localDateTime, metaObject);
        this.setFieldValByName("updater", user.getName(), metaObject);
        this.setFieldValByName("creator", user.getName(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        CurrentUser user = UserHolder.get();
        if(null == user){
            user = new CurrentUser();
            user.setName("注册");
        }
        this.setFieldValByName("updateTime", localDateTime, metaObject);
        this.setFieldValByName("updater", user.getName(), metaObject);
    }
}
