
package net.lantrack.project.oss.cloud;


import net.lantrack.framework.common.utils.ConfigConstant;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.utils.SpringContextUtils;
import net.lantrack.project.sys.service.SysConfigService;

/**
 *@Description 文件上传Factory
 *@Author lantrack
 *@Date 2019/8/23  9:58
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
