package net.lantrack.framework.common.utils;

import net.lantrack.framework.common.exception.GlobalException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 *@Description  web下载
 *@Author dahuzi
 *@Date 2019/11/23  16:30
 */
public class WebDownLoadUtil {
    /**
     *
     * @param file 文件
     * @param fileName 下载名称
     * @param response  repos
     * @throws Exception
     */
    public static void download(String file, String fileName,HttpServletResponse response)throws Exception{
       download(new File(file),fileName,response);
    }
    /**
     *
     * @param file 文件
     * @param fileName 下载名称
     * @param response  repos
     * @throws Exception
     */
    public static void download(File file, String fileName,HttpServletResponse response)throws Exception{
        if(!file.exists()){
            throw new GlobalException("文件不存在");
        }
        byte[] bytes = FileUtils.readFileToByteArray(file);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }

    public static void download(String fileName,HttpServletResponse response)throws Exception{
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");
    }
}
