package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *@Description 系统附件表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
public interface SysFileService extends IService<SysFileEntity> {

    /**
     * 获取图片的Base64字节码
     * @param imageId
     * @return
     */
    String base64(String imageId);

    /**
     * 图片缩略图,自定义w h
     */
    void imagesThumbnail(String fileId,String w,String h,HttpServletResponse response) throws Exception;
    /**
     * 图片预览
     * @param fileId
     * @param response
     */
    void imagesView(Long fileId, HttpServletResponse response) throws Exception;
    /**
     * 文件下载
     * @param fileId
     * @param response
     */
    void downloadFile(Long fileId, HttpServletResponse response) throws Exception;
    /**
     * 文件上传
     * @param file
     * @return
     */
    Long upFile(MultipartFile file) throws Exception ;

    /**
     * 删除文件
     * @param fileIds
     */
    void delFile(List<Long> fileIds) throws  Exception;
    /**
     * 持久化文件
     * @param fileIds
     */
    void persistFile(String fileIds);

    /**
     * 定时清理垃圾文件
     */
    void clearGarbage();

    PageEntity queryPage(Map<String, Object> params);
}

