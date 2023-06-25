package com.zgp.utils;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author 周光璞
 * @creation_time 2023-05-21-16:31
 */
public class WebUtils {
    public static String getYearMonthDay(){
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        String yearMonthDay = year+"/"+month+"/"+day;
        return yearMonthDay;
    }


    public static void upload(FileItem fileItem, HttpServletRequest request){
        //获取上传的文件名字
        String name = fileItem.getName();
        System.out.println("上传的文件名："+name);

        //把这个上传到服务器的 temp 目录下的文件保存到指定的目录
        //1.先指定一个目录
        String filePath = "/upload/";

        //2.获取到完整目录,目录是和项目绑定的,是动态的
        String fileRealPath =
                request.getServletContext().getRealPath(filePath);
        System.out.println("完整目录："+fileRealPath);

        //3.创建上传的目录==>创建目录==>java基础

        File fileRealPathDirectory = new File(fileRealPath+WebUtils.getYearMonthDay());
        System.out.println(fileRealPathDirectory);
        if(!fileRealPathDirectory.exists()){//不存在则创建目录
            fileRealPathDirectory.mkdirs();//创建
        }

        //4.将文件拷贝到fileRealPathDirectory目录下
        //   构建一个上传文件的完整路径 ：目录+文件名
        //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
        name = UUID.randomUUID().toString() +"_" + System.currentTimeMillis() + "_" + name;
        String fileFullPath = fileRealPathDirectory +"/"+ name;
        System.out.println("fileFullPath:"+fileFullPath);
        try {
            fileItem.write(new File(fileFullPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String yearMonthDay = WebUtils.getYearMonthDay();
        System.out.println(yearMonthDay);
    }

}
