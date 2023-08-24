package com.vehicle.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lijianbing
 * @date 2023/8/1 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserReq 对象", description = "UserReq 请求对象")
public class UserReq implements Serializable {
    private static final long serialVersionUID = -7036595752268707022L;

    private Long id;

    @NotBlank(message = "请填写姓名")
    private String name;

    @NotNull(message = "请填写用户类型")
    private Integer type;

    private Integer sex;

    @NotNull(message = "请填写年龄")
    private Integer age;

    @NotBlank(message = "请填写手机号")
    private String mobile;

    private String headUrl;

    @NotBlank(message = "请填写身份证号码")
    private String idNo;

    @NotBlank(message = "请填写部门")
    private String department;

    @NotBlank(message = "请填写岗位")
    private String duty;

    @NotBlank(message = "请填写职务")
    private String post;

    private String password;
}
