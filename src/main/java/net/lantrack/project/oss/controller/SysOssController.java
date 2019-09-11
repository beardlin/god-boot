
package net.lantrack.project.oss.controller;

import com.google.gson.Gson;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.ConfigConstant;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.framework.common.validator.group.AliyunGroup;
import net.lantrack.framework.common.validator.group.QcloudGroup;
import net.lantrack.framework.common.validator.group.QiniuGroup;
import net.lantrack.project.oss.cloud.CloudStorageConfig;
import net.lantrack.project.oss.cloud.OSSFactory;
import net.lantrack.project.oss.entity.SysOssEntity;
import net.lantrack.project.oss.service.SysOssService;
import net.lantrack.project.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 *@Description 文件上传
 *@Author lantrack
 *@Date 2019/8/23  10:06
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends BaseController {
	@Autowired
	private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		PageEntity page = sysOssService.queryPage(params);

		return getR().result(page);
	}


    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public ReturnEntity config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return getR().result(config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@PostMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public ReturnEntity saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return getR();
	}
	

	/**
	 * 上传文件
	 */
	@PostMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public ReturnEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new GlobalException("上传文件不能为空");
		}

		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

		return getR().result(url);
	}


	/**
	 * 删除
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public ReturnEntity delete(@RequestBody Long[] ids){
		sysOssService.removeByIds(Arrays.asList(ids));

		return getR();
	}

}
