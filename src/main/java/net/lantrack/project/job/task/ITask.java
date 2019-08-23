
package net.lantrack.project.job.task;

/**
 *@Description 定时任务接口，所有定时任务都要实现该接口
 *@Author lantrack
 *@Date 2019/8/23  9:09
 */
public interface ITask {

    /**
     * 执行定时任务接口
     *
     * @param params   参数，多参数使用JSON数据
     */
    void run(String params);
}