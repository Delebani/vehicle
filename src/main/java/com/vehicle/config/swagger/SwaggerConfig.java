package com.vehicle.config.swagger;

import com.vehicle.base.exception.BaseExceptionEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"dev"})
public class SwaggerConfig {

    private List<ResponseMessage> responseMessageList() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(BaseExceptionEnum.values()).forEach(errorEnums -> {
            if (errorEnums.getCode() >= 0) {
                responseMessageList.add(
                        new ResponseMessageBuilder()
                                .code(errorEnums.getCode())
                                .message(errorEnums.getMsg())
                                .responseModel(new ModelRef("Response响应对象"))
                                .build()
                );
            }
        });

        return responseMessageList;
    }

    private List<Parameter> pars() {

        ParameterBuilder tokenParUser = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        Parameter parUser = tokenParUser
                .name("TOKEN")
                .description("用户token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("admin")
                .required(false)
                .build();
        pars.add(parUser);

        return pars;
    }

    @Bean
    public Docket createZuulRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("前端")
                .globalResponseMessage(RequestMethod.GET, responseMessageList())
                .globalResponseMessage(RequestMethod.POST, responseMessageList())
                .globalResponseMessage(RequestMethod.PUT, responseMessageList())
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList())
                .globalOperationParameters(pars())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vehicle.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("车辆管理")
                .description("车辆管理")
                .termsOfServiceUrl("https://www.vehicle.com/")
                .version("v1.0.0")
                .build();
    }
}
