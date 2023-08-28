package com.vehicle.service;

import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.constants.Constants;
import com.vehicle.base.constants.SexEnum;
import com.vehicle.base.constants.UserStateEnum;
import com.vehicle.base.constants.UserTypeEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.req.PcLoginReq;
import com.vehicle.dto.req.PcRegisterReq;
import com.vehicle.dto.req.PcResetReq;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.po.UserPo;
import com.vehicle.transform.UserTransform;
import com.vehicle.utils.PasswordUtils;
import com.vehicle.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijianbing
 * @date 2023/8/1 21:21
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserService userService;

    public UserVo pcRegister(PcRegisterReq req) {

        UserPo userPo = userService.getByMobile(req.getMobile());
        if (null != userPo) {
            throw BizException.error("该手机号已注册，请直接登录");
        }

        // 验证码 验证
        String code = CacheManager.getData(Constants.MOBILE_CODE + req.getMobile());
        if (null == code) {
            throw BizException.error("验证码已过期");
        }
        if (!req.getCode().equals(code)) {
            throw BizException.error("验证码填写错误，请重新输入");
        }

        UserPo po = new UserPo();
        po.setName(req.getMobile());
        po.setMobile(req.getMobile());
        po.setIdNo("请填写身份证号码");
        po.setType(UserTypeEnum.ORDINARY.getCode());
        po.setHeadUrl(Constants.DEFAULT_HEAD);
        po.setPassword(Constants.DEFAULT_PASSWORD);
        po.setState(UserStateEnum.ENABLE.getCode());
        userService.save(po);

        return UserTransform.INSTANCE.po2Vo(userPo);
    }

    public UserVo pcLogin(PcLoginReq req) {

        UserPo userPo = userService.getByMobileAndPassword(req.getMobile(), req.getPassword());
        if (null == userPo) {
            throw BizException.error("用户不存在");
        }
        if(UserStateEnum.FREEZE.getCode() == userPo.getState()){
            throw BizException.error("账号被冻结，请联系管理员");
        }
        TokenUtil.setToken(userPo);
        userService.updateToken(userPo);
        return UserTransform.INSTANCE.po2Vo(userPo);
    }

    public UserVo pcResetPassword(PcResetReq req) {
        // 验证码 验证
        String code = CacheManager.getData(Constants.MOBILE_CODE + req.getMobile());
        if (null == code) {
            throw BizException.error("验证码已过期");
        }
        if (!req.getCode().equals(code)) {
            throw BizException.error("验证码填写错误，请重新输入");
        }
        UserPo po = userService.getByMobile(req.getMobile());
        po.setPassword(PasswordUtils.encryptPassword(req.getPassword()));
        userService.updateById(po);
        return UserTransform.INSTANCE.po2Vo(po);
    }
}
