package com.vehicle.controller;

import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.exception.BizException;
import com.vehicle.base.web.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author lijianbing
 * @date 2023/8/25 15:53
 */
@Api(value = "文件", tags = "文件")
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping("/upload")
    public Response upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            throw BizException.error("文件不存在");
        }
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //随机生成一个文件名
        fileName = UUID.randomUUID().toString().toLowerCase().replace("-", "") + suffixName;
        //设置上传文件的路径
        CurrentUser currentUser = UserHolder.get();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String localDate = dateTimeFormatter.format(LocalDate.now()).toString();
        String folderPath = uploadPath + currentUser.getName() + "\\" + localDate + "\\";
        //上传
        File dest = new File(folderPath, fileName);
        //判断文件路径是否存在，如果不存在 创建
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw BizException.error("上传失败");
        }
        //设置最终的文件名称信息返回前端
        String filePath = "/upload/" + currentUser.getName() + "/" + localDate + "/" + fileName;
        String fileUrl = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath()+ "/upload/" + currentUser.getName()+ "/" + localDate + "/"+ fileName;
        return Response.success(filePath);
    }

}
