package net.lantrack.module.sys.service.impl;

import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.DateUtil;
import net.lantrack.framework.common.utils.UserUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.sys.dao.SysFileDao;
import net.lantrack.module.sys.entity.SysFileEntity;
import net.lantrack.module.sys.service.SysFileService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *@Description 系统附件表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {


    @Override
    public String base64(String imageId) {
        SysFileEntity fileEntity = this.baseMapper.selectById(imageId);
        String imgFile = fileEntity.getFilePath();
        try {
//            Thumbnails.of(fileEntity.getFilePath()).size(100,60)
//                    .imageType(1).toFile(new File(""));
            byte[] bytes = FileUtils.readFileToByteArray(new File(imgFile));
            return  new String(Base64.encodeBase64(bytes));
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void delFile(List<Long> fileIds){
        List<SysFileEntity> files = this.baseMapper.selectList(
                new QueryWrapper<SysFileEntity>().in("id", fileIds)
        );
        for(SysFileEntity file:files){
            File f = new File(file.getFilePath());
            if(f.exists()){
                f.delete();
            }
        }
        this.baseMapper.deleteBatchIds(fileIds);
    }

    @Override
    public void persistFile(String fileIds) {
        //持久化文件状态
        if(StringUtils.isNotBlank(fileIds)){
            String[] split = fileIds.split(",");
            List<Long> fileIdList = new ArrayList<>();
            for(String fid:split){
                fileIdList.add(Long.valueOf(fid));
            }
            this.baseMapper.persistFile(fileIdList);
        }

    }

    @Override
    public void clearGarbage() {
        List<SysFileEntity> files = this.baseMapper.selectList(
                new QueryWrapper<SysFileEntity>().eq("use_status", 0)
        );
        List<Long> fileIds = new ArrayList<>(files.size());
        for(SysFileEntity file:files){
            fileIds.add(file.getId());
        }
        if(!fileIds.isEmpty()){
            delFile(fileIds);
        }
    }

    @Override
    public void imagesThumbnail(String fileId,String w,String h,HttpServletResponse response) throws Exception{
        Integer we = w==null?60:Integer.valueOf(w);
        Integer he = h==null?50:Integer.valueOf(h);
        SysFileEntity fileEntity = this.baseMapper.selectById(fileId);
        if(fileEntity==null){
            return;
        }
        String allType = ".BMP.JPG.JPEG.PNG.GIF.jpg.jpeg.gif.png.bmp";
        //判断当前文件是否为图片类型
        if(allType.indexOf(fileEntity.getFileType()) > 0){
            Thumbnails.of(fileEntity.getFilePath()).size(we,he).toOutputStream(response.getOutputStream());
        }else {
            throw new GlobalException("当前文件不是图片格式");
        }
    }

    @Override
    public void imagesView(Long fileId, HttpServletResponse response) throws Exception{
        SysFileEntity fileEntity = this.baseMapper.selectById(fileId);
        String allType = ".BMP.JPG.JPEG.PNG.GIF.jpg.jpeg.gif.png.bmp";
        //判断当前文件是否为图片类型
        if(allType.indexOf(fileEntity.getFileType()) > 0){
            String filePath = fileEntity.getFilePath();
            File file = new File(filePath);
            if(!file.exists()){
                throw new GlobalException("文件不存在");
            }
            byte[] bytes = FileUtils.readFileToByteArray(file);
            response.reset();
            //设置返回的文件类型
            response.setContentType("image/jpeg");
            IOUtils.write(bytes, response.getOutputStream());
        }else {
            throw new GlobalException("当前文件不是图片格式");
        }
    }

    @Override
    public void downloadFile(Long fileId, HttpServletResponse response) throws Exception{
        SysFileEntity fileEntity = this.baseMapper.selectById(fileId);
        String oldName = fileEntity.getOldName();
        String filePath = fileEntity.getFilePath();
        File file = new File(filePath);
        if(!file.exists()){
            throw new GlobalException("文件不存在");
        }
        byte[] bytes = FileUtils.readFileToByteArray(file);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename="+oldName);
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }

    @Override
    public Long upFile(MultipartFile file) throws Exception {
        SysFileEntity sysFile = new SysFileEntity();
        //上传文件名称
        String fileName = file.getOriginalFilename();
        sysFile.setOldName(fileName);
        //文件类型
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        sysFile.setFileType(suffix);
        //文件大小byte
        long size = file.getSize();
        sysFile.setFileSize(size);
        // 该方法返回的为当前项目的工作目录
        String dirPath = System.getProperty("user.dir")+ File.separator + "upload" +File.separator+ DateUtil.getDate("yyyyMMdd");
        File fileDir = new File(dirPath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        //使用uuid当做新文件名
        String uuidFileName = UUID.randomUUID().toString().replace("-", "")+"."+suffix;
        sysFile.setFileName(uuidFileName);
        //上传文件
        File f = new File(dirPath + File.separator + uuidFileName);
        file.transferTo(f);
        //文件保存路径
        sysFile.setFilePath(f.getAbsolutePath());
        //保存文件信息
        sysFile.setUploadBy(UserUtils.getUserName());
        sysFile.setUploadTime(new Date());
        //文件上传后当做垃圾文件，持久化后改为1
        sysFile.setUseStatus(0);
        this.save(sysFile);
        return sysFile.getId();
    }


    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<SysFileEntity> page = this.page(
                new Query<SysFileEntity>().getPage(params),
                new QueryWrapper<SysFileEntity>()
        );

        return new PageEntity(page);
    }

}
