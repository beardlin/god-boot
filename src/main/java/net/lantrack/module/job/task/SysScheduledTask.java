package net.lantrack.module.job.task;

import net.lantrack.module.sys.service.SysFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *@Description  采用注解方式实现定时任务
 *@Author dahuzi
 *@Date 2019/11/9  16:54
 */
@Component
public class SysScheduledTask {
    private static Logger logger = LoggerFactory.getLogger(SysScheduledTask.class);
    @Autowired
    private SysFileService sysFileService;

    //每15min执行一次
    @Scheduled(cron = "0 0/15 * * * *")
    public void test(){
        logger.info("SysScheduledTask：test执行");
        System.out.println("SysScheduledTask：test执行"+System.currentTimeMillis());
    }

    //每天半夜1点清理垃圾文件
    @Scheduled(cron = "0 0 1 * * *")
    public void cleanFile(){
        logger.info("SysScheduledTask：cleanFile执行");
        sysFileService.clearGarbage();
    }

}
