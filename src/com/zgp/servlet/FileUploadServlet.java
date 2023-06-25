package com.zgp.servlet; /**
 * @author 周光璞
 * @creation_time 2023-05-21-11:35
 */

import com.zgp.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FileUploadServlet...");
        //1.判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(request)){
            System.out.println("OK~");
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象
            /**
             *     表单提交的数据就是 input 元素
             *     <input type="file" name="pic" id="" value="2xxx.jpg" onchange="prev(this)"/>
             *     家居名: <input type="text" name="name"><br/>
             *     <input type="submit" value="上传"/>
             */
            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);

            //解决文件名中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");
            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            /**
             * [name=dog.jpg, StoreLocation=D:\Tomcat\apache-tomcat-8.0.51\temp\ upload__47da3151_1883d30feed__7fb1_00000000.tmp,
             * size=234327bytes, isFormField=false, FieldName=pic, name=null,
             * StoreLocation=D:\Tomcat\apache-tomcat-8.0.51\temp\ upload__47da3151_1883d30feed__7fb1_00000001.tmp,
             * size=0bytes, isFormField=true, FieldName=name]
             * */
            //   技巧: 如果我们不知道一个对象是什么结构[1.输出 2.debug 3.底层自动看到]
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //System.out.println(list);
                for (FileItem fileItem : list) {
                    //System.out.println("FileItem="+fileItem);
                    if (fileItem.isFormField()){//如果是true就是文本 input text
                        String name = fileItem.getString("utf-8");
                        System.out.println("家居名=" + name);
                    } else {//是一个文件
                        WebUtils.upload(fileItem,request);
//                        //获取上传的文件名字
//                        String name = fileItem.getName();
//                        System.out.println("上传的文件名："+name);
//
//                        //把这个上传到服务器的 temp 目录下的文件保存到指定的目录
//                        //1.先指定一个目录
//                        String filePath = "/upload/";
//
//                        //2.获取到完整目录,目录是和项目绑定的,是动态的
//                        String fileRealPath =
//                                request.getServletContext().getRealPath(filePath);
//                        System.out.println("完整目录："+fileRealPath);
//
//                        //3.创建上传的目录==>创建目录==>java基础
//
//                        File fileRealPathDirectory = new File(fileRealPath+WebUtils.getYearMonthDay());
//                        System.out.println(fileRealPathDirectory);
//                        if(!fileRealPathDirectory.exists()){//不存在则创建目录
//                            fileRealPathDirectory.mkdirs();//创建
//                        }
//
//                        //4.将文件拷贝到fileRealPathDirectory目录下
//                        //   构建一个上传文件的完整路径 ：目录+文件名
//                        //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
//                        name = UUID.randomUUID().toString() +"_" + System.currentTimeMillis() + "_" + name;
//                        String fileFullPath = fileRealPathDirectory +"/"+ name;
//                        System.out.println("fileFullPath:"+fileFullPath);
//                        fileItem.write(new File(fileFullPath));

                        //5.提示信息
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().write("上传成功~");
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("不是文件表单...");
        }
    }
}
