package com.cz.spring.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cz.spring.common.JsonResult;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.common.utils.UUIDUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//upload api
@Controller
@RequestMapping("/api/file")
public class ApiFileController {

    @Value("${file.filePath}")
    private String filePath;

    @Value("${file.successPath}")
    private String fileSuccessPath;

    @RequestMapping("/uploadIm")
    @ResponseBody
    public JsonResult uploadIm(@RequestParam(value = "file") MultipartFile file) {
        String fileName = file.getOriginalFilename();  
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  
        fileName = UUIDUtil.randomUUID8() + suffixName; 
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("upload file failed");
        }
        String src = fileSuccessPath + "/" + fileName;
        Map<String,Object> map=new HashMap<>();
        map.put("src",src);
        return JsonResult.ok(0,"upload file success").put("data",map);
    }
}
