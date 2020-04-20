package org.lgl.springboot.sys.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 文件上传下载工具类
 */
public class AppFileUtils {
    //文件上传的保存路径
    public static String UPLOAD_PATH="D:/upload/";

    static {
        //读取配配置文件的存储地址
        InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null==properties.getProperty("filepath")){
            UPLOAD_PATH = properties.getProperty("filepath");
        }
    }

    /**
     * 根据文件老名字得到新名字
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {
        String stuff = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        return IdUtil.simpleUUID().toUpperCase()+stuff;
    }

    /**
     * 文件下载
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1.构造文件对象
        File file = new File(UPLOAD_PATH,path);
        if(file.exists()){
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //创建封装响应头信息的对象
            HttpHeaders header = new HttpHeaders();
            //封装响应内容类型
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置下载的文件的名称
            //header.setContentDispositionFormData("attachment","123.jpg");
            //创建ResponseEntity对象
            ResponseEntity<Object> entity =
                    new ResponseEntity(bytes,header, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }

    /**
     * 根据路径更改名字
     * @param goodsimg
     * @return
     */
    public static String renameFile(String goodsimg) {
        File file = new File(UPLOAD_PATH,goodsimg);
        String replace = goodsimg.replace("_temp", "");
        if(file.exists()){
            file.renameTo(new File(UPLOAD_PATH,replace));
        }
        return replace;
    }

    /**
     * 根据原来的路径删除图片
     * @param oldPath
     */
    public static void removeFile(String oldPath) {
        if(!oldPath.equals(Constast.IMAGES_DEFAULTGOODSIMG_JPG)){
            File file = new File(UPLOAD_PATH,oldPath);
            if(file.exists()){
                file.delete();
            }
        }
    }
}
