
package net.lantrack.project.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.utils.PageUtils;
import net.lantrack.project.oss.entity.SysOssEntity;

import java.util.Map;

/**
 *@Description 文件上传
 *@Author lantrack
 *@Date 2019/8/23  10:05
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
