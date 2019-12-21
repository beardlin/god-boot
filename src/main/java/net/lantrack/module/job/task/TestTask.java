
package net.lantrack.module.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *@Description  测试定时任务(演示Demo ， 可删除) testTask为spring bean的名称
 *@Author dahuzi
 *@Date 2019/10/29  17:29
 */
@Component("testTask")
public class TestTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}
}
