package org.lgl.springboot.sys.controller;

import cn.hutool.core.date.DateUtil;
import org.lgl.springboot.sys.common.AppFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 */
@RestController
@RequestMapping("file")
public class FileController {
    /**
     * 文件上传
     */
    @RequestMapping("uploadFile")
    public Map<String,Object> uploadFile(MultipartFile mf){
        //1.得到文件名
        String oldName = mf.getOriginalFilename();
        //2.根据文件名生成新的文件名
        String newName = AppFileUtils.createNewFileName(oldName);
        //3.得到当前日期的字符串
        String dirName = DateUtil.format(new Date(),"yyyy-MM-dd");
        //4.构造文件夹
        File dirfile = new File(AppFileUtils.UPLOAD_PATH,dirName);
        //5.判断文件夹是否存在
        if(!dirfile.exists()){
            dirfile.mkdirs();//创建文件夹
        }
        //6.构建文件对象
        File file = new File(dirfile,newName+"_temp");
        //7.将mf里面的图片信息写入file中
        try {
            mf.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("path",dirName+"/"+newName+"_temp");
        return map;
    }
    /**
     * 文件下载
     */
    @RequestMapping("showImageByPath")
    public ResponseEntity<Object> showImageByPath(String path){
        return AppFileUtils.createResponseEntity(path);
    }
}
