package com.raycloud.overseas.erp.data.services.task;

import com.alibaba.fastjson.JSON;
import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.util.DataUtil;
import com.raycloud.overseas.erp.data.services.api.PushItemService;
import com.raycloud.overseas.erp.data.services.impl.GuessItemServiceImpl;
import com.raycloud.overseas.erp.data.services.impl.GuessMerchantServiceImpl;
import com.raycloud.overseas.erp.data.services.impl.RecordServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MyJobRunner.java
 * Created by zhongliang
 * Created on 2017/3/29 下午2:26
 * Copyright(c)2014  版权所有
 */
@Component
public class MyJobRunner implements JobRunner {


    private static Logger logger= Logger.getLogger(MyJobRunner.class);

    @Resource
    private KF5Task kf5Task;

    @Resource
    private GuessItemServiceImpl guessItemService;

    @Resource
    private GuessMerchantServiceImpl guessMerchantService;

    @Resource
    private PushItemService pushItemServiceImpl;

    @Resource
    private RecordServiceImpl recordService;


    @Override
    public Result run(JobContext jobContext) throws Throwable {

        Job job = jobContext.getJob();
        String taskId = job.getTaskId();
        Long start = System.currentTimeMillis();

        logger.biz("{},开始执行LTS任务:{}",taskId, JSON.toJSONString(jobContext));

        try{
            if ("dataRefreshNews".equals(taskId)) {

                kf5Task.refreshNewsPost();

            } else if ("dataGuessItem".equals(taskId)) {

                guessItemService.timerGuessItemTask();

            } else if ("dataGuessMerchant".equals(taskId)) {

                guessMerchantService.timerGuessMerchantTask();

            } else if ("dataPushItem".equals(taskId)) {

                pushItemServiceImpl.timerRefreshPushItemTask();

            } else if ("dataCancelPushItem".equals(taskId)) {

                pushItemServiceImpl.timerCancleItemFocusTask();

            } else if ("dataRecord".equals(taskId)) {

                recordService.timerRecordItemTask();

            } else {

                jobContext.getBizLogger().info(taskId+",没有匹配到执行的任务:" + DataUtil.getServerIp());
            }
          //  jobContext.getBizLogger().info(taskId+"taskId执行成功,执行任务节点:" + getMyIp());
        logger.biz("{},任务LTS执行结束",taskId);
        }catch(Exception e){
            jobContext.getBizLogger().error(taskId + "taskId执行失败,执行任务节点:" + DataUtil.getServerIp());
            return new Result(Action.EXECUTE_LATER, e.getMessage());

        }

        return new Result (Action.EXECUTE_SUCCESS, taskId+",执行成功:"+ DataUtil.getServerIp()+",耗时:"+(System.currentTimeMillis()-start));
    }



}
