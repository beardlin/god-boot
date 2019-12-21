package net.lantrack.module.sys.controller;

import java.util.List;
import java.util.Map;

import net.lantrack.framework.common.exception.GlobalException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysFileEntity;
import net.lantrack.module.sys.service.SysFileService;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


/**
 *@Description 系统附件表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@RestController
@RequestMapping("sys/sysfile")
public class SysFileController extends BaseController {
    @Autowired
    private SysFileService sysFileService;

    /**
     * 查看缩略图
     * sys/sysfile/thumbnail
     */
    @RequestMapping("/thumbnail")
    public void thumbnail(String fileId,String w,String h, HttpServletResponse response) throws Exception{
        this.sysFileService.imagesThumbnail(fileId,w,h,response);
    }

    /**
     * 图片预览
     * sys/sysfile/picture/{fileId}
     */
    @RequestMapping("/picture/{fileId}")
    public void picture(@PathVariable("fileId") Long fileId,HttpServletResponse response) throws Exception{
        this.sysFileService.imagesView(fileId,response);
    }
    /**
     * 文件删除
     * sys/sysfile/delete
     */
    @RequestMapping("/delete")
    public ReturnEntity delete(@RequestBody List<Long> fileIds) throws Exception{
        this.sysFileService.delFile(fileIds);
        return getR();
    }

    /**
     * 文件下载
     * sys/sysfile/download/{fileId}
     */
    @RequestMapping("/download/{fileId}")
    public void download(@PathVariable("fileId") String fileId,
                         HttpServletResponse response) throws Exception{
        if(fileId==null){
            throw  new GlobalException("文件id不能为空");
        }
        this.sysFileService.downloadFile(Long.valueOf(fileId),response);
    }
    /**
     * 上传文件
     * sys/sysfile/upload
     */
    @PostMapping("/upload")
    public ReturnEntity upload(@RequestParam("file") MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            return getR().err("上传文件不能为空");
        }
        Long fid = this.sysFileService.upFile(file);
        return getR().result(fid);
    }

//    /**
//     * 上传多个文件
//     * @param request
//     * @return
//     */
//    @PostMapping("/multiUpload")
//    public ReturnEntity multiUpload(HttpServletRequest request) {
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//        String filePath = "D:/temp/";
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            if (file.isEmpty()) {
//               continue;
//            }
//            String fileName = file.getOriginalFilename();
//
//            File dest = new File(filePath + fileName);
//            try {
//                file.transferTo(dest);
//            } catch (IOException e) {
//                   e.printStackTrace();
//            }
//        }
//
//        return getR();
//    }

    /**
     * 分页列表
     */
//    @RequestMapping("/page")
//    @RequiresPermissions("sys:sysfile:page")
//    public ReturnEntity page(@RequestBody Map<String, Object> params){
//        PageEntity page = sysFileService.queryPage(params);
//        return getR().result(page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysfile:info")
    public ReturnEntity info(@PathVariable("id") Long id){
		SysFileEntity sysFile = sysFileService.getById(id);
        return getR().result(sysFile);
    }



}
